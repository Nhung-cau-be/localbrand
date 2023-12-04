INSERT INTO user_type VALUES ('bce1f07f-314d-4b12-ae19-2f65017ab019', 'Admin');
INSERT INTO user_type VALUES ('e4ad9289-aaf2-48e3-8faa-70686a255648', 'Nhân viên');


INSERT INTO user_permission(id, user_type_id, permission) VALUES ('da2cb086-110a-43c8-8a3a-b17e944a6989', 'bce1f07f-314d-4b12-ae19-2f65017ab019', 'CUSTOMER_MANAGEMENT'),
                                                                 ('c4766ad3-d8c7-483e-b308-5e493bb104da', 'bce1f07f-314d-4b12-ae19-2f65017ab019', 'USER_MANAGEMENT'),
                                                                 ('03d39375-3d9b-4876-97c0-b446034bada5', 'bce1f07f-314d-4b12-ae19-2f65017ab019', 'PRODUCT_MANAGEMENT'),
                                                                 ('2485a629-00b9-45bc-9f15-7c1d38fb8257', 'bce1f07f-314d-4b12-ae19-2f65017ab019', 'PROVIDER_MANAGEMENT'),
                                                                 ('c87a08ac-7080-4db8-aa2f-60dfa6d6e9f0', 'bce1f07f-314d-4b12-ae19-2f65017ab019', 'MESSAGE_MANAGEMENT'),
                                                                 ('64c9da4a-72aa-4606-913e-26e15d3e689d', 'e4ad9289-aaf2-48e3-8faa-70686a255648', 'PRODUCT_MANAGEMENT'),
                                                                 ('fbec860c-9248-11ee-b9d1-0242ac120002', 'bce1f07f-314d-4b12-ae19-2f65017ab019', 'ORDER_MANAGEMENT');

INSERT INTO account(id, password, type, username) VALUES ('d8810188-55b7-4dc7-aa87-8a8f4ab7d873', '/xlrcORhmrfdRPjIIFibtA==', 'USER', 'admin');
INSERT INTO user(id, address, birthdate, email, is_man, name, phone, account_id, user_type_id)
    VALUES ('7883a71b-7150-43ec-89a2-33f540f208d1', 'HCM', '2000-01-01', 'admin@gmail.com', 1, 'admin', '0123456789', 'd8810188-55b7-4dc7-aa87-8a8f4ab7d873', 'bce1f07f-314d-4b12-ae19-2f65017ab019')