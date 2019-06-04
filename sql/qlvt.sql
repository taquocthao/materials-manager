USE QLVT
GO

--
-- Cơ sở dữ liệu: QLVT
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng chinhanh
--

CREATE TABLE ChiNhanh (
  MACN char(10) PRIMARY KEY NOT NULL,
  ChiNhanh nvarchar(100) UNIQUE NOT NULL,
  DIACHI nvarchar(100) NOT NULL,
  SoDT varchar(10) NOT NULL
)
GO

-- --------------------------------------------------------

--
-- Đổ dữ liệu cho table ChiNhanh
--

INSERT INTO ChiNhanh VALUES('CN1', N'Chi nhánh 1' ,N'19, Nguyễn Hữu Thọ, Quận 7, Hồ Chí Minh', '0999999999')
INSERT INTO ChiNhanh VALUES('CN2', N'Chi nhánh 2' ,N'80, Trần Thái Tông ,Cầu Giấy, Hà Nội', '0888888888')

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng kho
--

CREATE TABLE Kho (
  MAKHO char(4) PRIMARY KEY NOT NULL,
  TENKHO nvarchar(30) UNIQUE NOT NULL,
  DIACHI nvarchar(70)  NOT NULL,
  MACN char(10)  NOT NULL,
  FOREIGN KEY(MACN) REFERENCES ChiNhanh(MACN)
) 
GO

INSERT INTO Kho VALUES('K1', N'Kho 1', N'Hồ Chí Minh', 'CN1')
INSERT INTO Kho VALUES('K2', N'Kho 2', N'Hà Nội', 'CN2')
-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng nhanvien
--

CREATE TABLE NhanVien (
  MANV int PRIMARY KEY identity(1,1) NOT NULL,
  HO nvarchar(40) NOT NULL,
  TEN nvarchar(40) NOT NULL,
  DIACHI nvarchar(40) NOT NULL,
  NGAYSINH datetime NOT NULL,
  LUONG float NOT NULL,
  MACN char(10) NOT NULL,
  CHECK(LUONG >= 800000),
  FOREIGN KEY(MACN) REFERENCES ChiNhanh(MACN)
) 
GO

INSERT INTO NhanVien VALUES( N'Tạ', N'Thảo', N'An Giang', '1996-10-17 00:00:00.000', 23000000.0, 'CN1')

INSERT INTO NhanVien VALUES( N'Thủy', N'Tiên', N'Bình Định', '1996-08-17 00:00:00.000', 9000000.0, 'CN2')

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng phatsinh
--

CREATE TABLE PhatSinh (
  PHIEU char(8) PRIMARY KEY NOT NULL,
  NGAY datetime NOT NULL,
  LOAI char(1)  NOT NULL,
  HOTENKH nvarchar(40)  NOT NULL,
  THANHTIEN float NOT NULL,
  MANV int NOT NULL,
  MAKHO char(4)  NOT NULL,
  FOREIGN KEY(MANV) REFERENCES NhanVien(MANV),
  FOREIGN KEY(MAKHO) REFERENCES Kho(MAKHO),
  CHECK(LOAI = 'N' or LOAI = 'X' or LOAI = 'T' or LOAI = 'C')
)
GO

INSERT INTO PhatSinh VALUES('P1', '2019-03-28', 'N', N'Kim Liên', 23000000.0, 1, 'K1')
INSERT INTO PhatSinh VALUES('P2', '2019-03-28', 'N', N'Thu Hương', 50000000.0, 2, 'K2')
----------------------------------------------------------

--
-- Cấu trúc bảng cho bảng vattu
--

CREATE TABLE VatTu (
  MAVT char(4) PRIMARY KEY NOT NULL,
  TENVT nvarchar(30) UNIQUE NOT NULL,
  DVT nvarchar(15)  NOT NULL
) 
GO

INSERT INTO VatTu VALUES('V001', N'Tivi', N'Cái')
INSERT INTO VatTu VALUES('V002', N'Tủ lạnh', N'Cái')
-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng ct_phatsinh
--

CREATE TABLE CT_PhatSinh (
  PHIEU char(8) NOT NULL,
  MAVT char(4) NOT NULL,
  SOLUONG int NOT NULL,
  DONGIA float NOT NULL,
  CHECK (SOLUONG > 0 AND DONGIA > 0),
  FOREIGN KEY(PHIEU) REFERENCES PhatSinh(PHIEU),
  FOREIGN KEY(MAVT) REFERENCES VatTu(MAVT)
)
GO

INSERT INTO CT_PhatSinh VALUES('P0000001', 'V001', 2, 5000000)

-----------------------------------------------

create table APP_USER
(
  USER_ID           BIGINT not null,
  MANV				int,
  USER_NAME         VARCHAR(36) not null,
  ENCRYTED_PASSWORD VARCHAR(128) not null,
  ENABLED           BIT not null 
) ;
--  
alter table APP_USER
  add constraint APP_USER_PK primary key (USER_ID);
 
alter table APP_USER
  add constraint APP_USER_UK unique (USER_NAME);
 
alter table APP_USER add constraint fk_AU_NV foreign key(MANV) references dbo.NhanVien(MANV)

-- Create table
create table APP_ROLE
(
  ROLE_ID   BIGINT not null,
  ROLE_NAME VARCHAR(30) not null
) ;
--  
alter table APP_ROLE
  add constraint APP_ROLE_PK primary key (ROLE_ID);
 
alter table APP_ROLE
  add constraint APP_ROLE_UK unique (ROLE_NAME);
 
 
-- Create table
create table USER_ROLE
(
  ID      BIGINT not null,
  USER_ID BIGINT not null,
  ROLE_ID BIGINT not null
);
--  
alter table USER_ROLE
  add constraint USER_ROLE_PK primary key (ID);
 
alter table USER_ROLE
  add constraint USER_ROLE_UK unique (USER_ID, ROLE_ID);
 
alter table USER_ROLE
  add constraint USER_ROLE_FK1 foreign key (USER_ID)
  references APP_USER (USER_ID);
 
alter table USER_ROLE
  add constraint USER_ROLE_FK2 foreign key (ROLE_ID)
  references APP_ROLE (ROLE_ID);
 
  
     
-- Used by Spring Remember Me API.  
CREATE TABLE Persistent_Logins (
 
    username varchar(64) not null,
    series varchar(64) not null,
    token varchar(64) not null,
    last_used Datetime not null,
    PRIMARY KEY (series)
     
);