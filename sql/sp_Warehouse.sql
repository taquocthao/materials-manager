USE QLVT
GO

CREATE PROC usp_WarehouseGetAll
( 
	@start int = 0,
	@limit int = 10
)
AS
BEGIN
	SELECT *
	FROM Kho k
	ORDER BY k.MAKHO ASC
	OFFSET (@start) ROWS
	FETCH NEXT (@limit) ROWS ONLY
END
GO

-- EXEC usp_WarehouseGetAll 0, 10

ALTER PROC usp_WarehouseAutoAscID
	@MAKHO char(4) OUT
AS
BEGIN
	DECLARE @index INT = 1;
	SET @MAKHO =  'K001';
	WHILE EXISTS (SELECT MAKHO FROM Kho WHERE MAKHO = @MAKHO)
	BEGIN
		SET @index = @index + 1;
		SET @MAKHO = 'K' + REPLICATE('0', 3 - LEN(CAST(@index AS varchar))) + CAST(@index AS varchar);
	END
	PRINT @MAKHO;
END
GO
--DECLARE @MAKHO char(4)
--EXEC usp_WarehouseAutoAscID @MAKHO
--PRINT @MAKHO

CREATE PROC usp_WarehouseAdd
(
	@TENKHO nvarchar(30),
	@DIACHI nvarchar(70),
	@MACN char(10),
	@IsSuccess int OUT
)
AS
BEGIN
	

	SET XACT_ABORT ON
	BEGIN TRANSACTION

		DECLARE @MAKHO char(4);
		EXEC usp_WarehouseAutoAscID @MAKHO OUT

		INSERT INTO Kho(MAKHO, TENKHO, DIACHI, MACN)
		VALUES(@MAKHO, @TENKHO, @DIACHI, @MACN);

		IF @@ROWCOUNT = 0
		BEGIN
			SET @IsSuccess = 0;
		END
		ELSE
		BEGIN
			SET @IsSuccess = 1;
		END

	COMMIT
	PRINT @IsSuccess
END
GO

--
CREATE PROC usp_WarehouseUpdate
(
	@MAKHO char(4),
	@TENKHO nvarchar(30),
	@DIACHI nvarchar(70),
	@MACN char(10),
	@IsSuccess int OUT
)
AS
BEGIN
	
	SET XACT_ABORT ON
	BEGIN TRANSACTION

		UPDATE Kho
		SET TENKHO = @TENKHO, DIACHI = @DIACHI, MACN = @MACN
		WHERE MAKHO = @MAKHO;

		IF @@ROWCOUNT = 0
		BEGIN
			SET @IsSuccess = 0;
		END
		ELSE
		BEGIN
			SET @IsSuccess = 1;
		END

	COMMIT

	PRINT @IsSuccess;
END	
GO

-- delete 

CREATE PROC usp_WarehouseDelete
	@MAKHO char(4),
	@IsSuccess int OUT
AS
BEGIN
	DELETE FROM Kho WHERE MAKHO = @MAKHO;

	IF @@ROWCOUNT = 0
	BEGIN
		SET @IsSuccess = 0;
	END
	ELSE
	BEGIN
		SET @IsSuccess = 1;
	END

	PRINT @IsSuccess;

END
GO