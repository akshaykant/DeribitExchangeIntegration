CREATE DATABASE IF NOT EXISTS `copperdb` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `copperdb`;

DROP TABLE IF EXISTS `user_balance`;

CREATE TABLE IF NOT EXISTS `user_balance`
(
    `id`               int NOT NULL ,
    `client_id`        varchar(45) NOT NULL ,
    `currency`         varchar(45) NOT NULL ,
    `account_balance`  double  NULL ,
    `reserved_balance` double  NULL ,
    `initial_margin`   double  NULL ,
    `margin_balance`   double  NULL ,
    `deposit_address`  varchar(256)  NULL ,

    PRIMARY KEY (`id`)
    );


COMMIT;