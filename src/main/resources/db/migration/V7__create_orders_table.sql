CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    restaurant_id BIGINT NOT NULL,
    address_id BIGINT NOT NULL,
    status VARCHAR(255) NOT NULL,
    subtotal NUMERIC(10, 2) NOT NULL,
    delivery_fee NUMERIC(10, 2) NOT NULL,
    discount NUMERIC(10, 2) NOT NULL,
    total NUMERIC(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    paid_at TIMESTAMP,
    canceled_at TIMESTAMP,
    delivered_at TIMESTAMP,


    CONSTRAINT fk_orders_customer
        FOREIGN KEY (customer_id) REFERENCES users(id),


    CONSTRAINT fk_orders_restaurant
        FOREIGN KEY (restaurant_id) REFERENCES restaurants(id),


    CONSTRAINT fk_orders_address
        FOREIGN KEY (address_id) REFERENCES addresses(id)
);
