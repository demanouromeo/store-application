create table carts
(
    id           BINARY(16)    not null primary key,
    date_created date     default (curdate()) not null
);

create table card_items
(
    id         bigint auto_increment
        primary key,
    cart_id    BINARY(16)    not null,
    product_id bigint        not null,
    quantity   int default 1 not null,
    constraint card_items_cart_product_unique_key
        unique (cart_id, product_id),
    constraint card_items_carts_id_fk
        foreign key (cart_id) references carts (id)
            on delete cascade,
    constraint card_items_products_id_fk
        foreign key (product_id) references products (id)
            on delete cascade
);
