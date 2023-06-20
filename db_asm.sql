use master
go
create database chicken_app
go

use chicken_app
go

create table account
(
	username nvarchar(45) primary key not null,
	fullname nvarchar(100) not null,
	email varchar(45) not null,
	nphone varchar(45),
	[password] varchar(200),
	[address] nvarchar(45),
	photo varchar(45),
	[role] bit not null,
)
go

create table category
(
	categoryId nvarchar(45) primary key not null,
	categoryName nvarchar(45) not null,
	notes nvarchar(100)
)

create table [product]
(
	productId int identity(1,1) primary key  not null,
	product_name nvarchar(45) not null,
	price float not null,
	categoryId nvarchar(45) foreign key references category(categoryId) not null,
	createDate date not null,
	describe nvarchar(500) not null,
	quantity int not null,
	photo varchar(200) not null,
	khuyenmai nchar(20),
)
go


create table [order]
(
	id int primary key identity not null,
	[userid] nvarchar(45) foreign key references account(username) not null,
	total_price float not null,
	nphone varchar(45) not null,
	orderStatus int,
	[date] date not null,
	[address] nvarchar(45) not null
)
go

create table order_detail
(
	id int primary key not null identity,
	orderId int foreign key references [order](id) not null,
	productId int foreign key references [product](productId) not null,
	quantity int not null,
	price float not null
)
go


insert into account(username, fullname,email,nphone,[password],[address],photo, [role]) values
('truongdp',N'Đỗ Nguyễn Phi Trường', 'dotruong0108t@gmail.com','0375512356','111',N'Quận 12, Tp Hồ Chí Minh','', 0),
('thienlc',N'Lê Chí Thiên', 'thienlc06052000@gmail.com','0234543445','12345', N'Nguyễn Văn Công, P.3, Gò Vấp, Tp Hồ Chí Minh', '', 1)
go

insert into category(categoryId,categoryName, notes) values
('GC',N'Fried Chicken', ''),
('GN',N'BBQ Chicken', ''),
('MI',N'Pasta', ''),
('KTC',N'French Fries', ''),
('GV',N'Spice', ''),
('NU',N'Drink', '')
go

insert into product(product_name,price,categoryId,createDate,quantity,describe,photo) values
(N'Cánh gà chiên giòn sốt ngũ vị hương',120000,'GC','2023-05-20',4,N'Vị chua ngọt từ nước sốt, trộn cùng dưa leo tươi và mè sẽ khiến bạn không thể ngừng đũa','https://vncooking.com/files/cuisine/2018/12/16/canh-ga-chien-gion-sot-me-pcws.jpg'),
(N'Gà nướng tiêu đen nguyên con',95000,'GN','2023-05-20',6,N'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','https://cdn.tgdd.vn/Files/2017/12/13/1050073/cach-lam-ga-nuong-tieu-sieu-ngon-bang-lo-nuong-thuy-tinh.jpg'),
(N'Cánh gà chiên',600000,'GC','2023-05-20',10,N'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','https://vitinfo.com.vn/wp-content/uploads/2018/06/canh-ga-chien-nuoc-mam.jpg'),
(N'Chai COCA 1.5L',39000,'NU','2023-05-20',12,N'Nước uống có gas','https://thegioidouong.net/wp-content/uploads/2015/03/coca-binh-1-5lit-300x300.jpg'),
(N'Cánh gà sốt phô mai',93000,'GC','2023-05-20',10,N'Vị chua ngọt từ nước sốt, trộn cùng dưa leo tươi và mè sẽ khiến bạn không thể ngừng đũa','https://thucthan.com/media/2019/04/canh-ga-sot-chua-ngot/canh-ga-sot-chua-ngot.jpg'),
(N'Combo Gà chiên giòn & Khoai tây chiên',130000,'GC','2023-05-20',5,N'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','https://bepxua.vn/wp-content/uploads/2021/06/ga-ran.jpg'),
(N'Gà xiên que',35000,'GN','2023-05-20',20,N'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','https://anhminhpqp.com/wp-content/uploads/2019/04/G%C3%A0-xi%C3%AAn-que-2-768x511.jpg'),
(N'Xiên gà nướng rau củ quả',65000,'GN','2023-05-20',10,N'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','https://thucthan.com/media/2018/07/ga-nuong/thumbnails/ga-nuong-rau-cu-500.jpg'),
(N'Mì Ý xốt cà xúc xích',41000,'MI','2023-05-20',10,N'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.','https://static.kfcvietnam.com.vn/images/items/lg/MY-Y-POP.jpg?v=gqN5Z3'),
(N'Cánh gà 490g',48000,'GC','2023-05-20',10,N'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.','https://cdn.eva.vn/upload/2-2020/images/2020-04-17/canh-ga-chien-nuoc-mam-gion-ngon-dua-com-4-1587092878-107-width512height357.jpg'),
(N'Khoai tây chiên',19000,'KTC','2023-05-20',10,N'Khoai tây chiên ăn thêm','https://static.kfcvietnam.com.vn/images/items/lg/FF-R.jpg?v=gqN5Z3'),
(N'Mì Ý sốt bò băm',49000,'MI','2023-05-20',10,N'Mì Ý sốt bò ngon tuyệt hảo','https://cdn-www.vinid.net/5656c4a6-mi-y-xot-bo-bam-8.jpg')
go

insert into [order]([userid],total_price,nphone,orderStatus,[date],[address]) values
('truongdp',240000,'0375512356',1,'2023-05-18',N'Quận 12, Tp Hồ Chí Minh')
go

insert into order_detail(orderId,productId,quantity,price) values
(1, 1, 2,120000)
go

--Lệnh cập nhật số lượng tồn trong product khi đặt hàng--
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create trigger [dbo].[Tr_SoLuongTon_Hang]
on [dbo].[order_detail]
for insert
as 
begin
	update product
	set product.quantity = product.quantity - (
		select quantity
		from inserted
		where inserted.productId =product.productId
	)
	from order_detail, inserted 
	where	product.productId = inserted.productId	
end
GO
ALTER TABLE [dbo].[order_detail] ENABLE TRIGGER [Tr_SoLuongTon_Hang]
GO
--Lệnh cập nhật số lượng tồn trong product khi xóa order_detail--
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create trigger [dbo].[Tr_SoLuongTon_XoaChiTietHoaDon]
on [dbo].[order_detail]
for delete
as
begin
	update product
	set product.quantity = product.quantity + (select quantity from deleted where deleted.productId = product.productId)
	from  deleted
	where product.productId = deleted.productId
end	
GO
ALTER TABLE [dbo].[order_detail] ENABLE TRIGGER [Tr_SoLuongTon_XoaChiTietHoaDon]
GO
--Thành tiền của CT hóa đơn--
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create trigger [dbo].[Tr_ThanhTien_ChiTietHoaDon]
on [dbo].[order_detail]
for insert, update
as
if((select productId from inserted) is not null)
begin
	update order_detail
	set price=order_detail.quantity * product.price from product,(select productId,id from inserted) as I
	where product.productId=order_detail.productId and order_detail.productId=I.productId and order_detail.id=I.id
end
GO
ALTER TABLE [dbo].[order_detail] ENABLE TRIGGER [Tr_ThanhTien_ChiTietHoaDon]
GO

/******Tổng tiền order khi thêm order_detail     ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create trigger [dbo].[Tr_TongTienHoaDon]
on [dbo].[order_detail]
for insert, update
as
if((select orderid from inserted)is not null)
begin
	update [order]
	set total_price=(select SUM(price) from order_detail
	where order_detail.orderid=[order].id)
	from (select orderid from inserted) as I
	where [order].id=I.orderid
end
GO
ALTER TABLE [dbo].[order_detail] ENABLE TRIGGER [Tr_TongTienHoaDon]
GO
/****** Tổng tiền order khi xóa order_detail  ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create trigger [dbo].[Tr_TongTienHoaDon_XoaChiTietHoaDon]
on [dbo].[order_detail]
for delete
as
if((select orderid from deleted)is not null)
begin
	update [order]
	set total_price=(select SUM(price) from order_detail
	where order_detail.orderid=[order].id)
	from (select orderid from deleted) as I
	where [order].id=I.orderid
end
GO
ALTER TABLE [dbo].[order_detail] ENABLE TRIGGER [Tr_TongTienHoaDon_XoaChiTietHoaDon]
GO

--insert order--
insert into [order](nphone,[date],[address]) values
('0375512356','2023-06-02','Quận 12, TP HCM')
go
insert into [order_detail](orderid,productid,quantity) values
(2, 14,2)
go
select * from order_detail
go
select * from product
go
select * from [order]
go


drop table order_detail
drop table [order]
drop table account
