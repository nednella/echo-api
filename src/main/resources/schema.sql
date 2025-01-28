CREATE TABLE
    IF NOT EXISTS "account" (
        id                  UUID PRIMARY KEY UNIQUE NOT NULL DEFAULT gen_random_uuid(),
        username            VARCHAR(15) UNIQUE NOT NULL CHECK (username ~ '^[a-zA-Z0-9_]{3,15}$'),
        encrypted_password  VARCHAR(255) NOT NULL,
        role                VARCHAR(255) NOT NULL DEFAULT USER,
        enabled             BOOLEAN NOT NULL DEFAULT TRUE,
        created_at          TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at          TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
    );

CREATE UNIQUE INDEX 
    IF NOT EXISTS idx_account_username
        ON "account"(Lower(username));
    
CREATE TABLE
    IF NOT EXISTS "profile" (
        account_id       UUID PRIMARY KEY UNIQUE NOT NULL,
        username         VARCHAR(15) UNIQUE NOT NULL CHECK (username ~ '^[a-zA-Z0-9_]{3,15}$'),
        name             VARCHAR(50),
        bio              VARCHAR(160),
        location         VARCHAR(30),
        avatar_url       VARCHAR(255),
        banner_url       VARCHAR(255),
        following_count  INT NOT NULL DEFAULT 0,
        follower_count   INT NOT NULL DEFAULT 0,
        post_count       INT NOT NULL DEFAULT 0,
        media_count      INT NOT NULL DEFAULT 0,
        created_at       TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
        updated_at       TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES "account"(id) ON DELETE CASCADE,
        CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES "account"(username) ON UPDATE CASCADE
    );

CREATE UNIQUE INDEX 
    IF NOT EXISTS idx_profile_username
        ON "profile"(Lower(username));