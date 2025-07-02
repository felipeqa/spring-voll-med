CREATE TABLE medicos (
    id bigint not null auto_increment,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    crm VARCHAR(20) NOT NULL UNIQUE,
    especialidade VARCHAR(50) NOT NULL,

    -- Dados do endereço
    logradouro VARCHAR(100) NOT NULL,
    bairro VARCHAR(60) NOT NULL,
    cep CHAR(9) NOT NULL,
    complemento VARCHAR(100),
    numero VARCHAR(20),
    cidade VARCHAR(60) NOT NULL,
    uf CHAR(2) NOT NULL,

    primary key(id)
);