-- USERS
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

-- CLIENTS
CREATE TABLE IF NOT EXISTS clients (
    id BIGSERIAL PRIMARY KEY,
    client_id VARCHAR(20) UNIQUE NOT NULL,
    user_id BIGINT NOT NULL REFERENCES users(id),
    first_name VARCHAR(255) NOT NULL,
    middle_name VARCHAR(255),
    last_name VARCHAR(255) NOT NULL,
    date_of_birth DATE NOT NULL,
    document_type VARCHAR(20) NOT NULL,
    document_id VARCHAR(50),
    document_prefix VARCHAR(20),
    document_suffix VARCHAR(20)
);

-- PRODUCTS
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    key VARCHAR(10) NOT NULL,
    create_date TIMESTAMP DEFAULT now(),
    product_id VARCHAR(50) UNIQUE NOT NULL
);

-- CLIENT_PRODUCTS
CREATE TABLE IF NOT EXISTS client_products (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL REFERENCES clients(id),
    product_id BIGINT NOT NULL REFERENCES products(id),
    open_date TIMESTAMP DEFAULT now(),
    close_date TIMESTAMP,
    status VARCHAR(20) NOT NULL
);
