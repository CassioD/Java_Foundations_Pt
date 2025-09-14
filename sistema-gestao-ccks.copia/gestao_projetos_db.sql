-- Define o banco de dados a ser utilizado. Se não existir, ele será criado.
--CREATE DATABASE IF NOT EXISTS gestao_projetos_db;
-- Seleciona o banco de dados para as operações seguintes.
--USE gestao_projetos_db;

-- Tabela para armazenar os dados dos usuários do sistema.
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY, -- Identificador único para cada usuário.
    full_name VARCHAR(255) NOT NULL, -- Nome completo do usuário.
    cpf VARCHAR(14) NOT NULL UNIQUE, -- CPF do usuário, deve ser único.
    email VARCHAR(255) NOT NULL UNIQUE, -- E-mail do usuário, deve ser único.
    job_title VARCHAR(100), -- Cargo ou função do usuário na empresa.
    login VARCHAR(50) NOT NULL UNIQUE, -- Nome de usuário para login, deve ser único.
    password VARCHAR(255) NOT NULL, -- Senha do usuário, armazenada como um hash seguro (BCrypt).
    --profile ENUM('ADMINISTRADOR', 'GERENTE', 'COLABORADOR') NOT NULL, -- Perfil de permissões do usuário.
    profile TEXT CHECK(profile IN('ADMINISTRADOR', 'GERENTE', 'COLABORADOR')) NOT NULL, -- Perfil de permissões do usuário.
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP -- Data e hora de criação do registro.
);

-- Tabela para armazenar os projetos.
CREATE TABLE projects (
    id INT AUTO_INCREMENT PRIMARY KEY, -- Identificador único para cada projeto.
    name VARCHAR(255) NOT NULL, -- Nome do projeto.
    description TEXT, -- Descrição detalhada do projeto.
    start_date DATE NOT NULL, -- Data de início do projeto.
    planned_end_date DATE NOT NULL, -- Data de término planejada.
    --status ENUM('PLANEJADO', 'EM_ANDAMENTO', 'CONCLUIDO', 'CANCELADO') NOT NULL, -- Status atual do projeto.
    status TEXT CHECK (status IN('PLANEJADO', 'EM_ANDAMENTO', 'CONCLUIDO', 'CANCELADO')) NOT NULL, -- Status atual do projeto.
    manager_id INT, -- ID do usuário que é o gerente do projeto.
    -- Chave estrangeira que liga o gerente do projeto (manager_id) ao ID do usuário na tabela 'users'.
    FOREIGN KEY (manager_id) REFERENCES users(id)
);

-- Tabela para armazenar as equipes de trabalho.
CREATE TABLE teams (
    id INT AUTO_INCREMENT PRIMARY KEY, -- Identificador único para cada equipe.
    name VARCHAR(255) NOT NULL, -- Nome da equipe.
    description TEXT -- Descrição da equipe.
);

-- Tabela de associação para relacionar usuários a equipes (relação Muitos-para-Muitos).
CREATE TABLE team_members (
    team_id INT, -- ID da equipe.
    user_id INT, -- ID do usuário.
    PRIMARY KEY (team_id, user_id), -- A chave primária composta garante que um usuário só pode estar uma vez em cada equipe.
    -- Chaves estrangeiras que ligam à tabela 'teams' e 'users'.
    -- ON DELETE CASCADE: Se uma equipe ou um usuário for deletado, essa associação também será.
    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Tabela de associação para relacionar projetos a equipes (relação Muitos-para-Muitos).
CREATE TABLE project_teams (
    project_id INT, -- ID do projeto.
    team_id INT, -- ID da equipe.
    PRIMARY KEY (project_id, team_id), -- A chave primária composta garante que uma equipe só pode estar uma vez em cada projeto.
    -- Chaves estrangeiras que ligam à tabela 'projects' e 'teams'.
    -- ON DELETE CASCADE: Se um projeto ou uma equipe for deletada, essa associação também será.
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES teams(id) ON DELETE CASCADE
);

-- Tabela para armazenar as tarefas de cada projeto.
CREATE TABLE tasks (
    id INT AUTO_INCREMENT PRIMARY KEY, -- Identificador único para cada tarefa.
    title VARCHAR(255) NOT NULL, -- Título da tarefa.
    description TEXT, -- Descrição detalhada da tarefa.
    project_id INT NOT NULL, -- ID do projeto ao qual a tarefa pertence.
    responsible_id INT, -- ID do usuário responsável pela tarefa.
    --status ENUM('PENDENTE', 'EM_EXECUCAO', 'CONCLUIDA') NOT NULL, -- Status atual da tarefa.
    status TEXT CHECK (status IN('PENDENTE', 'EM_EXECUCAO', 'CONCLUIDA')) NOT NULL, -- Status atual da tarefa.
    planned_start_date DATE, -- Data de início planejada para a tarefa.
    planned_end_date DATE, -- Data de término planejada para a tarefa.
    actual_start_date DATE, -- Data em que a tarefa foi realmente iniciada.
    actual_end_date DATE, -- Data em que a tarefa foi realmente concluída.
    -- Chave estrangeira que liga a tarefa ao seu projeto.
    FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
    -- Chave estrangeira que liga a tarefa ao seu responsável.
    FOREIGN KEY (responsible_id) REFERENCES users(id)
);

-- =================================================================
-- DADOS DE EXEMPLO (POPULAÇÃO INICIAL)
-- =================================================================

-- A senha para o usuário 'admin' é 'admin123'.
-- O hash abaixo foi gerado com a biblioteca BCrypt, que é o mesmo método
-- usado pela aplicação Java para verificar e criar senhas.
-- É crucial usar o mesmo algoritmo para que a verificação funcione.

-- Insere um usuário 'Administrador' inicial.
INSERT INTO users (full_name, cpf, email, job_title, login, password, profile)
VALUES (
    'Administrador do Sistema',
    '000.000.000-00',
    'admin@ccks.com',
    'Admin',
    'admin',
    '$2a$10$/G3Jp3SYW2QhHvhIsHBov.1pkhktYzsjbiV76ukb0vfqyEeGwutGm', -- Senha 'admin123' criptografada com BCrypt.
    'ADMINISTRADOR'
);

-- Insere um projeto de teste inicial, associado ao usuário 'admin' (cujo id é 1).
INSERT INTO projects (name, description, start_date, planned_end_date, status, manager_id)
VALUES ('Implantação do Sistema', 'Projeto inicial para configuração e implantação do sistema de gestão.', '2024-05-01', '2024-08-31', 'PLANEJADO', 1);
