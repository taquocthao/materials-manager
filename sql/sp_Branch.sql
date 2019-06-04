USE QLVT
GO

CREATE PROC usp_GetBranchs
AS
BEGIN
	SET NOCOUNT ON
	select * from ChiNhanh;
END
GO