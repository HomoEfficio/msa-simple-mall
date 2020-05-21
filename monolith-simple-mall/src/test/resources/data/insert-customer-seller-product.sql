insert into customer (name, email, phone, zip_no, road_addr_part1, road_addr_part2, addr_detail, login_id, password) values
('열글자로된고객이름일', 'aaaaaaaaaabbbbbbbbbbcccccccccc@dddddddd.eeeeeeeeee', '12345678901234567890', '12345', '도로명 주소 11', '도로명 주소 12', '상세주소 1', 'aaaaaaaaaabbbbbbbbbbcccccccccc', '{bcrypt}$2a$10$zii0rO5dHoHNoE9tzNQKbOUSfN1gXFYo2P5TljCwEwyB5S0I.9BaG'),
('열글자로된고객이름이', 'smile-customer@gmail.com', '12345678901234567890', '54321', '도로명 주소 21', '도로명 주소 22', '상세주소 2', 'smile-customer', '{bcrypt}$2a$10$YM6ki4N4Q6zLZmNR7IHgTOcGbLeYeJJHmo7KZs75ouovu3XcRSD12')
;

insert into seller (name, email, phone, login_id, password) values
('Seller01', 'user1@test.com', '010-1111-1111', 'Seller01', 'p123456'),
('Seller02', 'ab@t.c', '123456789', 'Seller02', 'passwd'),
('Seller03', 'aaaaaaaaaabbbbbbbbbbcccccccccc@dddddddd.eeeeeeeeee', '12345678901234567890', 'Seller03', 'abcde12345abcde12345')
;

select * from seller
;

insert into product (name, description, seller_id, manufacturer, price, count) values
('상품 101', '설명 와장창', 1, '다같은놈', 10000, 111L),
('상품 102', '설명 와장창', 1, '다같은놈', 20000, 222L),
('상품 103', '설명 와장창', 1, '다같은놈', 30000, 333L),
('상품 201', '설명 와장창', 2, '다같은놈', 10000, 111L),
('상품 202', '설명 와장창', 2, '다같은놈', 20000, 222L),
('상품 203', '설명 와장창', 2, '다같은놈', 30000, 333L),
('상품 301', '설명 와장창', 3, '다같은놈', 10000, 111L),
('상품 302', '설명 와장창', 3, '다같은놈', 20000, 222L),
('상품 303', '설명 와장창', 3, '다같은놈', 30000, 333L)
;
