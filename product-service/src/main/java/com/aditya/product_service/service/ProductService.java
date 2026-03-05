package com.aditya.product_service.service;

import com.aditya.product_service.dto.CreateProductRequest;
import com.aditya.product_service.dto.ProductResponse;
import com.aditya.product_service.entity.Product;
import com.aditya.product_service.event.ProductCreatedEvent;
import com.aditya.product_service.event.StockReducedEvent;
import com.aditya.product_service.exception.InsufficientStockException;
import com.aditya.product_service.exception.ProductNotFoundException;
import com.aditya.product_service.kafka.ProductEventProducer;
import com.aditya.product_service.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductEventProducer productEventProducer;

    public ProductResponse createProduct(CreateProductRequest request) {

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stock(request.getStock())
                .createdAt(LocalDateTime.now())
                .build();

        Product savedProduct = productRepository.save(product);

        productEventProducer.publishProductCreated(
                ProductCreatedEvent.builder()
                        .productId(savedProduct.getId())
                        .name(savedProduct.getName())
                        .price(savedProduct.getPrice())
                        .quantity(savedProduct.getStock())
                        .build()
        );

        return mapToResponse(savedProduct);
    }

    @Cacheable(value = "productList")
    public Page<ProductResponse> getAllProducts(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> productPage =  productRepository.findAll(pageable);

        return productPage.map(this::mapToResponse);
    }

    @Cacheable(value = "products", key = "#id")
    public ProductResponse getProductById(Long id) throws ProductNotFoundException {

        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with id: " + id)
        );

        return mapToResponse(product);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "productList", allEntries = true)
            },
            put = {
                    @CachePut(value = "products", key = "#id")
            }
    )
    public ProductResponse updateProduct(Long id, CreateProductRequest request) {

        Product product = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with id: " + id)
        );

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product updatedProduct = productRepository.save(product);

        return mapToResponse(updatedProduct);
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "products", key = "#id"),
                    @CacheEvict(value = "productList", allEntries = true)
            }
    )
    public void deleteProductId(Long id) {

        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }

        productRepository.deleteById(id);
    }

    @Transactional
    @Caching(
            evict = {
                    @CacheEvict(value = "products", key = "#productId"),
                    @CacheEvict(value = "productList", allEntries = true)
            }
    )
    public void reduceStock(Long productId, Integer quantity) {

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new ProductNotFoundException("Product not found with id: " + productId)
        );

        if (product.getStock() < quantity) {
            throw new InsufficientStockException("Insufficient stock for product id: " + productId);
        }

        product.setStock(product.getStock() - quantity);

        productEventProducer.publishStockReduced(
                StockReducedEvent.builder()
                        .productId(product.getId())
                        .quantity(product.getStock())
                        .build()
        );
    }

    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .createdAt(product.getCreatedAt())
                .build();
    }
}