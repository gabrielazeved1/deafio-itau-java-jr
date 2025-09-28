# API de Transações - Desafio Itaú Backend Jr

## Introdução

Este projeto consiste em uma API RESTful desenvolvida em Java com Spring Boot.  
Como estudante de Engenharia de Computação, decidi realizar este desafio como uma oportunidade para aplicar e aprofundar meus conhecimentos em desenvolvimento de software backend, enfrentando um problema prático do mercado.

O objetivo é criar um serviço que gerencia transações financeiras e retorna estatísticas em tempo real sobre elas.  
A aplicação permite adicionar novas transações, limpar todos os registros e consultar estatísticas (soma, média, mínimo, máximo e contagem) referentes às transações ocorridas nos últimos 60 segundos.

A solução foi projetada para ser simples, eficiente e performática, com foco em boas práticas de desenvolvimento e uma arquitetura limpa.

---

## Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework Principal:** Spring Boot 3.x
- **Build Tool:** Gradle
- **Documentação da API:** Springdoc OpenAPI (Swagger UI)
- **Utilitários:** Lombok
- **Ferramenta de Teste Manual:** Insomnia

---

## Estrutura do Projeto

O projeto segue uma arquitetura em camadas para separar responsabilidades, facilitando a manutenção e a escalabilidade.

```properties
├── src
│ ├── main
│ │ └── java
│ │ └── com
│ │ └── desafiojavajr
│ │ └── transacao_api
│ │ ├── business
│ │ │ └── services
│ │ │ ├── EstatisticasService.java # Lógica para calcular estatísticas
│ │ │ └── TransacaoService.java # Lógica para gerenciar transações
│ │ ├── controller
│ │ │ ├── dtos # Data Transfer Objects (DTOs)
│ │ │ ├── EstatisticasController.java # Endpoint para estatísticas
│ │ │ └── TransacaoController.java # Endpoints para transações
│ │ ├── infrastructure
│ │ │ └── exceptions # Exceções customizadas
│ │ └── TransacaoApiApplication.java # Ponto de entrada da aplicação
│ └── test # Testes unitários e de integração
├── build.gradle.kts # Arquivo de configuração e dependências (Gradle)
└── Dockerfile # Arquivo para containerização da aplicação
``` 
## Papel de Cada Arquivo Principal

- **TransacaoService:** É o coração da lógica de negócio. Valida e armazena as transações recebidas.

- **EstatisticasService:** Responsável por filtrar as transações dentro da janela de tempo e calcular as estatísticas usando `DoubleSummaryStatistics` para maior performance.

- **Controller.java:** Expõe os endpoints REST (`/transacao` e `/estatistica`) para o mundo externo, recebendo requisições e retornando respostas.

- **DTOs:** São objetos simples que carregam os dados entre o cliente e a aplicação, garantindo que a lógica de negócio não seja exposta diretamente.

- **build.gradle.kts:** O "mapa" do projeto para o Gradle. Define as informações do projeto, as versões das ferramentas e, mais importante, todas as bibliotecas e frameworks necessários (as dependências).

- **Dockerfile:** Uma "receita" que ensina o Docker a empacotar a aplicação em um contêiner, garantindo que ela rode de forma consistente em qualquer ambiente.

---

## Papel das Dependências

- **Spring Boot:** Framework principal que simplifica drasticamente a criação de aplicações Java. Ele gerencia a configuração, as dependências e fornece um servidor web embutido (Tomcat), permitindo que a aplicação rode de forma autônoma.

- **Spring Web:** Fornece os componentes para criar APIs RESTful, como as anotações `@RestController`, `@GetMapping`, `@PostMapping`, etc.

- **Spring Actuator:** Expõe endpoints de monitoramento e gerenciamento da aplicação (ex: `/actuator/health`).

- **Lombok:** Uma biblioteca que reduz código repetitivo (boilerplate) no Java, gerando automaticamente getters, setters e construtores através de anotações como `@RequiredArgsConstructor`.

- **Springdoc OpenAPI:** Gera automaticamente uma documentação interativa da API (Swagger UI) a partir do código, facilitando o teste e a compreensão dos endpoints.

---

## Armazenamento de Dados

Para a simplicidade e performance exigidas no desafio, os dados das transações são armazenados em memória, dentro de uma lista (`ArrayList`) no `TransacaoService`.

**Implicação importante:** Como os dados estão em memória, eles são voláteis. Isso significa que toda vez que a aplicação é reiniciada, todos os dados de transações são perdidos.

Esta abordagem foi escolhida por ser extremamente rápida e atender aos requisitos do desafio, que não exigia persistência em banco de dados.

## Como Executar a Aplicação

Certifique-se de ter o **Java 21 (JDK)** instalado.

---

### 1. Clone o Repositório

```bash
git clone <url-do-seu-repositorio>
```
### 2. Navegue até a Pasta do Projeto
```bash
cd transacao-api
```
### 3. Dê Permissão de Execução ao Gradle (Apenas na primeira vez)
```bash
chmod +x ./gradlew
```
### 4. Inicie a Aplicação
```bash
./gradlew bootRun
```
*A API estará disponível em: http://localhost:8080*

## Como Testar a API (usando Insomnia)

Eu utilizei o **Insomnia** para realizar os testes manuais dos endpoints.

---

### 1. Adicionar uma Transação

- **Método:** POST
- **URL:** `http://localhost:8080/transacao`
- **Body (JSON):** Envie um corpo com a data e hora no passado recente.

```json
{
    "valor": 150.75,
    "dataHora": "2025-09-22T17:30:00.000-03:00"
}
```
- Resposta Esperada: Status **201 Created.** 

### 2. Consultar as Estatísticas

- **Método:** GET
- **URL:** `http://localhost:8080/estatistica`

**Atenção:** Esta chamada deve ser feita em até 60 segundos após a adição da transação para que ela entre no cálculo.

- **Resposta Esperada:** Status `200 OK` com um JSON contendo as estatísticas.
```json
{
    "count": 1,
    "sum": 150.75,
    "avg": 150.75,
    "min": 150.75,
    "max": 150.75
}
-
```
### 3. Limpar Todas as Transações

- **Método:** DELETE
- **URL:** `http://localhost:8080/transacao`

- **Resposta Esperada:** Status `200 OK`.
---
## Documentação (Swagger UI)

A documentação interativa da API está disponível enquanto a aplicação estiver rodando.  
Acesse pelo navegador: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

Lá, você pode visualizar todos os endpoints e testá-los diretamente.

---
## Dificuldades e Aprendizados

Sendo um estudante de Engenharia de Computação, a realização deste desafio foi uma excelente jornada de aprendizado prático.  
A principal dificuldade conceitual encontrada foi gerenciar a janela de tempo de 60 segundos durante os testes manuais. Inicialmente, os resultados das estatísticas retornavam zerados, o que me levou a um processo de depuração sistemático para isolar a causa.

Este processo foi extremamente valioso, pois me permitiu aplicar na prática um método de diagnóstico: formular uma hipótese (o problema é a janela de tempo), criar um experimento para verificá-la (usar o parâmetro `intervaloBusca` com um valor alto) e analisar os resultados para confirmar a teoria.

Além disso, o desenvolvimento me proporcionou experiência prática na configuração de um projeto com Gradle, na gestão de dependências como Springdoc OpenAPI e na compreensão do fluxo de uma requisição em uma aplicação Spring Boot, desde o Controller até a camada de Serviço.

---

## Conclusão

A conclusão deste desafio representa mais do que a entrega de uma API funcional; simboliza um passo importante no meu desenvolvimento como futuro engenheiro de computação.

O projeto atende a todos os requisitos técnicos, implementando endpoints RESTful para gestão de transações com armazenamento em memória e cálculo de estatísticas em tempo real.

Mais importante, o processo solidificou conhecimentos fundamentais em **Java**, **Spring Boot**, e na metodologia de resolução de problemas de software.

---
**Gabriel Azevedo**  
Estudante de Engenharia de Computação - UFU  
Email: gazevedo.ti@gmail.com
