USE QLVT
GO

-- lấy danh sách vật tư
CREATE PROC usp_MaterialsGetALL
AS
BEGIN
	SET NOCOUNT ON
	SELECT * 
	FROM VatTu AS VT
	ORDER BY VT.MAVT ASC
END
GO

CREATE PROC usp_MaterialsGetForEachPage
(
	@start int = 0,
	@limit int = 11
)
AS
BEGIN
	SELECT * 
	FROM VatTu
	ORDER BY MAVT ASC
	OFFSET (@start) ROW
	FETCH NEXT (@limit) ROWS ONLY
END
GO

-- Mã vật tư tự tăng
ALTER PROC usp_MaterialIdAutoASC
	@MAVT char(4) OUT
AS
BEGIN

	SET @MAVT = 'V001';
	
	DECLARE @Index int;
	SET @Index = 1;

	WHILE EXISTS (SELECT VT.MAVT FROM VatTu AS VT WHERE VT.MAVT = @MAVT)
	BEGIN
		SET @Index = @Index + 1;
		SET @MAVT = 'V' + REPLICATE('0', 3 - LEN(CAST(@Index AS varchar))) + CAST(@Index AS varchar);
	END

	PRINT @MAVT;
END
GO

--DECLARE @MAVT CHAR(4)
--EXEC usp_MaterialIdAutoASC @MAVT OUT
--SELECT @MAVT

-- thêm vật tư
ALTER PROC usp_MaterialAdd
( 
	@TENVT nvarchar(30), 
	@DVT nvarchar(15),
	@IsSuccess int OUT
)
AS
BEGIN
	
	SET XACT_ABORT ON
	BEGIN TRANSACTION
		DECLARE @MAVT CHAR(4);
		
		EXEC dbo.usp_MaterialIdAutoASC @MAVT OUT

		INSERT INTO VatTu(MAVT, TENVT, DVT)
		VALUES (@MAVT, @TENVT, @DVT)
		IF @@ROWCOUNT = 0
		BEGIN
			SET @IsSuccess = 0; -- thêm thất bại
		END
		ELSE
		BEGIN
			SET @IsSuccess = 1; -- thêm thành công
		END
	COMMIT
	RETURN @IsSuccess;
END
GO

-- cập nhật thông tin vật tư
ALTER PROC usp_MaterialUpdate
(
	@MAVT char(4),
	@TENVT nvarchar(30), 
	@DVT nvarchar(15),
	@IsSuccess int OUT
)
AS
BEGIN

	SET XACT_ABORT ON
	BEGIN TRANSACTION

		UPDATE VatTu
		SET TENVT = @TENVT, DVT = @DVT
		WHERE MAVT = @MAVT

		IF @@ROWCOUNT = 0
		BEGIN
			SET @IsSuccess = 0; -- cập nhật thất bại
		END
		ELSE
		BEGIN
			SET @IsSuccess = 1; -- cập nhật thành công
		END
	COMMIT

	RETURN @IsSuccess;
END
GO

-- xóa vật tư
ALTER PROC usp_MaterialDelete 
( 
	@MAVT CHAR(4),
	@IsSuccess INT OUT
)
AS
BEGIN

	DELETE FROM VatTu WHERE MAVT = @MAVT;

	IF @@ROWCOUNT = 0
	BEGIN
		SET @IsSuccess = 0; -- xoas thất bại
	END
	ELSE
	BEGIN
		SET @IsSuccess = 1; --  xóa thành công
	END

	RETURN @IsSuccess;
END
GO
