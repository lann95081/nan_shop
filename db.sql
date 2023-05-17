drop database if exists nan_shop;
create database nan_shop;
use nan_shop;

create table `brand` (
brand_id int primary key auto_increment,
brand_name varchar(50)
);

create table `product_type` (
product_type_id int primary key auto_increment,
product_type_name varchar(50)
);

create table `product` (
product_id int primary key auto_increment,
product_name varchar(255),
price double,
`description` text,
img text,
amount int,
delete_status bit default 0,
brand_id int,
product_type_id int,
foreign key (brand_id) references `brand`(brand_id),
foreign key (product_type_id) references `product_type`(product_type_id)
);

create table `user` (
user_id int primary key auto_increment,
username varchar(25),
`password`varchar(15),
`name` varchar(50),
email varchar(50) unique,
address varchar(50),
gender bit(1),
phone varchar(15) unique,
delete_status bit default 0
);

create table `role` (
role_id int primary key auto_increment,
role_name varchar(45)
);

create table `user_role` (
id int primary key auto_increment,
user_id int,
role_id int,
foreign key (user_id) references `user`(user_id),
foreign key (role_id) references `role`(role_id)
);

create table `cart` (
cart_id int primary key auto_increment,
`date` varchar(50),
pay_status bit default 0,
user_id int,
foreign key(user_id) references `user`(user_id)
);

create table `cart_detail` (	
cart_detail_id int primary key auto_increment,
amonut int,
product_id int,
cart_id int,
foreign key (cart_id) references `cart`(cart_id),
foreign key (product_id) references `product`(product_id)
);