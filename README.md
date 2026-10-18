# OSINT API

Sistema de coleta de informações públicas (OSINT) em duas partes:

- **Cliente Python**: consulta Whois, Shodan e theHarvester e envia o resultado para a API.
- **API Java (Spring Boot)**: recebe os dados por HTTP e os grava em um banco MySQL.

```
Python (coleta) --PUT JSON--> Spring Boot (API) --JPA/Hibernate--> MySQL
Python          <--GET JSON-- Spring Boot (API) <------------------
```

## Ferramentas

| Ferramenta | O que coleta | Endpoint da API |
|---|---|---|
| Whois | registrador, datas de criação e expiração, servidores de nome, país | `/whois` |
| Shodan | organização, país e portas abertas de um IP | `/shodan` |
| theHarvester | e-mails, hosts e IPs de um domínio | `/harvester` |

Cada endpoint tem:

- `PUT /<ferramenta>/put`: grava um novo resultado (cada consulta cria uma linha nova, formando um histórico).
- `GET /<ferramenta>/get`: devolve o resultado mais recente.
- `GET /<ferramenta>/todos`: lista todo o histórico.

## Tecnologias

- Java 21, Spring Boot 4, Spring Data JPA (Hibernate), Maven
- MySQL
- Python 3 com `python-whois`, `requests` e `shodan`
- [theHarvester](https://github.com/laramies/theHarvester)

## Estrutura

```
.
├── pom.xml
├── src/
│   ├── main/java/com/springboot/api/   controllers, entidades e repositories
│   ├── main/resources/application.properties
│   └── test/java/com/springboot/api/
├── main.py        menu principal
├── Menu.py
├── Whois.py
├── Shodan.py
├── Harvester.py
└── requirements.txt
```

## Como rodar

### 1. Banco de dados

Com o MySQL ligado, crie o banco. As tabelas são criadas sozinhas pelo Hibernate quando a API sobe.

```sql
CREATE DATABASE osint CHARACTER SET utf8mb4;
```

### 2. API Java

A senha do MySQL **não** fica no código. Defina a variável de ambiente `DB_PASSWORD` antes de subir a API.

No Windows (PowerShell):

```powershell
$env:DB_PASSWORD="sua_senha"
mvn spring-boot:run
```

No IntelliJ: *Run > Edit Configurations > Modify options > Environment variables* e informe `DB_PASSWORD=sua_senha`.

A API sobe em `http://localhost:8080`.

### 3. Cliente Python

```bash
pip install -r requirements.txt
```

Para usar o Shodan, configure sua chave de API como variável de ambiente (nunca escreva a chave no código):

```powershell
$env:SHODAN_API_KEY="sua_chave"
```

Para usar o theHarvester, instale-o separadamente e confirme que o comando `theHarvester` funciona no terminal:

```bash
git clone https://github.com/laramies/theHarvester
cd theHarvester
pip install .
```

Depois, com a API rodando:

```bash
python main.py
```

No menu, escolha `whois`, `shodan` ou `harvester` e informe o alvo:

- Whois e theHarvester: um **domínio** (ex.: `exemplo.com`)
- Shodan: um **endereço IP**

### 4. Conferir os dados

```sql
USE osint;
SELECT * FROM whois_resultado;
SELECT * FROM shodan_resultado;
SELECT * FROM harvester_resultado;
```

Ou pelo navegador: `http://localhost:8080/whois/todos`.

## Uso responsável

Este projeto é para estudo. Consulte apenas domínios e IPs públicos ou sobre os quais você tem autorização. Respeite os termos de uso das fontes consultadas.

## Próximos passos

- Adicionar `dnspython` (registros DNS) e `ipwhois` (informações de IP)
- Trocar os campos públicos das entidades por getters/setters ou Lombok
- Validar a entrada do usuário antes de consultar as fontes
