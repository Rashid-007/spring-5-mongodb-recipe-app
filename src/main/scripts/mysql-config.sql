
## Use to run mysql db docker image, optional if you're not using a local mysqldb
# docker run --name throne-mysql -p 3306:3306 -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql

# connect to mysql and run as root user
#Create Databases
CREATE DATABASE throne_dev;
CREATE DATABASE throne_prod;

#Create database service accounts
CREATE USER 'throne_dev_user'@'localhost' IDENTIFIED BY 'thronedev';
CREATE USER 'throne_prod_user'@'localhost' IDENTIFIED BY 'throneprod';
CREATE USER 'throne_dev_user'@'%' IDENTIFIED BY 'thronedev';
CREATE USER 'throne_prod_user'@'%' IDENTIFIED BY 'throneprod';

#Database grants
GRANT SELECT ON throne_dev.* to 'throne_dev_user'@'localhost';
GRANT INSERT ON throne_dev.* to 'throne_dev_user'@'localhost';
GRANT DELETE ON throne_dev.* to 'throne_dev_user'@'localhost';
GRANT UPDATE ON throne_dev.* to 'throne_dev_user'@'localhost';
GRANT SELECT ON throne_prod.* to 'throne_prod_user'@'localhost';
GRANT INSERT ON throne_prod.* to 'throne_prod_user'@'localhost';
GRANT DELETE ON throne_prod.* to 'throne_prod_user'@'localhost';
GRANT UPDATE ON throne_prod.* to 'throne_prod_user'@'localhost';
GRANT SELECT ON throne_dev.* to 'throne_dev_user'@'%';
GRANT INSERT ON throne_dev.* to 'throne_dev_user'@'%';
GRANT DELETE ON throne_dev.* to 'throne_dev_user'@'%';
GRANT UPDATE ON throne_dev.* to 'throne_dev_user'@'%';
GRANT SELECT ON throne_prod.* to 'throne_prod_user'@'%';
GRANT INSERT ON throne_prod.* to 'throne_prod_user'@'%';
GRANT DELETE ON throne_prod.* to 'throne_prod_user'@'%';
GRANT UPDATE ON throne_prod.* to 'throne_prod_user'@'%';