CREATE TABLE `histories`
(
    `id`      bigint(20) NOT NULL,
    `user_id` bigint(20) NOT NULL,
    `action_date`    timestamp NULL,
    `type`    varchar(255) DEFAULT NULL,
    `info`    varchar(255) DEFAULT NULL
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;

ALTER TABLE `histories`
    ADD PRIMARY KEY (`id`);

ALTER TABLE `histories`
    MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

ALTER TABLE `histories`
    ADD KEY `histories_user_id` (`user_id`);

ALTER TABLE `histories`
    ADD CONSTRAINT `histories_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
;
