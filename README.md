# Portal Cliente - CoderBank

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Status](https://img.shields.io/badge/Status-Em_Desenvolvimento-yellow?style=for-the-badge)
![Maintenance](https://img.shields.io/badge/Maintained-Yes-green?style=for-the-badge)

## 📋 Descrição

Portal Cliente CoderBank é uma API REST completa para gerenciamento de contas bancárias digitais. O sistema permite realizar operações bancárias essenciais como criação de contas, depósitos, saques, transferências e consulta de movimentações, proporcionando uma experiência bancária digital moderna e segura.

### Motivação

Este projeto foi desenvolvido para demonstrar:
- Arquitetura de APIs RESTful robustas e escaláveis
- Implementação de operações bancárias com segurança e validação
- Padrões de projeto em Java com Spring Boot
- Uso de DTOs (Data Transfer Objects) para separação de responsabilidades
- Validação de dados com Bean Validation
- Gerenciamento de transações financeiras

### Tecnologias Utilizadas

- **Java 17+** - Linguagem de programação principal
- **Spring Boot** - Framework para desenvolvimento de aplicações Java
- **Spring Data JPA** - Persistência de dados e mapeamento objeto-relacional
- **Jakarta Validation** - Validação de dados de entrada
- **Gradle** - Ferramenta de automação de build
- **PostgreSQL** - Banco de dados relacional (configurável)
- **H2 Database** - Banco de dados em memória para testes

### Funcionalidades Principais

✅ Criação de contas bancárias  
✅ Consulta de saldo  
✅ Realização de depósitos  
✅ Realização de saques  
✅ Transferências entre contas  
✅ Consulta de movimentações (extrato)  
✅ Validação de dados com mensagens personalizadas  
✅ Tratamento de erros centralizado

---

## 🚀 Instruções de Instalação

### Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:

- **Java JDK 17** ou superior
  - Download: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) ou [OpenJDK](https://adoptium.net/)
  - Verificar instalação: `java -version`

- **Git 2.4** ou superior
  - Download: [Git](https://git-scm.com/downloads)
  - Verificar instalação: `git --version`

- **PostgreSQL 12+** (opcional - pode usar H2)
  - Download: [PostgreSQL](https://www.postgresql.org/download/)

- **Gradle 7.x+** (opcional - projeto inclui Gradle Wrapper)

### Etapas de Instalação

1. **Clone o repositório**

```bash
git clone https://github.com/MarclHenrique/portalcliente.git
cd portalcliente
```

2. **Configure o banco de dados**

Edite o arquivo `src/main/resources/application.properties`:

**Para PostgreSQL:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/coderbank
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

**Para H2 (testes/desenvolvimento):**
```properties
spring.datasource.url=jdbc:h2:mem:coderbank
spring.datasource.driverClassName=org.h2.Driver
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=create-drop
```

3. **Execute a aplicação**

**No Linux/Mac:**
```bash
./gradlew bootRun
```

**No Windows:**
```bash
gradlew.bat bootRun
```

4. **Build do projeto**

Para gerar o arquivo JAR:

```bash
./gradlew build
```

O arquivo JAR será gerado em `build/libs/`

Para executar o JAR:
```bash
java -jar build/libs/portalCliente-0.0.1-SNAPSHOT.jar
```

---

## 💻 Instruções de Uso

### Executando a Aplicação

Após seguir as etapas de instalação, a API estará disponível em:

```
http://localhost:8080
```

---

## 📚 Documentação da API

### Base URL
```
http://localhost:8080/v1
```

---

## 🏦 Endpoints - Contas

### **1. Criar Conta**

Cria uma nova conta bancária para um cliente.

```http
POST /v1/contas
Content-Type: application/json
```

**Request Body:**
```json
{
  "idCliente": "123e4567-e89b-12d3-a456-426614174000"
}
```

**Response:** `201 Created`
```json
{
  "id": "789e4567-e89b-12d3-a456-426614174001",
  "agencia": "0001",
  "numero": "123456-7",
  "saldo": 0.00,
  "idCliente": "123e4567-e89b-12d3-a456-426614174000",
  "criadoEm": "2026-01-25T10:30:00",
  "atualizadoEm": "2026-01-25T10:30:00"
}
```

**Validações:**
- `idCliente` é obrigatório

---

### **2. Consultar Saldo**

Consulta o saldo de uma conta através do ID do cliente.

```http
GET /v1/contas/{idCliente}
```

**Response:** `200 OK`
```json
{
  "contaId": "789e4567-e89b-12d3-a456-426614174001",
  "agencia": "0001",
  "numero": "123456-7",
  "saldo": 1500.50,
  "idCliente": "123e4567-e89b-12d3-a456-426614174000"
}
```

---

### **3. Realizar Depósito**

Realiza um depósito em uma conta.

```http
PATCH /v1/contas/deposito
Content-Type: application/json
```

**Request Body:**
```json
{
  "idConta": "789e4567-e89b-12d3-a456-426614174001",
  "valor": 500.00
}
```

**Response:** `200 OK`
```json
{
  "contaId": "789e4567-e89b-12d3-a456-426614174001",
  "tipoOperacao": "DEPOSITO",
  "valor": 500.00,
  "saldoAnterior": 1000.00,
  "saldoAtual": 1500.00,
  "dataHora": "2026-01-25T14:30:00"
}
```

**Validações:**
- `idConta` é obrigatório
- `valor` é obrigatório
- `valor` deve ser maior que 0.01

---

### **4. Realizar Saque**

Realiza um saque de uma conta.

```http
PATCH /v1/contas/saque
Content-Type: application/json
```

**Request Body:**
```json
{
  "idConta": "789e4567-e89b-12d3-a456-426614174001",
  "valor": 200.00
}
```

**Response:** `200 OK`
```json
{
  "contaId": "789e4567-e89b-12d3-a456-426614174001",
  "tipoOperacao": "SAQUE",
  "valor": 200.00,
  "saldoAnterior": 1500.00,
  "saldoAtual": 1300.00,
  "dataHora": "2026-01-25T15:00:00"
}
```

**Validações:**
- `idConta` é obrigatório
- `valor` é obrigatório
- `valor` deve ser maior que 0.01
- Conta deve ter saldo suficiente

---

## 💸 Endpoints - Transferências

### **5. Realizar Transferência**

Transfere valor de uma conta para outra.

```http
POST /v1/transferencias
Content-Type: application/json
```

**Request Body:**
```json
{
  "idContaOrigem": "789e4567-e89b-12d3-a456-426614174001",
  "idContaDestino": "789e4567-e89b-12d3-a456-426614174002",
  "valor": 300.00
}
```

**Response:** `200 OK`
```json
{
  "contaOrigemId": "789e4567-e89b-12d3-a456-426614174001",
  "contaDestinoId": "789e4567-e89b-12d3-a456-426614174002",
  "clienteOrigemId": "123e4567-e89b-12d3-a456-426614174000",
  "clienteDestinoId": "123e4567-e89b-12d3-a456-426614174003",
  "tipoOperacao": "TRANSFERENCIA",
  "valor": 300.00,
  "saldoAnteriorOrigem": 1300.00,
  "saldoAtualOrigem": 1000.00,
  "dataHora": "2026-01-25T16:00:00"
}
```

**Validações:**
- `idContaOrigem` é obrigatório
- `idContaDestino` é obrigatório
- `valor` é obrigatório
- `valor` deve ser maior que 0.01
- Conta origem deve ter saldo suficiente

---

### **6. Consultar Movimentações por Cliente**

Consulta todas as movimentações de um cliente.

```http
GET /v1/accounts/cliente/{clienteId}
```

**Response:** `200 OK`
```json
[
  {
    "id": "abc12345-e89b-12d3-a456-426614174010",
    "conta": "789e4567-e89b-12d3-a456-426614174001",
    "tipo": "DEPOSITO",
    "valor": 500.00,
    "contaDestinoId": null,
    "dataHora": "2026-01-25T14:30:00"
  },
  {
    "id": "def67890-e89b-12d3-a456-426614174011",
    "conta": "789e4567-e89b-12d3-a456-426614174001",
    "tipo": "TRANSFERENCIA",
    "valor": 300.00,
    "contaDestinoId": "789e4567-e89b-12d3-a456-426614174002",
    "dataHora": "2026-01-25T16:00:00"
  }
]
```

---

## ⚠️ Tratamento de Erros

A API utiliza um sistema de tratamento centralizado de exceções através do `@ControllerAdvice`, garantindo respostas consistentes para todos os erros.

### Formato Padrão de Erro

Todos os erros retornam o seguinte formato:

```json
{
  "message": "Descrição do erro",
  "timeStamp": 1706194800000
}
```

### Tipos de Erros e Status HTTP

#### **400 - Bad Request**

Retornado quando há problemas com os dados enviados.

**Cenários:**
- Validação de campos falhou
- Saldo insuficiente
- Conta destino inválida
- Tentativa de transferência para a mesma conta

**Exemplos de respostas:**

**Validação de campos:**
```json
{
  "message": "{valor=O valor do depósito deve ser maior que zero, idConta=O id da conta é obrigatório}",
  "timeStamp": 1706194800000
}
```

**Saldo insuficiente:**
```json
{
  "message": "Saldo insuficiente para realizar a operação",
  "timeStamp": 1706194800000
}
```

**Transferência para mesma conta:**
```json
{
  "message": "Não é possível transferir para a mesma conta",
  "timeStamp": 1706194800000
}
```

**Conta destino inválida:**
```json
{
  "message": "Conta destino inválida ou inexistente",
  "timeStamp": 1706194800000
}
```

---

#### **404 - Not Found**

Retornado quando um recurso solicitado não existe.

**Cenários:**
- Conta não encontrada

**Exemplo:**
```json
{
  "message": "Conta não encontrada",
  "timeStamp": 1706194800000
}
```

---

#### **409 - Conflict**

Retornado quando há conflito com o estado atual do recurso.

**Cenários:**
- Cliente já possui conta
- Tentativa de criar conta duplicada

**Exemplos:**

```json
{
  "message": "Cliente já possui uma conta cadastrada",
  "timeStamp": 1706194800000
}
```

```json
{
  "message": "Já existe uma conta com esses dados",
  "timeStamp": 1706194800000
}
```

---

### Exceções Customizadas

O sistema possui as seguintes exceções customizadas:

| Exceção | Status HTTP | Descrição |
|---------|-------------|-----------|
| `ClienteJaExistenteException` | 409 Conflict | Cliente já possui conta cadastrada |
| `ContaJaExistenteException` | 409 Conflict | Conta já existe no sistema |
| `ContaNaoEncontradaException` | 404 Not Found | Conta não foi encontrada |
| `SaldoInsuficienteException` | 400 Bad Request | Saldo insuficiente para operação |
| `ContaDestinoInvalidaException` | 400 Bad Request | Conta destino é inválida |
| `TransferenciaParaMesmaContaException` | 400 Bad Request | Tentativa de transferir para mesma conta |
| `MethodArgumentNotValidException` | 400 Bad Request | Validação de campos falhou |

---

### Tratamento Global de Exceções

A classe `ControllerExceptionHandler` com `@ControllerAdvice` intercepta e trata todas as exceções da aplicação, garantindo:

✅ Respostas padronizadas para todos os erros  
✅ Status HTTP corretos para cada tipo de erro  
✅ Mensagens de erro claras e descritivas  
✅ Timestamp para rastreamento  
✅ Validação detalhada de múltiplos campos

**Separação de responsabilidades:**
- **Validações de formato** (campos obrigatórios, tipos, tamanhos) → Tratadas pelo `@Valid` e capturadas no `ControllerExceptionHandler`
- **Validações de negócio** (saldo, CPF na Receita Federal, regras bancárias) → Implementadas nos Services e lançam exceções customizadas

---

## 🧪 Testando a API

### Usando cURL

**Criar uma conta:**
```bash
curl -X POST http://localhost:8080/v1/contas \
  -H "Content-Type: application/json" \
  -d '{"idCliente":"123e4567-e89b-12d3-a456-426614174000"}'
```

**Realizar depósito:**
```bash
curl -X PATCH http://localhost:8080/v1/contas/deposito \
  -H "Content-Type: application/json" \
  -d '{
    "idConta":"789e4567-e89b-12d3-a456-426614174001",
    "valor":500.00
  }'
```

**Realizar saque:**
```bash
curl -X PATCH http://localhost:8080/v1/contas/saque \
  -H "Content-Type: application/json" \
  -d '{
    "idConta":"789e4567-e89b-12d3-a456-426614174001",
    "valor":50.00
  }'
```

**Consultar saldo:**
```bash
curl http://localhost:8080/v1/contas/123e4567-e89b-12d3-a456-426614174000
```

**Realizar transferência:**
```bash
curl -X POST http://localhost:8080/v1/transferencias \
  -H "Content-Type: application/json" \
  -d '{
    "idContaOrigem":"789e4567-e89b-12d3-a456-426614174001",
    "idContaDestino":"789e4567-e89b-12d3-a456-426614174002",
    "valor":300.00
  }'
```

**Consultar movimentações:**
```bash
curl -X POST http://localhost:8080/v1/accounts/789e4567-e89b-12d3-a456-426614174001/transactions \
```

### Usando Postman ou Insomnia

1. Importe a collection (se disponível) ou crie as requisições manualmente
2. Configure a Base URL: `http://localhost:8080/v1`
3. Use os exemplos acima para criar suas requisições
4. Teste os diferentes endpoints e cenários

---

## 🏗️ Arquitetura do Projeto

```
portalcliente/
├── src/main/java/br/com/coderbank/portalCliente/
│   ├── controllers/          # Controladores REST
│   │   ├── ContaController.java
│   │   ├── TransferenciaController.java
│   │   ├── MovimentacaoController.java
│   │   └── ControllerExceptionHandler.java  # Tratamento global de exceções
│   ├── dtos/                 # Data Transfer Objects
│   │   ├── request/          # DTOs de requisição
│   │   │   ├── ContaRequestDTO.java
│   │   │   ├── DepositoRequestDTO.java
│   │   │   ├── SaqueRequestDTO.java
│   │   │   └── TransferenciaRequestDTO.java
│   │   └── response/         # DTOs de resposta
│   │       ├── ContaResponseDTO.java
│   │       ├── OperacaoResponseDTO.java
│   │       ├── SaldoResponseDTO.java
│   │       ├── TransferenciaResponseDTO.java
│   │       ├── MovimentacaoResponseDTO.java
│   │       └── ErrorResponseDTO.java
│   ├── services/             # Camada de serviços (lógica de negócio)
│   │   ├── ContaService.java
│   │   └── MovimentacaoService.java
│   ├── repositories/         # Repositórios JPA
│   ├── entities/             # Entidades JPA
│   └── exceptions/           # Exceções customizadas
│       ├── ClienteJaExistenteException.java
│       ├── ContaJaExistenteException.java
│       ├── ContaNaoEncontradaException.java
│       ├── SaldoInsuficienteException.java
│       ├── ContaDestinoInvalidaException.java
│       └── TransferenciaParaMesmaContaException.java
└── src/main/resources/
    └── application.properties
```

### Padrão de Camadas

- **Controllers**: Recebem requisições HTTP, validam dados e delegam ao Service
- **Services**: Contêm a lógica de negócio e regras bancárias
- **Repositories**: Acessam e persistem dados no banco
- **DTOs**: Transferem dados entre camadas sem expor entidades
- **Entities**: Representam as tabelas do banco de dados
- **Exceptions**: Exceções customizadas para regras de negócio
- **ControllerExceptionHandler**: Tratamento centralizado de todas as exceções (@ControllerAdvice)

---

## 📄 Licença

Este projeto está sob a licença MIT. Isso significa:

✅ **Uso comercial permitido** - Você pode usar este projeto em aplicações comerciais  
✅ **Modificação permitida** - Você pode modificar o código conforme necessário  
✅ **Distribuição permitida** - Você pode distribuir o projeto  
✅ **Uso privado permitido** - Você pode usar para projetos privados

⚠️ **Condições:**
- Incluir uma cópia da licença e aviso de copyright em distribuições
- Não há garantias - o software é fornecido "como está"

Para mais detalhes, consulte o arquivo [LICENSE](LICENSE) na raiz do projeto.

---

## 👥 Contribuidores

### Owner / Maintainer

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/MarclHenrique">
        <img src="https://github.com/MarclHenrique.png" width="100px;" alt="Marcl Henrique"/>
        <br />
        <sub><b>Marcl Henrique</b></sub>
      </a>
      <br />
      <sub>Desenvolvedor Principal</sub>
    </td>
  </tr>
</table>

---

## 🎯 Roadmap

Funcionalidades planejadas para versões futuras:

- [ ] Autenticação e autorização (Spring Security + JWT)
- [ ] Limite de crédito para contas
- [ ] Agendamento de transferências
- [ ] Notificações por e-mail
- [ ] API de cartões
- [ ] Investimentos
- [ ] Documentação Swagger/OpenAPI
- [ ] Testes unitários e de integração
- [ ] Docker e Docker Compose
- [ ] CI/CD Pipeline

---



<div align="center">

**Desenvolvido com ❤️ por [Marcl Henrique](https://github.com/MarclHenrique)**


</div>