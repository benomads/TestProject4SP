create table orders
(
    quantity    integer   not null,
    total_price float(53) not null,
    created_at  timestamp(6),
    id          bigserial not null,
    product_id  bigint,
    user_id     bigint,
    address     varchar(255),
    primary key (id)
);

create table products
(
    category    smallint check (category between 0 and 5),
    price       float(53),
    quantity    integer,
    created_at  timestamp(6),
    id          bigserial not null,
    users_id    bigint,
    description varchar(255),
    image_url   varchar(255),
    title       varchar(255),
    primary key (id)
);

create table products_orders
(
    orders_id  bigint not null unique,
    product_id bigint not null
);

create table users
(
    created_at   timestamp(6),
    id           bigserial not null,
    email        varchar(255),
    full_name    varchar(255),
    password     varchar(255),
    phone_number varchar(255),
    username     varchar(255),
    primary key (id)
);

create table users_orders
(
    orders_id bigint not null unique,
    user_id   bigint not null
);

create table users_products
(
    products_id bigint not null unique,
    user_id     bigint not null
);

alter table if exists orders
    add constraint FKkp5k52qtiygd8jkag4hayd0qg foreign key (product_id) references products;
alter table if exists orders
    add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (user_id) references users;
alter table if exists products
    add constraint FKpu3y14rfrx91joe0s6m004c11 foreign key (users_id) references users;
alter table if exists products_orders
    add constraint FK20hxmw72fbrwlnc8dvtwnocgy foreign key (orders_id) references orders;
alter table if exists products_orders
    add constraint FKixe7cct0ge9ihyc9okwpw3mpf foreign key (product_id) references products;
alter table if exists users_orders
    add constraint FK2lnf5jw8p8q0ytkr8dp0mlx6 foreign key (orders_id) references orders;
alter table if exists users_orders
    add constraint FKms88pdhtsiuuusjpeij73f6df foreign key (user_id) references users;
alter table if exists users_products
    add constraint FK5aqj5juhtwinl80hqwe9dimtc foreign key (products_id) references products;
alter table if exists users_products
    add constraint FK7j1q4wj87yy0fmovuwq4yf7it foreign key (user_id) references users;