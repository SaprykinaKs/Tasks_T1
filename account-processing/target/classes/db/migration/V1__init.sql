-- ACCOUNTS
CREATE TABLE IF NOT EXISTS accounts (
    id BIGSERIAL PRIMARY KEY,
    client_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    balance NUMERIC(18,2) DEFAULT 0,
    interest_rate NUMERIC(5,2),
    is_recalc BOOLEAN DEFAULT false,
    card_exist BOOLEAN DEFAULT false,
    status VARCHAR(20) NOT NULL
);

-- CARDS
CREATE TABLE IF NOT EXISTS cards (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES accounts(id),
    card_id VARCHAR(50) UNIQUE NOT NULL,
    payment_system VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL
);

-- PAYMENTS
CREATE TABLE IF NOT EXISTS payments (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES accounts(id),
    payment_date TIMESTAMP NOT NULL,
    amount NUMERIC(18,2) NOT NULL,
    is_credit BOOLEAN DEFAULT false,
    payed_at TIMESTAMP,
    type VARCHAR(50)
);

-- TRANSACTIONS
CREATE TABLE IF NOT EXISTS transactions (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES accounts(id),
    card_id BIGINT REFERENCES cards(id),
    type VARCHAR(50) NOT NULL,
    amount NUMERIC(18,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    timestamp TIMESTAMP DEFAULT now()
);
