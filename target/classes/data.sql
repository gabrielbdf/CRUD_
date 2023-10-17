
CREATE TABLE pessoas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    idade INT,
    cpf VARCHAR(11) UNIQUE,
    nacionalidade VARCHAR(255)
);

INSERT INTO pessoas (nome, idade, cpf, nacionalidade) VALUES
    ('John Doe', 30, '12345678901', 'Brazil'),
    ('Alice Smith', 25, '98765432109', 'United States');