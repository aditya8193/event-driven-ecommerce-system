package com.aditya.product_service.controller;

import com.aditya.product_service.dto.CreateProductRequest;
import com.aditya.product_service.dto.ProductResponse;
import com.aditya.product_service.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getAllProducts(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProductById(@PathVariable Long id, @Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductId(@PathVariable Long id) {
        productService.deleteProductId(id);
        return ResponseEntity.ok("Product Deleted Successfully");
    }

    @PostMapping("/{id}/reduce-stock")
    public ResponseEntity<String> reduceStock(@PathVariable Long id, @RequestParam Integer quantity) {
        productService.reduceStock(id, quantity);
        return ResponseEntity.ok("Stock reduced successfully");
    }

}