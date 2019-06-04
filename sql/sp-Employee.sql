USE QLVT
GO

-- lấy danh sách nhân viên
CREATE PROC usp_EmployeesGetAll
AS
BEGIN
	SELECT * FROM NhanVien;
END
GO
--EXECUTE usp_GetAllEmployees;

-- tìm nhân viên theo mã
CREATE PROC usp_EmployeeFindById
	@MANV int = 0,
	@HO nvarchar(40) OUT,
	@TEN nvarchar(40) OUT,
	@DIACHI nvarchar(40) OUT,
	@NGAYSINH datetime OUT,
	@LUONG float OUT,
	@MACN char(10) OUT
AS
BEGIN
	SELECT @HO = HO, @TEN = TEN, @DIACHI  = DIACHI, @NGAYSINH = NGAYSINH, @LUONG = LUONG,
			@MACN = MACN
	FROM NhanVien
	WHERE MANV = @MANV;
END
GO

CREATE PROC usp_EmployeeGetForEachPage
(
	@start int = 0, 
	@limit int = 11
)
AS
BEGIN
	SET NOCOUNT ON
	SELECT *
	FROM NhanVien  
	ORDER BY MANV
	OFFSET (@start) ROW
	FETCH NEXT (@limit) ROWS ONLY
END
GO
--EXEC usp_FindEmployeeById 14;

-- Kiểm tra ID Nhân viên có tồn tại
CREATE PROC usp_EmployeeCheckIDExists
	@MANV int
AS
BEGIN
	SET NOCOUNT ON
	-- kiểm tra trong bảng hiện tại
	IF EXISTS(SELECT 1 FROM NhanVien WHERE MANV = @MANV)
		RETURN 1; -- nếu có tồn tại mã nhân viên này
	-- Kiểm tra trong phân mảnh còn lại
	IF EXISTS(SELECT 1 FROM LINK.QLVT.dbo.NhanVien AS NV WHERE NV.MANV = @MANV)
		RETURN 1;
	
	RETURN 0; -- trả về 0 nếu không tồn tại mã nhân viên này
END
GO

-- thêm nhân viên
CREATE PROC usp_EmployeeAdd
(
	--@MANV int,
	@HO nvarchar(40),
	@TEN nvarchar(40),
	@DIACHI nvarchar(40),
	@NGAYSINH datetime,
	@LUONG float,
	@MACN char(10),
	@MANV int OUT
)
AS
BEGIN

	SET NOCOUNT ON

	SET XACT_ABORT ON
	--SET IDENTITY_INSERT dbo.NhanVien ON

	BEGIN TRANSACTION
		
		INSERT INTO NhanVien( HO, TEN, DIACHI, NGAYSINH, LUONG, MACN)
		VALUES
		(
			@HO, @TEN, @DIACHI, @NGAYSINH, @LUONG, @MACN
		)


	COMMIT
	SELECT @MANV = SCOPE_IDENTITY()
	--SET IDENTITY_INSERT dbo.NhanVien OFF
END
GO

-- Cập nhật nhân viên
CREATE PROC usp_EmployeeUpdate
(
	@MANV int,
	@HO nvarchar(40),
	@TEN nvarchar(40),
	@DIACHI nvarchar(40),
	@NGAYSINH datetime,
	@LUONG float,
	@MACN char(10),
	@SUCCESS int OUT
) 
AS
BEGIN
	SET XACT_ABORT ON
	BEGIN TRANSACTION
		UPDATE dbo.NhanVien
		SET
			HO = @HO,
			TEN = @TEN,
			DIACHI = @DIACHI,
			NGAYSINH = @NGAYSINH,
			LUONG = @LUONG,
			MACN = @MACN
		WHERE MANV = @MANV
		IF @@ROWCOUNT = 0
		BEGIN
			SET @SUCCESS = 0;
		END
		ELSE
		BEGIN
			SET @SUCCESS = 1;
		END
	COMMIT
	
	SELECT @SUCCESS;
END
GO
-- xóa nhân viên
CREATE PROC usp_EmployeeDelete
	@MANV int,
	@SUCCESS int OUT
AS
BEGIN
	SET XACT_ABORT ON
	BEGIN TRANSACTION
		DELETE FROM NhanVien WHERE MANV = @MANV
	COMMIT

	IF EXISTS(SELECT 1 FROM NhanVien WHERE MANV = @MANV)
	BEGIN
		SET @SUCCESS = 0; -- xoa that bai
	END
	ELSE
	BEGIN
		SET @SUCCESS = 1; --xoa thanh cong
	END
	
	SELECT @SUCCESS;

END

-- EXEC usp_DeleteEmployee 15