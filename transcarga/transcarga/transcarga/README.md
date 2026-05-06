# TransCarga - Sistema de Gerenciamento de Fretes

Sistema web em **Spring Boot** com arquitetura MVC em três camadas, baseado no tutorial TransCarga Java.

## Tecnologias

| Camada | Tecnologia |
|--------|-----------|
| Aplicação (View) | Thymeleaf + HTML/CSS |
| Negócios (Controller) | Spring MVC (`@Controller`) |
| Persistência (Model) | Spring Data JPA + Hibernate |
| Banco de dados | MariaDB (produção) / H2 (desenvolvimento) |
| Build | Maven |
| Runtime | Spring Boot 3.2 / Java 17 |

## Estrutura do Projeto

```
transcarga/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/br/com/transcarga/
    │   │   ├── TransCargaApplication.java     ← Ponto de entrada
    │   │   ├── DataLoader.java                ← Dados de exemplo
    │   │   ├── model/
    │   │   │   └── Frete.java                 ← Entidade JPA (Model)
    │   │   ├── persistencia/
    │   │   │   └── FreteRepository.java       ← DAO com Spring Data JPA
    │   │   └── negocios/
    │   │       ├── FreteService.java          ← Lógica de negócio
    │   │       ├── FreteController.java       ← Controller MVC (= FreteServlet)
    │   │       └── HomeController.java        ← Página inicial / dashboard
    │   └── resources/
    │       ├── application.properties         ← Configuração principal
    │       ├── application-h2.properties      ← Perfil H2 (padrão)
    │       ├── application-mariadb.properties ← Perfil MariaDB
    │       ├── templates/                     ← Views Thymeleaf
    │       │   ├── index.html
    │       │   ├── cadastrarFrete.html
    │       │   ├── listarFretes.html
    │       │   └── editarFrete.html
    │       └── static/css/
    │           └── style.css
    └── test/
        └── java/br/com/transcarga/
            └── TransCargaApplicationTests.java
```

## Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.8+

### Opção 1 — H2 (banco em memória, sem instalar nada)

```bash
# Clone / extraia o projeto
cd transcarga

# Execute diretamente
mvn spring-boot:run
```

Acesse: **http://localhost:8080/transcarga/**

> O banco H2 é criado em memória automaticamente. Os dados são perdidos ao reiniciar.

### Opção 2 — MariaDB (banco persistente)

1. Crie o banco no MariaDB:

```sql
CREATE DATABASE transcargadb CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
GRANT ALL PRIVILEGES ON transcargadb.* TO 'root'@'localhost';
FLUSH PRIVILEGES;
```

2. Edite `application-mariadb.properties` com seu usuário e senha.

3. Ative o perfil MariaDB em `application.properties`:

```properties
spring.profiles.active=mariadb
```

4. Execute:

```bash
mvn spring-boot:run
```

### Importar no Eclipse / IntelliJ

- **Eclipse**: `File → Import → Existing Maven Projects`
- **IntelliJ**: `Open` → selecione a pasta `transcarga`

## URLs do sistema

| URL | Descrição |
|-----|-----------|
| `http://localhost:8080/transcarga/` | Dashboard principal |
| `http://localhost:8080/transcarga/fretes/cadastrar` | Cadastrar frete |
| `http://localhost:8080/transcarga/fretes/listar` | Listar fretes |
| `http://localhost:8080/transcarga/h2-console` | Console H2 (perfil h2) |

## Equivalência com o Tutorial Original

| Tutorial (Servlets) | Spring Boot |
|--------------------|-------------|
| `FreteServlet.doPost()` | `FreteController.cadastrarFrete()` |
| `FreteServlet.doGet()` | `FreteController.listarFretes()` |
| `FreteDAO.java` | `FreteRepository.java` (Spring Data JPA) |
| `Frete.java` | `Frete.java` (com anotações JPA) |
| `persistence.xml` | `application.properties` |
| `cadastrarFrete.html` | `cadastrarFrete.html` (Thymeleaf) |
| `listarFretes.html` | `listarFretes.html` (Thymeleaf) |
| Tomcat (manual) | Spring Boot Embedded Tomcat |

## Funcionalidades

- ✅ Cadastrar frete (destino, peso, transportadora, status)
- ✅ Listar todos os fretes em tabela
- ✅ Editar frete existente
- ✅ Excluir frete (com confirmação)
- ✅ Busca por destino ou transportadora
- ✅ Filtro por status
- ✅ Dashboard com estatísticas
- ✅ Validação de campos no servidor
- ✅ Dados de exemplo carregados automaticamente
