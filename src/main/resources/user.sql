INSERT INTO user_type VALUES ('bce1f07f-314d-4b12-ae19-2f65017ab019', 'Admin');
INSERT INTO user_type VALUES ('e4ad9289-aaf2-48e3-8faa-70686a255648', 'Nhân viên');


INSERT INTO user_permission(id, user_type_id, permission) VALUES ('da2cb086-110a-43c8-8a3a-b17e944a6989', 'bce1f07f-314d-4b12-ae19-2f65017ab019', 'CUSTOMER_MANAGEMENT'),
                                                                 ('c4766ad3-d8c7-483e-b308-5e493bb104da', 'bce1f07f-314d-4b12-ae19-2f65017ab019', 'USER_MANAGEMENT'),
                                                                 ('03d39375-3d9b-4876-97c0-b446034bada5', 'bce1f07f-314d-4b12-ae19-2f65017ab019', 'PRODUCT_MANAGEMENT'),
                                                                 ('2485a629-00b9-45bc-9f15-7c1d38fb8257', 'bce1f07f-314d-4b12-ae19-2f65017ab019', 'PROVIDER_MANAGEMENT'),
                                                                 ('c87a08ac-7080-4db8-aa2f-60dfa6d6e9f0', 'bce1f07f-314d-4b12-ae19-2f65017ab019', 'MESSAGE_MANAGEMENT'),
                                                                 ('64c9da4a-72aa-4606-913e-26e15d3e689d', 'e4ad9289-aaf2-48e3-8faa-70686a255648', 'PRODUCT_MANAGEMENT'),
                                                                 ('fbec860c-9248-11ee-b9d1-0242ac120002', 'bce1f07f-314d-4b12-ae19-2f65017ab019', 'ORDER_MANAGEMENT');

INSERT INTO `account` (`id`, `password`, `type`, `username`) VALUES
('1782564a-0926-4ffb-a5fb-78a4eb170b0d', '/xlrcORhmrfdRPjIIFibtA==', 'USER', 'tan'),
('5bf89df1-7026-4133-9955-ce9ec899955e', '/xlrcORhmrfdRPjIIFibtA==', 'USER', 'quan'),
('61927175-37c4-4f3c-a777-fb6bf6dcc917', '/xlrcORhmrfdRPjIIFibtA==', 'CUSTOMER', 'tancbe'),
('81187e78-4652-488b-9d28-ff48252ca0f8', '/xlrcORhmrfdRPjIIFibtA==', 'CUSTOMER', 'tannef'),
('951dce52-6227-41a8-867a-5a7797a62542', '/xlrcORhmrfdRPjIIFibtA==', 'CUSTOMER', 'quanla'),
('ad3be6b2-ab98-429b-9b66-51774fc41169', '/xlrcORhmrfdRPjIIFibtA==', 'CUSTOMER', 'thanh'),
('d8810188-55b7-4dc7-aa87-8a8f4ab7d873', '/xlrcORhmrfdRPjIIFibtA==', 'USER', 'admin'),
('eff5f9aa-5257-45ed-af39-de3c54f47750', '/xlrcORhmrfdRPjIIFibtA==', 'CUSTOMER', 'linhdo'),
('f9714802-2568-4cdc-ac6b-0de724a40af7', '/xlrcORhmrfdRPjIIFibtA==', 'CUSTOMER', 'thanh123'),
('ff93719c-74da-4012-a151-3c0ce43953cb', '/xlrcORhmrfdRPjIIFibtA==', 'USER', 'do');


INSERT INTO `user` (`id`, `address`, `birthdate`, `email`, `is_man`, `name`, `phone`, `account_id`, `user_type_id`) VALUES
('08fe9568-a31f-463f-842a-10b2862a5e82', 'HCM', '2002-07-17', 'linhkhanh1809@gmail.com', b'1', 'Nguyễn Linh Khánh', '0912428650', 'ff93719c-74da-4012-a151-3c0ce43953cb', 'e4ad9289-aaf2-48e3-8faa-70686a255648'),
('2d92b6da-61ce-460b-b103-4fa7a06930b3', 'BL', '2002-02-17', 'nhattan@gmail.com', b'1', 'Đinh Nhật Tân', '0838893378', '1782564a-0926-4ffb-a5fb-78a4eb170b0d', 'e4ad9289-aaf2-48e3-8faa-70686a255648'),
('76cf137b-bd69-4b95-a41c-4a3a97c7db3e', 'BD', '2002-11-15', 'thanhhuynh100@gmail.com', b'1', 'Huỳnh Tuấn Thanh', '0372737715', 'ad3be6b2-ab98-429b-9b66-51774fc41169', 'e4ad9289-aaf2-48e3-8faa-70686a255648'),
('7883a71b-7150-43ec-89a2-33f540f208d1', 'HCM', '2000-01-01', 'admin@gmail.com', b'1', 'admin', '0123456789', 'd8810188-55b7-4dc7-aa87-8a8f4ab7d873', 'bce1f07f-314d-4b12-ae19-2f65017ab019'),
('abd11e21-ba48-4150-a5fc-449bdba7dc97', 'HCM', '2002-02-11', 'anhquan1412@gmai.com', b'1', 'Lê Anh Quân', '0343409914', '5bf89df1-7026-4133-9955-ce9ec899955e', 'e4ad9289-aaf2-48e3-8faa-70686a255648');

insert into customer_type values('f999d28a-671b-4fbf-8f70-7f7d0492f239',0,'Bậc Đồng', 0);
insert into customer_type values('eb314449-5c2a-435e-bd39-991d7d83f209',2.5,'Bậc Bạc', 100);
insert into customer_type values('8045e3ed-b06d-479b-a263-61cf831211a0',5,'Bậc Vàng', 250);
insert into customer_type values('2b6bcb98-8f3d-48c0-b44f-9ba4379a1636',7.5,'Bậc Bạch Kim', 500);
insert into customer_type values('6f29d1ff-feca-432f-aa5e-d40bfc4fe956',10,'Bậc Kim Cương', 1000);

INSERT INTO `customer` (`id`, `address`, `birthdate`, `email`, `is_man`, `membership_point`, `name`, `phone`, `account_id`, `customer_type_id`) VALUES
('19c37e38-2030-4537-9f8c-105531d85a5f', 'Đồng Nai', '2002-01-31', 'tancbe@gmail.com', b'1', 0, 'Phan Hoàng Nhật Tân', '0237456981', '61927175-37c4-4f3c-a777-fb6bf6dcc917', 'f999d28a-671b-4fbf-8f70-7f7d0492f239'),
('1e1c094b-44db-4e2f-bec8-1dd1eb57c13f', 'Gia Lai', '2002-07-17', 'linhdo@gmail.con', b'1', 101, 'Nguyễn Linh Khánh', '0598713264', 'eff5f9aa-5257-45ed-af39-de3c54f47750', 'eb314449-5c2a-435e-bd39-991d7d83f209'),
('902bd9b1-ee7f-4f55-ab4a-d5576ad72b63', 'Bảo Lộc', '2003-01-17', 'tanbe@gmail.com', b'1', 0, 'Đinh Nhật Tân', '0359874632', '81187e78-4652-488b-9d28-ff48252ca0f8', 'f999d28a-671b-4fbf-8f70-7f7d0492f239'),
('c04532b8-dadf-47cc-8cfe-a33385d8d91c', 'TPHCM', '2002-12-14', 'quanal@gmail.com', b'1', 251, 'Lê Anh Quân', '0356608977', '951dce52-6227-41a8-867a-5a7797a62542', '8045e3ed-b06d-479b-a263-61cf831211a0'),
('c641a96d-4697-43b7-8383-74af4c5eb31b', 'Bình Dương', '2023-12-01', 'tuanthanh@gmail.com', b'1', 501, 'Huỳnh Tuấn Thanh', '0327658522', 'f9714802-2568-4cdc-ac6b-0de724a40af7', '2b6bcb98-8f3d-48c0-b44f-9ba4379a1636');
