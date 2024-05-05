-- CREATE TABLE IF NOT EXISTS categories
-- (
--     id           BIGINT PRIMARY KEY,
--     categoryName VARCHAR(255) NOT NULL
-- );
--
-- CREATE TABLE IF NOT EXISTS roles
-- (
--     id       INT PRIMARY KEY,
--     roleName VARCHAR(255) NOT NULL
-- );
--
--
-- -- UserEntity table
-- CREATE TABLE IF NOT EXISTS user
-- (
--     id           BIGSERIAL    NOT NULL,
--     created_at   TIMESTAMP(6),
--     full_name    VARCHAR(255) NOT NULL,
--     username     VARCHAR(255) NOT NULL,
--     password     VARCHAR(255) NOT NULL,
--     avatar_url   VARCHAR(255),
--     email        VARCHAR(255) NOT NULL,
--     phone_number VARCHAR(255) NOT NULL,
--     role         VARCHAR(255),
--     PRIMARY KEY (id)
-- );
--
-- -- Order table
-- CREATE TABLE IF NOT EXISTS orders
-- (
--     id           BIGSERIAL NOT NULL,
--     created_at   TIMESTAMP(6),
--     user_id      BIGINT    NOT NULL,
--     product_id   BIGINT    NOT NULL,
--     quantity     INTEGER,
--     total_price  DOUBLE PRECISION,
--     address      VARCHAR(255),
--     phone_number VARCHAR(255),
--     PRIMARY KEY (id),
--     FOREIGN KEY (user_id) REFERENCES users (id),
--     FOREIGN KEY (product_id) REFERENCES products (id)
-- );
--
-- -- Product table
-- CREATE TABLE IF NOT EXISTS products
-- (
--     id          BIGSERIAL NOT NULL,
--     created_at  TIMESTAMP(6),
--     title       VARCHAR(255),
--     description TEXT,
--     price       DOUBLE PRECISION,
--     quantity    INTEGER,
--     image_url   VARCHAR(255),
--     category    VARCHAR(255),
--     user_id     BIGINT    NOT NULL,
--     PRIMARY KEY (id),
--     FOREIGN KEY (user_id) REFERENCES users (id)
-- );
--
--
-- CREATE TABLE IF NOT EXISTS News
-- (
--     id        BIGINT PRIMARY KEY,
--     createdAt TIMESTAMP,
--     title     VARCHAR(255),
--     content   TEXT,
--     productId BIGINT,
--     FOREIGN KEY (productId) REFERENCES Products (id)
-- );
--
--
-- CREATE TABLE IF NOT EXISTS User_Roles
-- (
--     userId BIGINT,
--     roleId INT,
--     PRIMARY KEY (userId, roleId),
--     FOREIGN KEY (userId) REFERENCES Users (id),
--     FOREIGN KEY (roleId) REFERENCES Roles (id)
-- );
--
--
-- CREATE TABLE Product_Categories
-- (
--     productId  BIGINT,
--     categoryId BIGINT,
--     PRIMARY KEY (productId, categoryId),
--     FOREIGN KEY (productId) REFERENCES Products (id),
--     FOREIGN KEY (categoryId) REFERENCES Categories (id)
-- );
--
-- CREATE TABLE users_orders
-- (
--     users_id  BIGINT NOT NULL,
--     orders_id BIGINT NOT NULL,
--     PRIMARY KEY (users_id, orders_id),
--     FOREIGN KEY (users_id) REFERENCES users (id),
--     FOREIGN KEY (orders_id) REFERENCES orders (id)
-- );
--
-- CREATE TABLE products_orders
-- (
--     product_id BIGINT NOT NULL,
--     orders_id   BIGINT NOT NULL,
--     PRIMARY KEY (product_id, orders_id),
--     FOREIGN KEY (product_id) REFERENCES products (id),
--     FOREIGN KEY (orders_id) REFERENCES orders (id)
-- );
--
--
--
INSERT INTO roles (role_name)
VALUES ('USER');
INSERT INTO roles (role_name)
VALUES ('ADMIN');


INSERT INTO categories (category_name)
VALUES ('Electronics');
INSERT INTO categories (category_name)
VALUES ('Books');
INSERT INTO categories (category_name)
VALUES ('Home Appliances');
INSERT INTO categories (category_name)
VALUES ('Clothing');
INSERT INTO categories (category_name)
VALUES ('Sports Equipment');
INSERT INTO categories (category_name)
VALUES ('Toys');
INSERT INTO categories (category_name)
VALUES ('Groceries');
INSERT INTO categories (category_name)
VALUES ('Automotive');
INSERT INTO categories (category_name)
VALUES ('Furniture');
INSERT INTO categories (category_name)
VALUES ('Garden');