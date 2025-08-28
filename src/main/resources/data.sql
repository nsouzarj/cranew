-- ===================================================================
-- BASIC DATA INITIALIZATION SCRIPT FOR POSTGRESQL
-- ===================================================================

-- UF data
INSERT INTO uf (sigla, nome) VALUES 
('SP', 'São Paulo'),
('RJ', 'Rio de Janeiro'),
('MG', 'Minas Gerais'),
('RS', 'Rio Grande do Sul'),
('PR', 'Paraná'),
('SC', 'Santa Catarina'),
('GO', 'Goiás'),
('PE', 'Pernambuco'),
('CE', 'Ceará'),
('BA', 'Bahia')
ON CONFLICT (sigla) DO NOTHING;

-- Órgãos data
INSERT INTO orgao (descricao) VALUES 
('Tribunal de Justiça'),
('Tribunal Regional Federal'),
('Superior Tribunal de Justiça'),
('Tribunal Superior do Trabalho'),
('Tribunal Regional do Trabalho')
ON CONFLICT (descricao) DO NOTHING;

-- Some basic comarca data for SP (assuming UF with id 1 is SP)
INSERT INTO comarca (nome, uf_id) 
SELECT 'São Paulo', u.id FROM uf u WHERE u.sigla = 'SP'
UNION ALL
SELECT 'Campinas', u.id FROM uf u WHERE u.sigla = 'SP'
UNION ALL
SELECT 'Santos', u.id FROM uf u WHERE u.sigla = 'SP'
UNION ALL
SELECT 'Ribeirão Preto', u.id FROM uf u WHERE u.sigla = 'SP'
UNION ALL
SELECT 'Sorocaba', u.id FROM uf u WHERE u.sigla = 'SP'
ON CONFLICT (nome, uf_id) DO NOTHING;

-- Some basic users (passwords are encrypted with BCrypt)
-- admin/admin123 -> $2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.
-- advogado1/adv123 -> $2a$10$eImiTXuWVxfM37uY4JANjOHyqQxFPHSRwdd/Q7r8STgdmgMZnFNzm
-- corresp1/corresp123 -> $2a$10$X5wFBtLrL/.p03LkBOJfsuO1DsiK.mq9QhTf5SNEFm2ReDJSTQFpu
INSERT INTO usuario (login, senha, nomecompleto, emailprincipal, tipo, dataentrada, ativo) VALUES 
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Administrador do Sistema', 'admin@cra.com.br', 1, CURRENT_TIMESTAMP, true),
('advogado1', '$2a$10$eImiTXuWVxfM37uY4JANjOHyqQxFPHSRwdd/Q7r8STgdmgMZnFNzm', 'Dr. João Silva', 'joao.silva@cra.com.br', 2, CURRENT_TIMESTAMP, true),
('corresp1', '$2a$10$X5wFBtLrL/.p03LkBOJfsuO1DsiK.mq9QhTf5SNEFm2ReDJSTQFpu', 'Maria Santos', 'maria.santos@correspondente.com.br', 3, CURRENT_TIMESTAMP, true)
ON CONFLICT (login) DO NOTHING;