-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 30 juin 2026 à 04:46
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
(4, 0x1ca06ce7e974420bb60b8d852a8e6f6e, 2, 6),
(5, 0x4c2b4480ef87458abc340e37ae728043, 3, 18),
(6, 0x017e96988e6c4fa0810267b644e4b78d, 2, 4),
(7, 0x1ca06ce7e974420bb60b8d852a8e6f6e, 4, 3),
(8, 0x1ca06ce7e974420bb60b8d852a8e6f6e, 1, 4),
(25, 0x31baafdb3423415f869c45ad6c59661a, 1, 3),
(26, 0x31baafdb3423415f869c45ad6c59661a, 2, 5);

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
(1, 'SAMSUNG s25', 2300.00, 'Model 2025', 2),
(2, 'Tesla', 34000.00, 'Electric car', 1),
(3, 'BMW', 50000.00, 'Voiture de courses', 1),
(4, 'LG', 1500.00, 'Smart TV, ROKu', 3),
(5, 'iPhone 17', 4000.00, 'Telephone le plus avance de chez APPLE', 2),
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
(19, 'Peter', 'petertest@example.com', '$2a$10$kdkyerlwUqSulxOlz/d2yOPHz2GjQCD3hIynuf8QziihwBrvhGDq2', 'USER');

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT pour la table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` tinyint(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `products`
--
ALTER TABLE `products`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT pour la table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

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
