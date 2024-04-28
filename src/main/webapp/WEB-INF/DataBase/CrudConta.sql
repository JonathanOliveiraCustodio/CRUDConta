USE master
CREATE DATABASE CRUDConta
GO
USE CRUDConta
GO
CREATE TABLE contaBancaria(
nome_cliente	VARCHAR(250)	NOT NULL,
num_conta		INT				NOT NULL,
saldo			DECIMAL(7, 2)	NOT NULL,
PRIMARY KEY (num_conta)
)
GO
CREATE TABLE contaPoupanca(
num_conta		INT				NOT NULL,
dia_rendimento	INT				NOT NULL,
PRIMARY KEY (num_conta),
FOREIGN KEY (num_conta) REFERENCES contaBancaria(num_conta)
)
Go
CREATE TABLE contaEspecial(
num_conta		INT				NOT NULL,
limite			DECIMAL(7, 2)	NOT NULL,
PRIMARY KEY (num_conta),
FOREIGN KEY (num_conta) REFERENCES contaBancaria(num_conta)
)
GO
CREATE TRIGGER t_saque ON contaBancaria
AFTER UPDATE
AS
BEGIN
	DECLARE @num_conta INT
	DECLARE @limite DECIMAL(7, 2)
	DECLARE @saldo DECIMAL(7, 2)
	SET @num_conta = (SELECT num_conta FROM INSERTED)
	SET @saldo = (SELECT saldo FROM INSERTED)
	SET @limite = (SELECT limite FROM contaEspecial WHERE num_conta = @num_conta)

	IF (@limite IS NOT NULL)
	BEGIN
		-- Se for conta especial
		IF (@saldo < 0 AND @saldo < (@limite * (-1)))
		BEGIN
			ROLLBACK TRANSACTION
			RAISERROR ('Saldo abaixo do limite permitido', 16, 1)
		END
	END
	ELSE
	BEGIN
		-- Se for conta normal
		IF (@saldo < 0)
		BEGIN
			ROLLBACK TRANSACTION
			RAISERROR ('Saldo não pode ser negativo para este tipo de conta', 16, 1)
		END
	END
END
GO
CREATE FUNCTION fn_dados_da_conta(@num_conta INT)
RETURNS @tabela TABLE(
num_conta			INT,
nome				VARCHAR(255),
saldo				DECIMAL(7, 2),
saldo_com_limite	DECIMAL(7, 2)
)
BEGIN
	INSERT INTO @tabela (num_conta, nome, saldo) 
		SELECT num_conta, nome_cliente, saldo FROM contaBancaria WHERE num_conta = @num_conta

	DECLARE @limite DECIMAL(7, 2)
	SET @limite = (SELECT limite FROM conta_especial WHERE num_conta = @num_conta)
	IF (@limite IS NOT NULL)
	BEGIN
		DECLARE @saldo DECIMAL(7, 2)
		SET @saldo = (SELECT saldo FROM contaBancaria WHERE num_conta = @num_conta)
		UPDATE @tabela SET saldo_com_limite = (@saldo + @limite) WHERE num_conta = @num_conta
	END
	RETURN
END
GO
INSERT INTO contaBancaria (nome_cliente, num_conta, saldo)
VALUES 
('Cliente 1', 1001, 1500.00),
('Cliente 2', 1002, 2000.00),
('Cliente 3', 1003, 1800.00),
('Cliente 4', 1004, 2200.00),
('Cliente 5', 1005, 2500.00),
('Cliente 6', 1006, 1700.00),
('Cliente 7', 1007, 1900.00),
('Cliente 8', 1008, 2100.00),
('Cliente 9', 1009, 2300.00),
('Cliente 10', 1010, 2400.00);
GO
INSERT INTO contaPoupanca (num_conta, dia_rendimento)
VALUES 
(1001, 5),
(1002, 10),
(1003, 15),
(1004, 20),
(1005, 25);
GO
INSERT INTO contaEspecial(num_conta, limite)
VALUES 
(1006, 500.00),
(1007, 600.00),
(1008, 550.00),
(1009, 700.00),
(1010, 800.00);
GO
CREATE FUNCTION listarContasPoupanca()
RETURNS TABLE
AS
RETURN
(
    SELECT cp.num_conta, 
           cp.dia_rendimento, 
           cb.nome_cliente, 
           cb.saldo
    FROM contaPoupanca cp
    JOIN contaBancaria cb ON cp.num_conta = cb.num_conta
);
GO
CREATE FUNCTION listarContasEspeciais()
RETURNS TABLE
AS
RETURN
(
    SELECT ce.num_conta, 
           ce.limite, 
           cb.nome_cliente, 
           cb.saldo
    FROM contaEspecial ce
    JOIN contaBancaria cb ON ce.num_conta = cb.num_conta
);
GO
CREATE FUNCTION fn_consultar_conta_Poupanca(@num_conta INT)
RETURNS TABLE
AS
RETURN
(
    SELECT cp.num_conta, 
           cp.dia_rendimento, 
           cb.nome_cliente, 
           cb.saldo
    FROM contaPoupanca cp
    JOIN contaBancaria cb ON cp.num_conta = cb.num_conta
    WHERE cp.num_conta = @num_conta
);
GO
CREATE FUNCTION fn_consultar_conta_Especial(@num_conta INT)
RETURNS TABLE
AS
RETURN
(
    SELECT ce.num_conta, 
           ce.limite, 
           cb.nome_cliente, 
           cb.saldo
    FROM contaEspecial ce
    JOIN contaBancaria cb ON ce.num_conta = cb.num_conta
    WHERE ce.num_conta = @num_conta
);
GO
CREATE FUNCTION fn_consultar_conta_Bancaria(@num_conta INT)
RETURNS TABLE
AS
RETURN
(
    SELECT num_conta, 
           nome_cliente, 
           saldo
    FROM contaBancaria

    WHERE num_conta = @num_conta
);
GO
CREATE PROCEDURE sp_iud_contaPoupanca
    @acao CHAR(1),
    @num_conta INT,
    @nome_cliente VARCHAR(250) = NULL,
    @saldo DECIMAL(7, 2) = NULL,
    @dia_rendimento INT,
    @saida VARCHAR(100) OUTPUT
AS
BEGIN
    IF (@acao = 'I')
    BEGIN
        INSERT INTO contaPoupanca (num_conta, dia_rendimento)
        VALUES (@num_conta, @dia_rendimento)
        SET @saida = 'Conta Poupança inserida com sucesso'
    END
    ELSE IF (@acao = 'U')
    BEGIN
        UPDATE contaPoupanca
        SET dia_rendimento = @dia_rendimento
        WHERE num_conta = @num_conta
        SET @saida = 'Conta Poupança alterada com sucesso'
    END
    ELSE IF (@acao = 'D')
    BEGIN
        DELETE FROM contaPoupanca WHERE num_conta = @num_conta
        SET @saida = 'Conta Poupança excluída com sucesso'
    END
    ELSE
    BEGIN
        RAISERROR('Operação inválida', 16, 1)
        RETURN
    END
    -- Atualiza o saldo se fornecido
    IF (@saldo IS NOT NULL)
    BEGIN
        UPDATE contaBancaria
        SET saldo = @saldo
        WHERE num_conta = @num_conta
    END

    -- Atualiza o nome do cliente se fornecido
    IF (@nome_cliente IS NOT NULL)
    BEGIN
        UPDATE contaBancaria
        SET nome_cliente = @nome_cliente
        WHERE num_conta = @num_conta
    END
END
GO
CREATE PROCEDURE sp_iud_conta_especial
    @acao CHAR(1),
    @num_conta INT,
    @nome_cliente VARCHAR(250) = NULL,
    @saldo DECIMAL(7, 2) = NULL,
    @limite DECIMAL(7, 2),
    @saida VARCHAR(100) OUTPUT
AS
BEGIN
    IF (@acao = 'I')
    BEGIN
        INSERT INTO contaEspecial (num_conta, limite)
        VALUES (@num_conta, @limite)
        SET @saida = 'Conta Especial inserida com sucesso'
    END
    ELSE IF (@acao = 'U')
    BEGIN
        UPDATE contaEspecial
        SET limite = @limite
        WHERE num_conta = @num_conta
        SET @saida = 'Conta Especial alterada com sucesso'
    END
    ELSE IF (@acao = 'D')
    BEGIN
        DELETE FROM contaEspecial WHERE num_conta = @num_conta
        SET @saida = 'Conta Especial excluída com sucesso'
    END
    ELSE
    BEGIN
        RAISERROR('Operação inválida', 16, 1)
        RETURN
    END
    -- Atualiza o saldo se fornecido
    IF (@saldo IS NOT NULL)
    BEGIN
        UPDATE contaBancaria
        SET saldo = @saldo
        WHERE num_conta = @num_conta
    END

    -- Atualiza o nome do cliente se fornecido
    IF (@nome_cliente IS NOT NULL)
    BEGIN
        UPDATE contaBancaria
        SET nome_cliente = @nome_cliente
        WHERE num_conta = @num_conta
    END
END
GO
CREATE PROCEDURE sp_iud_contaBancaria
    @acao CHAR(1),
    @num_conta INT,
    @saldo DECIMAL(7, 2)
AS
BEGIN
    IF (@acao = 'I')
    BEGIN
        INSERT INTO contaBancaria (num_conta, saldo)
        VALUES (@num_conta, @saldo);
    END
    ELSE IF (@acao = 'U')
    BEGIN
        UPDATE contaBancaria
        SET saldo = @saldo
        WHERE num_conta = @num_conta;
    END
    ELSE IF (@acao = 'D')
    BEGIN
        DELETE FROM contaBancaria
        WHERE num_conta = @num_conta;
    END
END;
GO


CREATE PROCEDURE sp_sacar
    @num_conta INT,
    @valor_saque DECIMAL(7, 2),
    @saida VARCHAR(100) OUTPUT
AS
BEGIN
    UPDATE contaBancaria
    SET saldo = saldo - @valor_saque
    WHERE num_conta = @num_conta;

    SET @saida = 'Saque Realizado com sucesso';
END








