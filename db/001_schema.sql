CREATE DATABASE IF NOT EXISTS qikro;
USE qikro;

CREATE TABLE IF NOT EXISTS admin_actions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    admin_id VARCHAR(50) NOT NULL,
    action_type VARCHAR(100) NOT NULL,
    entity_type VARCHAR(50) NOT NULL,
    entity_id VARCHAR(50) NOT NULL,
    before_state JSON,
    after_state JSON,
    reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS orders (
    id VARCHAR(50) PRIMARY KEY,
    retailer_name VARCHAR(150) NOT NULL,
    supplier_name VARCHAR(150) NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,
    order_state VARCHAR(50) DEFAULT 'placed',
    payment_state VARCHAR(50) DEFAULT 'captured',
    delivery_state VARCHAR(50) DEFAULT 'pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT IGNORE INTO orders (id, retailer_name, supplier_name, total_amount, order_state, payment_state, delivery_state) 
VALUES ('ORD-9421', 'Sri Venkateswara General Store', 'Metro Wholesale', 12450.00, 'dispatched', 'captured', 'out_for_delivery');