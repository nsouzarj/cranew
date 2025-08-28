# CRA Backend - Spring Boot API

Este projeto é um backend desenvolvido em Spring Boot para o sistema CRA (Correspondente Responsável por Atos), baseado nas entidades do projeto original localizado em `D:\Projetos\cra\src\br\adv\cra\entity`.

## 🚀 Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **Spring Web**
- **Spring Security** (JWT Authentication)
- **Spring Validation**
- **JWT (JSON Web Tokens)**
- **PostgreSQL** (produção)
- **MySQL** (alternativo)
- **H2 Database** (desenvolvimento/testes)
- **Lombok**
- **Maven**

## 📋 Pré-requisitos

- Java 17 ou superior
- Maven 3.6 ou superior
- **PostgreSQL 12+** (produção) - Configurado em 192.168.1.105:5432
- MySQL 8.0 (alternativo)

## 🛠️ Configuração do Projeto

### 1. Clone e Configuração Inicial

O projeto já está configurado na pasta atual. Para executar:

### 2. Configuração do Banco de Dados

#### Para Desenvolvimento (H2):
```bash
# Usar o perfil de desenvolvimento (padrão)
# O banco H2 será criado automaticamente em memória
```

#### Para Produção (PostgreSQL):
```sql
-- Conectar ao PostgreSQL como superusuário
psql -h 192.168.1.105 -U postgres

-- Criar banco de dados (se não existir)
CREATE DATABASE dbcra WITH ENCODING 'UTF8';

-- Verificar conexão
\c dbcra
\dt
```

#### Para MySQL (Alternativo):
```sql
-- Criar banco de dados
CREATE DATABASE cra_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Criar usuário
CREATE USER 'cra_user'@'localhost' IDENTIFIED BY 'cra_password';
GRANT ALL PRIVILEGES ON cra_db.* TO 'cra_user'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Execução do Projeto

#### Modo Produção (PostgreSQL):
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

#### Modo Desenvolvimento (H2):
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

#### Modo Padrão (PostgreSQL):
```bash
mvn spring-boot:run
```

### 4. Acesso às Aplicações

- **API REST**: http://localhost:8080/cra-api
- **H2 Console** (dev): http://localhost:8080/cra-api/h2-console
- **Actuator Health**: http://localhost:8080/cra-api/actuator/health

## 📚 Estrutura do Projeto

```
src/
├── main/
│   ├── java/br/adv/cra/
│   │   ├── CraBackendApplication.java
│   │   ├── config/
│   │   │   ├── WebConfig.java
│   │   │   └── GlobalExceptionHandler.java
│   │   ├── entity/
│   │   │   ├── Usuario.java
│   │   │   ├── Correspondente.java
│   │   │   ├── Endereco.java
│   │   │   ├── Uf.java
│   │   │   ├── Comarca.java
│   │   │   ├── Orgao.java
│   │   │   ├── Processo.java
│   │   │   └── Solicitacao.java
│   │   ├── repository/
│   │   │   ├── UsuarioRepository.java
│   │   │   ├── CorrespondenteRepository.java
│   │   │   ├── EnderecoRepository.java
│   │   │   ├── UfRepository.java
│   │   │   ├── ComarcaRepository.java
│   │   │   ├── OrgaoRepository.java
│   │   │   ├── ProcessoRepository.java
│   │   │   └── SolicitacaoRepository.java
│   │   ├── service/
│   │   │   ├── UsuarioService.java
│   │   │   ├── CorrespondenteService.java
│   │   │   ├── ProcessoService.java
│   │   │   ├── SolicitacaoService.java
│   │   │   └── UfService.java
│   │   └── controller/
│   │       ├── UsuarioController.java
│   │       ├── CorrespondenteController.java
│   │       ├── ProcessoController.java
│   │       └── SolicitacaoController.java
│   └── resources/
│       ├── application.properties
│       ├── application-dev.properties
│       └── data.sql
```

## 🔗 Endpoints da API

### Usuários
- `GET /api/usuarios` - Listar todos os usuários
- `GET /api/usuarios/{id}` - Buscar usuário por ID
- `GET /api/usuarios/ativos` - Listar usuários ativos
- `GET /api/usuarios/buscar/login/{login}` - Buscar por login
- `GET /api/usuarios/buscar/nome?nome={nome}` - Buscar por nome
- `GET /api/usuarios/buscar/tipo/{tipo}` - Buscar por tipo
- `POST /api/usuarios` - Criar novo usuário
- `PUT /api/usuarios/{id}` - Atualizar usuário
- `PUT /api/usuarios/{id}/ativar` - Ativar usuário
- `PUT /api/usuarios/{id}/inativar` - Inativar usuário
- `DELETE /api/usuarios/{id}` - Deletar usuário
- `POST /api/usuarios/autenticar` - Autenticar usuário

### Correspondentes
- `GET /api/correspondentes` - Listar todos os correspondentes
- `GET /api/correspondentes/{id}` - Buscar correspondente por ID
- `GET /api/correspondentes/ativos` - Listar correspondentes ativos
- `GET /api/correspondentes/buscar/nome?nome={nome}` - Buscar por nome
- `GET /api/correspondentes/buscar/cpfcnpj/{cpfCnpj}` - Buscar por CPF/CNPJ
- `GET /api/correspondentes/buscar/oab/{oab}` - Buscar por OAB
- `GET /api/correspondentes/buscar/tipo/{tipo}` - Buscar por tipo
- `GET /api/correspondentes/regra1` - Listar com regra 1
- `GET /api/correspondentes/regra2` - Listar com regra 2
- `POST /api/correspondentes` - Criar novo correspondente
- `PUT /api/correspondentes/{id}` - Atualizar correspondente
- `PUT /api/correspondentes/{id}/ativar` - Ativar correspondente
- `PUT /api/correspondentes/{id}/inativar` - Inativar correspondente
- `DELETE /api/correspondentes/{id}` - Deletar correspondente

### Processos
- `GET /api/processos` - Listar todos os processos
- `GET /api/processos/{id}` - Buscar processo por ID
- `GET /api/processos/buscar/numero/{numeroProcesso}` - Buscar por número
- `GET /api/processos/buscar/numero-pesquisa?numero={numero}` - Buscar por número de pesquisa
- `GET /api/processos/buscar/parte?parte={parte}` - Buscar por parte
- `GET /api/processos/buscar/adverso?adverso={adverso}` - Buscar por adverso
- `GET /api/processos/buscar/status/{status}` - Buscar por status
- `GET /api/processos/buscar/assunto?assunto={assunto}` - Buscar por assunto
- `GET /api/processos/buscar/comarca/{comarcaId}` - Buscar por comarca
- `GET /api/processos/buscar/orgao/{orgaoId}` - Buscar por órgão
- `GET /api/processos/estatisticas/status/{status}` - Contar por status
- `POST /api/processos` - Criar novo processo
- `PUT /api/processos/{id}` - Atualizar processo
- `DELETE /api/processos/{id}` - Deletar processo

### Solicitações
- `GET /api/solicitacoes` - Listar todas as solicitações
- `GET /api/solicitacoes/{id}` - Buscar solicitação por ID
- `GET /api/solicitacoes/pendentes` - Listar solicitações pendentes
- `GET /api/solicitacoes/concluidas` - Listar solicitações concluídas
- `GET /api/solicitacoes/atrasadas` - Listar solicitações atrasadas
- `GET /api/solicitacoes/pagas` - Listar solicitações pagas
- `GET /api/solicitacoes/nao-pagas` - Listar solicitações não pagas
- `GET /api/solicitacoes/usuario/{usuarioId}` - Buscar por usuário
- `GET /api/solicitacoes/buscar/periodo?inicio={inicio}&fim={fim}` - Buscar por período
- `GET /api/solicitacoes/buscar/texto?texto={texto}` - Buscar por texto
- `GET /api/solicitacoes/buscar/grupo/{grupo}` - Buscar por grupo
- `GET /api/solicitacoes/estatisticas/pendentes` - Contar pendentes
- `POST /api/solicitacoes` - Criar nova solicitação
- `PUT /api/solicitacoes/{id}` - Atualizar solicitação
- `PUT /api/solicitacoes/{id}/concluir` - Concluir solicitação
- `PUT /api/solicitacoes/{id}/marcar-pago` - Marcar como pago
- `PUT /api/solicitacoes/{id}/marcar-nao-pago` - Marcar como não pago
- `DELETE /api/solicitacoes/{id}` - Deletar solicitação

## 🔐 Segurança JWT

O projeto utiliza **JWT (JSON Web Tokens)** para autenticação e autorização.

### Tipos de Usuário e Permissões:
- **Tipo 1 - ADMIN**: Acesso completo ao sistema
- **Tipo 2 - ADVOGADO**: Acesso a funcionalidades de advogados
- **Tipo 3 - CORRESPONDENTE**: Acesso limitado para correspondentes

### Endpoints de Autenticação:

#### `POST /api/auth/login`
Autentica um usuário e retorna JWT token.

**Request:**
```json
{
  "login": "admin",
  "senha": "admin123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "login": "admin",
  "nomeCompleto": "Administrador do Sistema",
  "emailPrincipal": "admin@cra.com.br",
  "tipo": 1,
  "roles": ["ROLE_ADMIN"],
  "expiresAt": "2024-08-29T14:30:00"
}
```

#### `POST /api/auth/refresh`
Renova o JWT token usando refresh token.

**Request:**
```json
{
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

#### `POST /api/auth/register` 🔒 **ADMIN only**
Registra um novo usuário (apenas administradores).

**Request:**
```json
{
  "login": "novousuario",
  "senha": "senha123",
  "nomeCompleto": "Nome Completo",
  "emailPrincipal": "email@exemplo.com",
  "tipo": 2
}
```

#### `GET /api/auth/me`
Retorna informações do usuário autenticado.

#### `POST /api/auth/logout`
Realiza logout (remove token no cliente).

#### `GET /api/auth/validate`
Valida se o token JWT atual é válido.

#### `GET /api/auth/database-info`
Retorna informações sobre a conexão com o banco de dados PostgreSQL (endpoint público para testes).

### Como Usar a Autenticação:

1. **Login**: Faça POST para `/api/auth/login` com credenciais
2. **Use o Token**: Inclua o token no header `Authorization: Bearer <token>`
3. **Renovação**: Use `/api/auth/refresh` quando o token expirar

### Exemplo de Requisição Autenticada:

```bash
curl -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
     http://localhost:8080/cra-api/api/usuarios
```

### Configuração JWT:

```properties
# application.properties
app.jwt.secret=<secret-key-base64>
app.jwt.expiration=86400000          # 24 horas
app.jwt.refresh-expiration=604800000 # 7 dias
```

## 🔧 Solução de Problemas

### Problemas de Conexão PostgreSQL:

1. **Verificar se o PostgreSQL está rodando:**
```bash
# No servidor 192.168.1.105
sudo systemctl status postgresql
sudo systemctl start postgresql
```

2. **Testar conexão manual:**
```bash
psql -h 192.168.1.105 -U postgres -d dbcra
```

3. **Verificar firewall:**
```bash
# Permitir porta 5432
sudo ufw allow 5432
telnet 192.168.1.105 5432
```

4. **Verificar configuração PostgreSQL:**
```bash
# Editar postgresql.conf
listen_addresses = '*'
port = 5432

# Editar pg_hba.conf
host    all             all             0.0.0.0/0               md5
```

5. **Endpoint de teste de conexão:**
```bash
curl http://localhost:8080/cra-api/api/auth/database-info
```

### Problemas de Autenticação:

1. **Erro "Credenciais inválidas":**
```bash
# Execute o script de correção de senhas no PostgreSQL
psql -h 192.168.1.105 -U postgres -d dbcra -f database/fix-passwords.sql
```

2. **Usuários padrão após correção:**
```
Login: admin, Senha: admin123 (ROLE_ADMIN)
Login: advogado1, Senha: adv123 (ROLE_ADVOGADO)  
Login: corresp1, Senha: corresp123 (ROLE_CORRESPONDENTE)
Login: isomina, Senha: isomina123 (ROLE_ADVOGADO)
```

3. **Testar login após correção:**
```bash
curl -X POST http://localhost:8080/cra-api/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"login":"admin","senha":"admin123"}'
```

### Logs do Sistema:
```bash
# Verificar logs da aplicação
tail -f logs/cra-backend.log

# Verificar logs do PostgreSQL
sudo tail -f /var/log/postgresql/postgresql-*.log
```

## 🧪 Dados de Teste

O projeto inclui dados iniciais para desenvolvimento:

### Usuários Padrão:
- **Admin**: login=`admin`, senha=`admin123`, tipo=1
- **Advogado**: login=`advogado1`, senha=`adv123`, tipo=2
- **Correspondente**: login=`corresp1`, senha=`corresp123`, tipo=3

### Tipos de Usuário:
- `1` - Administrador
- `2` - Advogado
- `3` - Correspondente

## 🔧 Personalização

### Modificar Configurações de Banco:
Edite o arquivo `application.properties` para produção ou `application-dev.properties` para desenvolvimento.

### Adicionar Novos Endpoints:
1. Crie o método no Service correspondente
2. Adicione o endpoint no Controller
3. Teste via Postman ou curl

### Adicionar Validações:
Use as anotações do Bean Validation nas entidades ou crie validadores customizados.

## 📞 Suporte

Para dúvidas ou problemas, verifique:

1. Logs da aplicação em `logs/cra-backend.log`
2. Console do H2 (modo dev): http://localhost:8080/cra-api/h2-console
3. Endpoint de health: http://localhost:8080/cra-api/actuator/health

## 🏗️ Modernizações Implementadas

- **Migração para Jakarta EE** (Spring Boot 3.x)
- **Uso de Lombok** para reduzir boilerplate
- **Validações com Bean Validation**
- **Tratamento global de exceções**
- **Configuração CORS**
- **Profiles de ambiente**
- **Logs estruturados**
- **Endpoints RESTful padronizados**
- **Uso de LocalDateTime** ao invés de Date
- **IDs Long** ao invés de Integer