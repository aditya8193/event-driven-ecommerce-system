CREATE SCHEMA IF NOT EXISTS user_schema;
CREATE SCHEMA IF NOT EXISTS product_schema;
CREATE SCHEMA IF NOT EXISTS order_schema;
CREATE SCHEMA IF NOT EXISTS payment_schema;

CREATE TABLE IF NOT EXISTS order_schema.outbox_event (
    id UUID PRIMARY KEY,
    aggregate_type VARCHAR(255),
    aggregate_id BIGINT,
    event_type VARCHAR(255),
    payload JSONB,
    status VARCHAR(50),
    retry_count INT,
    created_at TIMESTAMP,
    processed_at TIMESTAMP
);