INSERT INTO
    `categories` (`id`, `name`)
VALUES
    (1, 'Cars'),
    (2, 'Phones'),
    (3, 'TV');

INSERT INTO
    `carts` (`id`, `date_created`)
VALUES
    (0x017e96988e6c4fa0810267b644e4b78d, '2026-06-19'),
    (0x1ca06ce7e974420bb60b8d852a8e6f6e, '2026-06-19'),
    (0x31baafdb3423415f869c45ad6c59661a, '2026-06-25'),
    (0x4c2b4480ef87458abc340e37ae728043, '2026-06-21'),
    (0xbc9f655a26b945ed864ba93165353d5d, '2026-06-21');

INSERT INTO
    `products` (
        `id`,
        `name`,
        `price`,
        `description`,
        `category_id`
    )
VALUES
    (1, 'SAMSUNG s25', 235.50, 'Model 2025', 2),
    (2, 'Tesla', 34000.00, 'Electric car', 1),
    (3, 'BMW', 50000.00, 'Voiture de courses', 1),
    (4, 'LG', 67.64, 'Smart TV, ROKu', 3),
    (
        5,
        'iPhone 17',
        750.67,
        'Telephone le plus avance de chez APPLE',
        2
    ),
    (6, 'Mercedez', 100000.00, 'Luxury car', 1),
    (7, 'MOTOROLA 2022', 3500.00, '', 2);

INSERT INTO
    `users` (`id`, `name`, `email`, `password`, `role`)
VALUES
    (
        1,
        'Martin EFFA',
        'EffaaMartin@example.com',
        'jonsais934',
        'USER'
    ),
    (
        2,
        'ATEBA Francois',
        'atebfranc@example.com',
        'atebaliid',
        'USER'
    ),
    (
        3,
        'Tamon Kafka',
        'kafka_test11@example.com',
        '123_maiNstreet22',
        'USER'
    ),
    (
        5,
        'Gaban Sean',
        'edimo11@example.com',
        '8iNstreet22',
        'USER'
    );

INSERT INTO
    `card_items` (`id`, `cart_id`, `product_id`, `quantity`)
VALUES
    (1, 0x017e96988e6c4fa0810267b644e4b78d, 1, 2),
    (2, 0x4c2b4480ef87458abc340e37ae728043, 3, 5),
    (3, 0x017e96988e6c4fa0810267b644e4b78d, 2, 4),
    (4, 0x31baafdb3423415f869c45ad6c59661a, 1, 2),
    (5, 0x1ca06ce7e974420bb60b8d852a8e6f6e, 3, 6),
    (6, 0x1ca06ce7e974420bb60b8d852a8e6f6e, 2, 3);