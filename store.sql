-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : ven. 03 juil. 2026 à 16:17
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `store`
--

DELIMITER $$
--
-- Fonctions
--
CREATE DEFINER=`root`@`localhost` FUNCTION `uuid_to_bin16` () RETURNS BINARY(16) DETERMINISTIC BEGIN
    RETURN UUID_TO_BIN(UUID());
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `addresses`
--

CREATE TABLE `addresses` (
  `id` bigint(20) NOT NULL,
  `street` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `zip` varchar(255) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `card_items`
--

CREATE TABLE `card_items` (
  `id` bigint(20) NOT NULL,
  `cart_id` binary(16) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `card_items`
--

INSERT INTO `card_items` (`id`, `cart_id`, `product_id`, `quantity`) VALUES
(3, 0x017e96988e6c4fa0810267b644e4b78d, 1, 10),
(5, 0x4c2b4480ef87458abc340e37ae728043, 3, 18),
(6, 0x017e96988e6c4fa0810267b644e4b78d, 2, 4);

-- --------------------------------------------------------

--
-- Structure de la table `carts`
--

CREATE TABLE `carts` (
  `id` binary(16) NOT NULL,
  `date_created` date NOT NULL DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `carts`
--

INSERT INTO `carts` (`id`, `date_created`) VALUES
(0x017e96988e6c4fa0810267b644e4b78d, '2026-06-19'),
(0x1ca06ce7e974420bb60b8d852a8e6f6e, '2026-06-19'),
(0x31baafdb3423415f869c45ad6c59661a, '2026-06-25'),
(0x4c2b4480ef87458abc340e37ae728043, '2026-06-21'),
(0xbc9f655a26b945ed864ba93165353d5d, '2026-06-21');

-- --------------------------------------------------------

--
-- Structure de la table `categories`
--

CREATE TABLE `categories` (
  `id` tinyint(4) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `categories`
--

INSERT INTO `categories` (`id`, `name`) VALUES
(1, 'Cars'),
(2, 'Phones'),
(3, 'TV');

-- --------------------------------------------------------

--
-- Structure de la table `flyway_schema_history`
--

CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `flyway_schema_history`
--

INSERT INTO `flyway_schema_history` (`installed_rank`, `version`, `description`, `type`, `script`, `checksum`, `installed_by`, `installed_on`, `execution_time`, `success`) VALUES
(1, '1', 'initial migration', 'SQL', 'V1__initial_migration.sql', 1290928148, 'root', '2026-06-13 11:14:06', 386, 1);

-- --------------------------------------------------------

--
-- Structure de la table `orders`
--

CREATE TABLE `orders` (
  `id` bigint(20) NOT NULL,
  `customer_id` bigint(20) NOT NULL,
  `status` varchar(20) DEFAULT 'Pending' COMMENT 'it can be PENDING, PAID, FAILED, CANCELLED',
  `created_at` datetime NOT NULL DEFAULT current_timestamp(),
  `total_price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `orders`
--

INSERT INTO `orders` (`id`, `customer_id`, `status`, `created_at`, `total_price`) VALUES
(1, 22, 'PENDING', '2026-06-30 23:20:12', 217700.00),
(4, 3, 'PENDING', '2026-06-30 23:32:06', 702400.00),
(5, 22, 'PENDING', '2026-07-01 07:19:30', 4382000.00),
(6, 22, 'PENDING', '2026-07-01 18:06:09', 4382000.00),
(7, 22, 'PENDING', '2026-07-01 18:13:01', 4382000.00),
(8, 22, 'PENDING', '2026-07-01 18:14:20', 4382000.00),
(9, 22, 'PENDING', '2026-07-01 19:13:34', 4382000.00),
(11, 22, 'PENDING', '2026-07-01 21:49:01', 8044.00),
(12, 22, 'PENDING', '2026-07-01 21:50:11', 8004.00),
(13, 22, 'PENDING', '2026-07-01 21:51:56', 8004.00),
(17, 22, 'PENDING', '2026-07-01 21:55:55', 61.00),
(18, 22, 'PENDING', '2026-07-01 23:11:35', 9.54),
(19, 22, 'PENDING', '2026-07-01 23:25:54', 3771.54),
(20, 22, 'PENDING', '2026-07-02 07:09:10', 2443.34),
(21, 22, 'PENDING', '2026-07-02 07:11:37', 170706.50),
(22, 22, 'PENDING', '2026-07-02 08:55:50', 51501.34),
(23, 22, 'PENDING', '2026-07-02 17:28:03', 2443.34),
(24, 22, 'PENDING', '2026-07-02 17:29:37', 2443.34);

-- --------------------------------------------------------

--
-- Structure de la table `order_items`
--

CREATE TABLE `order_items` (
  `id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `product_id` bigint(20) NOT NULL,
  `unit_price` decimal(10,2) NOT NULL COMMENT 'unit price of each product at the time the order was place',
  `quantity` int(11) NOT NULL,
  `total_price` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `order_items`
--

INSERT INTO `order_items` (`id`, `order_id`, `product_id`, `unit_price`, `quantity`, `total_price`) VALUES
(1, 4, 1, 2300.00, 3, 6900.00),
(2, 4, 4, 1500.00, 10, 15000.00),
(3, 4, 16, 3500.00, 23, 80500.00),
(4, 4, 6, 100000.00, 6, 600000.00),
(5, 5, 5, 4000.00, 8, 32000.00),
(6, 5, 6, 100000.00, 33, 3300000.00),
(7, 5, 3, 50000.00, 21, 1050000.00),
(8, 6, 6, 100000.00, 33, 3300000.00),
(9, 6, 3, 50000.00, 21, 1050000.00),
(10, 6, 5, 4000.00, 8, 32000.00),
(11, 7, 3, 50000.00, 21, 1050000.00),
(12, 7, 5, 4000.00, 8, 32000.00),
(13, 7, 6, 100000.00, 33, 3300000.00),
(14, 8, 6, 100000.00, 33, 3300000.00),
(15, 8, 5, 4000.00, 8, 32000.00),
(16, 8, 3, 50000.00, 21, 1050000.00),
(17, 9, 6, 100000.00, 33, 3300000.00),
(18, 9, 3, 50000.00, 21, 1050000.00),
(19, 9, 5, 4000.00, 8, 32000.00),
(22, 11, 1, 11.00, 4, 44.00),
(23, 11, 5, 4000.00, 2, 8000.00),
(24, 12, 1, 1.00, 4, 4.00),
(25, 12, 5, 4000.00, 2, 8000.00),
(26, 13, 5, 4000.00, 2, 8000.00),
(27, 13, 1, 1.00, 4, 4.00),
(34, 17, 1, 10.00, 4, 40.00),
(35, 17, 5, 10.50, 2, 21.00),
(36, 18, 1, 1.22, 4, 4.88),
(37, 18, 5, 2.33, 2, 4.66),
(38, 19, 1, 567.55, 4, 2270.20),
(39, 19, 5, 750.67, 2, 1501.34),
(40, 20, 1, 235.50, 4, 942.00),
(41, 20, 5, 750.67, 2, 1501.34),
(42, 21, 2, 34000.00, 5, 170000.00),
(43, 21, 1, 235.50, 3, 706.50),
(44, 22, 3, 50000.00, 1, 50000.00),
(45, 22, 5, 750.67, 2, 1501.34),
(46, 23, 5, 750.67, 2, 1501.34),
(47, 23, 1, 235.50, 4, 942.00),
(48, 24, 1, 235.50, 4, 942.00),
(49, 24, 5, 750.67, 2, 1501.34);

-- --------------------------------------------------------

--
-- Structure de la table `products`
--

CREATE TABLE `products` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `description` longtext NOT NULL,
  `category_id` tinyint(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `products`
--

INSERT INTO `products` (`id`, `name`, `price`, `description`, `category_id`) VALUES
(1, 'SAMSUNG s25', 235.50, 'Model 2025', 2),
(2, 'Tesla', 34000.00, 'Electric car', 1),
(3, 'BMW', 50000.00, 'Voiture de courses', 1),
(4, 'LG', 67.64, 'Smart TV, ROKu', 3),
(5, 'iPhone 17', 750.67, 'Telephone le plus avance de chez APPLE', 2),
(6, 'Mercedez', 100000.00, 'Luxury car', 1),
(16, 'MOTOROLA 2022', 3500.00, '', 2);

-- --------------------------------------------------------

--
-- Structure de la table `profiles`
--

CREATE TABLE `profiles` (
  `id` bigint(20) NOT NULL,
  `bio` longtext DEFAULT NULL,
  `phone_number` varchar(15) DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `loyalty_points` int(10) UNSIGNED DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL DEFAULT 'USER'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `password`, `role`) VALUES
(1, 'Martin EFFA', 'EffaaMartin@example.com', 'jonsais934', 'USER'),
(2, 'ATEBA Francois', 'atebfranc@example.com', 'atebaliid', 'USER'),
(3, 'Tamon Kafka', 'kafka_test11@example.com', '123_maiNstreet22', 'USER'),
(4, 'Gaban Sean', 'edimo11@example.com', '8iNstreet22', 'USER'),
(18, 'TADMON Eric', 'tadmoneric@gmail.com', '$2a$10$cLg9XGOInHGOiJMmHrlQsuYgqYlrXVaX5XrJ2bhNNAAVpsHjcPTBG', 'USER'),
(21, 'User 2', 'user2@gmail.com', '$2a$10$CECAiFORVDOdSvJZmyXp5OlCy31zAkQtvK92X3ORWiDyNGAorYJmm', 'USER'),
(22, 'Roly say', 'rolysay@gmail.com', '$2a$10$iE1/mnomcFd4roY00/JABucXlY4JXHzjuX31iHlZo.p4o27.EIkGS', 'ADMIN');

-- --------------------------------------------------------

--
-- Structure de la table `wishlist`
--

CREATE TABLE `wishlist` (
  `product_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `wishlist`
--

INSERT INTO `wishlist` (`product_id`, `user_id`) VALUES
(1, 1),
(2, 1),
(2, 2),
(3, 1),
(3, 4),
(4, 2),
(4, 3),
(5, 2),
(6, 3);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `addresses`
--
ALTER TABLE `addresses`
  ADD PRIMARY KEY (`id`),
  ADD KEY `addresses_users_id_fk` (`user_id`);

--
-- Index pour la table `card_items`
--
ALTER TABLE `card_items`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `card_items_cart_product_unique_key` (`cart_id`,`product_id`),
  ADD KEY `card_items_products_id_fk` (`product_id`);

--
-- Index pour la table `carts`
--
ALTER TABLE `carts`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `flyway_schema_history`
--
ALTER TABLE `flyway_schema_history`
  ADD PRIMARY KEY (`installed_rank`),
  ADD KEY `flyway_schema_history_s_idx` (`success`);

--
-- Index pour la table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `orders_users_fk` (`customer_id`);

--
-- Index pour la table `order_items`
--
ALTER TABLE `order_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `order_items_orders_id_fk` (`order_id`),
  ADD KEY `order_items_products_id_fk` (`product_id`);

--
-- Index pour la table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_category` (`category_id`);

--
-- Index pour la table `profiles`
--
ALTER TABLE `profiles`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Index pour la table `wishlist`
--
ALTER TABLE `wishlist`
  ADD PRIMARY KEY (`product_id`,`user_id`),
  ADD KEY `fk_wishlist_on_user` (`user_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `addresses`
--
ALTER TABLE `addresses`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `card_items`
--
ALTER TABLE `card_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT pour la table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT pour la table `order_items`
--
ALTER TABLE `order_items`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT pour la table `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `addresses`
--
ALTER TABLE `addresses`
  ADD CONSTRAINT `addresses_users_id_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION;

--
-- Contraintes pour la table `card_items`
--
ALTER TABLE `card_items`
  ADD CONSTRAINT `card_items_carts_id_fk` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `card_items_products_id_fk` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE;

--
-- Contraintes pour la table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_users_fk` FOREIGN KEY (`customer_id`) REFERENCES `users` (`id`);

--
-- Contraintes pour la table `order_items`
--
ALTER TABLE `order_items`
  ADD CONSTRAINT `order_items_orders_id_fk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  ADD CONSTRAINT `order_items_products_id_fk` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

--
-- Contraintes pour la table `products`
--
ALTER TABLE `products`
  ADD CONSTRAINT `fk_category` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`) ON DELETE NO ACTION;

--
-- Contraintes pour la table `profiles`
--
ALTER TABLE `profiles`
  ADD CONSTRAINT `profiles_ibfk_1` FOREIGN KEY (`id`) REFERENCES `users` (`id`) ON DELETE NO ACTION;

--
-- Contraintes pour la table `wishlist`
--
ALTER TABLE `wishlist`
  ADD CONSTRAINT `fk_wishlist_on_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_wishlist_on_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
