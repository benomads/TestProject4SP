-- UserEntity table
CREATE TABLE users
(
    id           BIGSERIAL NOT NULL,
    created_at   TIMESTAMP(6),
    full_name    VARCHAR(255) NOT NULL,
    username     VARCHAR(255) NOT NULL,
    password     VARCHAR(255) NOT NULL,
    avatar_url   VARCHAR(255),
    email        VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    role         VARCHAR(255),
    PRIMARY KEY (id)
);

-- Order table
CREATE TABLE orders
(
    id          BIGSERIAL NOT NULL,
    created_at  TIMESTAMP(6),
    user_id     BIGINT NOT NULL,
    product_id  BIGINT NOT NULL,
    quantity    INTEGER,
    total_price DOUBLE PRECISION,
    address     VARCHAR(255),
    phone_number VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Product table
CREATE TABLE products
(
    id          BIGSERIAL NOT NULL,
    created_at  TIMESTAMP(6),
    title       VARCHAR(255),
    description TEXT,
    price       DOUBLE PRECISION,
    quantity    INTEGER,
    image_url   VARCHAR(255),
    category    VARCHAR(255),
    user_id     BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Data for table: users
INSERT INTO users (id,
                   created_at,
                   full_name,
                   username,
                   password,
                   avatar_url,
                   email,
                   phone_number,
                   role)
VALUES (1,
        '2021-10-10 00:00:00',
        'UserEntity Userov',
        'user0001',
        'password',
        'https://www.pngitem.com/pimgs/m/146-1468479_my-profile-icon-blank-profile-picture-circle-hd.png',
        'userovski@gmail.com ',
        '7777777777',
        'USER');




