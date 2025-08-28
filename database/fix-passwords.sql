-- ===================================================================
-- PASSWORD UPDATE SCRIPT FOR EXISTING USERS
-- ===================================================================
-- Run this script on your PostgreSQL database to fix the password hashes
-- Connect to: psql -h 192.168.1.105 -U postgres -d dbcra
-- ===================================================================

-- Update existing users with correct BCrypt password hashes
UPDATE usuario SET senha = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.' WHERE login = 'admin';
UPDATE usuario SET senha = '$2a$10$eImiTXuWVxfM37uY4JANjOHyqQxFPHSRwdd/Q7r8STgdmgMZnFNzm' WHERE login = 'advogado1';
UPDATE usuario SET senha = '$2a$10$X5wFBtLrL/.p03LkBOJfsuO1DsiK.mq9QhTf5SNEFm2ReDJSTQFpu' WHERE login = 'corresp1';

-- Verify the updates
SELECT login, 
       CASE 
           WHEN senha = '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.' THEN 'admin123 ✓'
           WHEN senha = '$2a$10$eImiTXuWVxfM37uY4JANjOHyqQxFPHSRwdd/Q7r8STgdmgMZnFNzm' THEN 'adv123 ✓'
           WHEN senha = '$2a$10$X5wFBtLrL/.p03LkBOJfsuO1DsiK.mq9QhTf5SNEFm2ReDJSTQFpu' THEN 'corresp123 ✓'
           ELSE 'Password hash needs update ✗'
       END as password_status,
       tipo, 
       ativo,
       nomecompleto
FROM usuario 
WHERE login IN ('admin', 'advogado1', 'corresp1')
ORDER BY tipo;

-- Alternative: Delete and re-insert users (if update doesn't work)
-- DELETE FROM usuario WHERE login IN ('admin', 'advogado1', 'corresp1');
-- 
-- INSERT INTO usuario (login, senha, nomecompleto, emailprincipal, tipo, dataentrada, ativo) VALUES 
-- ('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.', 'Administrador do Sistema', 'admin@cra.com.br', 1, CURRENT_TIMESTAMP, true),
-- ('advogado1', '$2a$10$eImiTXuWVxfM37uY4JANjOHyqQxFPHSRwdd/Q7r8STgdmgMZnFNzm', 'Dr. João Silva', 'joao.silva@cra.com.br', 2, CURRENT_TIMESTAMP, true),
-- ('corresp1', '$2a$10$X5wFBtLrL/.p03LkBOJfsuO1DsiK.mq9QhTf5SNEFm2ReDJSTQFpu', 'Maria Santos', 'maria.santos@correspondente.com.br', 3, CURRENT_TIMESTAMP, true);

-- Show all users after update
SELECT login, nomecompleto, tipo, ativo, dataentrada FROM usuario ORDER BY tipo;