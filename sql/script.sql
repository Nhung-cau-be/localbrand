CREATE DATABASE localbrand;
use localbrand;

CREATE TABLE cart (
  id varchar(50) primary key,
  customer_id int
);
CREATE TABLE cart_item (
   id varchar(50) primary key,
   cart_id varchar(50),
   product_sku_id varchar(50),
   quantity int
);
CREATE TABLE `order` (
     id varchar(50) primary key,
     customer_id varchar(50),
     code varchar(10),
     created_date   datetime,
     customer_name varchar(50),
     status   varchar(20),
     phone    char(20),
     address    varchar(100),
     subtotal   bigint,
     discount    bigint,
     total    bigint
);
CREATE TABLE order_item (
    id varchar(50) primary key,
    order_id varchar(50),
    product_sku_id varchar(50),
    quantity int,
    price int,
    discount_price int
);
CREATE TABLE product_sku (
     id varchar(50) primary key,
     product_id    varchar(20),
     code varchar(10),
     is_deleted  bit,
     quantity    int
);
CREATE TABLE product_attribute_detail (
      id varchar(50) primary key,
      product_sku_id    varchar(50),
      product_attribute_value_id varchar(50)
);
CREATE TABLE product_attribute_value (
     id varchar(50) primary key,
     product_attribute_id varchar(50),
     value	varchar(50)
);
CREATE TABLE product_attribute (
       id varchar(50) primary key,
       code	varchar(10),
       name	varchar(50)
);
CREATE TABLE customer (
      id varchar(50) primary key,
      customer_type_id 	varchar(50),
      account_id    varchar(50),
      name   varchar(50),
      phone    char(10),
      is_man    bit,
      birthdate    date,
      address    varchar(50),
      email    char(30),
      membership_point    int
);
CREATE TABLE customer_type (
    id varchar(50) primary key,
    name    varchar(100),
    standard_point    int,
    discount_percent   float
);
CREATE TABLE account (
     id varchar(50) primary key,
     username    varchar(50),
     password    varchar(50),
     type    varchar(50)
);
CREATE TABLE product (
     id varchar(50) primary key,
     product_group_id     varchar(50),
     code    varchar(10),
     name   varchar(50),
     main_image_url   varchar(100),
     price    int,
     discount_price    int,
     discount_percent    int,
     `describe`   varchar(200),
     is_deleted    bit,
     provider_id varchar(50)
);
CREATE TABLE product_group (
   id varchar(50) primary key,
   category_id    varchar(50),
   name   varchar(20)
);
CREATE TABLE `image` (
     id varchar(50) primary key,
     product_id    varchar(50),
     url	varchar(100)
);
CREATE TABLE collection_item (
    id varchar(50) primary key,
    collection_id    varchar(50),
    product_id		varchar(50)
);
CREATE TABLE collection (
    id varchar(50) primary key,
    name   varchar(50)
);
CREATE TABLE category (
    id varchar(50) primary key,
    name   varchar(20)
);
CREATE TABLE provider (
    id varchar(50) primary key,
    code varchar(10),
    name   varchar(50),
    address  varchar(100)
);
CREATE TABLE `user` (
    id varchar(50) primary key,
    user_type_id 	varchar(50),
    account_id    varchar(50),
    name   varchar(50),
    phone    char(10),
    is_man    bit,
    birthdate    date,
    address    varchar(50),
    email    char(30)
);
CREATE TABLE user_type (
    id varchar(50) primary key,
    name    varchar(100)
);