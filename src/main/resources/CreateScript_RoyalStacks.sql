DROP SCHEMA IF EXISTS royalstacks;
CREATE SCHEMA IF NOT EXISTS `royalstacks` DEFAULT CHARACTER SET utf8 ;
USE `royalstacks` ;

CREATE USER IF NOT EXISTS royalstacksuser@localhost IDENTIFIED BY 'royalstackspassword';
GRANT ALL PRIVILEGES ON royalstacks . * TO royalstacksuser@localhost;