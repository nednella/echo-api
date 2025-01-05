CREATE TABLE
    IF NOT EXISTS "user" (
        id          UUID DEFAULT gen_random_uuid() PRIMARY KEY,
        username    VARCHAR(255) UNIQUE NOT NULL CHECK (username ~ '^[a-zA-Z0-9_]{3,15}$'),
        password    VARCHAR(255),
        role        VARCHAR(255) NOT NULL DEFAULT USER,
        enabled     BOOLEAN NOT NULL DEFAULT TRUE,
        created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE UNIQUE INDEX 
    IF NOT EXISTS idx_user_username
        ON "user"(Lower(username));