CREATE TABLE usuarios(
	
	id BIGINT NOT NULL UNIQUE AUTO_INCREMENT,
	usuario VARCHAR(255) NOT NULL,
	senha VARCHAR(255) NOT NULL,
	
	PRIMARY KEY(id)
);