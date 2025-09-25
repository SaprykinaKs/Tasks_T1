-- PRODUCT_REGISTRY
CREATE TABLE IF NOT EXISTS product_registry (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    interest_rate NUMERIC(5,2),
    open_date TIMESTAMP DEFAULT now()
);

-- PAYMENT_REGISTRY
CREATE TABLE IF NOT EXISTS payment_registry (
    id BIGSERIAL PRIMARY KEY,
    product_registry_id BIGINT NOT NULL REFERENCES product_registry(id),
    paymend_date TIMESTAMP NOT NULL,
    amount NUMERIC(18,2) NOT NULL,
    interest_rate_amount NUMERIC(18,2),
    debt_amount NUMERIC(18,2),
    expired BOOLEAN DEFAULT false,
    payment_expiration_date TIMESTAMP
);
