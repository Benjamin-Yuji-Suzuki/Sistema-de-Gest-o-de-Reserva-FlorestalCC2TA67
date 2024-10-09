# Sistema de Gestão de Reserva Florestal - CC2TA67
Projeto desenvolvido sob orientação do Professor Isacc.

## Sumário
- [Visão Geral](#visão-geral)
- [Requisitos do Sistema](#requisitos-do-sistema)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Estrutura do Projeto](#estrutura-do-projeto)
- [Instruções de Uso](#instruções-de-uso)
- [Critérios de Avaliação](#critérios-de-avaliação)
- [Equipe](#equipe)

## Visão Geral
O projeto visa criar um sistema de gestão para reservas florestais, permitindo o cadastro de espécies (animais e plantas), monitoramento de áreas de preservação e registro de avistamentos de espécies. Este sistema facilita o gerenciamento e controle das espécies que habitam essas áreas, oferecendo uma ferramenta eficiente para acompanhamento e preservação da biodiversidade.

## Requisitos do Sistema
O sistema possui os seguintes requisitos funcionais:

### Cadastro de Espécies
- **Capacidade para até 200 espécies** em uma estrutura de array.
- Cada espécie cadastrada deve incluir:
  - **Nome** (string)
  - **Tipo** (planta ou animal)
  - **Status de Ameaça** (indicando se a espécie está ameaçada ou não)

### Monitoramento de Áreas
- **Capacidade para até 20 áreas monitoradas** em uma array.
- Cada área deve possuir:
  - **Nome da Área** (string)
  - **Localização** (coordenadas geográficas ou descrição textual)
  - **Tamanho em Hectares** (float ou double)

### Registro de Avistamento
- Registro de avistamento de espécies em áreas monitoradas para acompanhamento da fauna e flora.

### Listagem de Dados
- Função para listar todas as espécies e áreas monitoradas, exibindo as informações cadastradas.

## Requisitos Mínimos
- O projeto deve ser desenvolvido utilizando **Maven** (ou Gradle, porém a escolha foi Maven).
- Deve possuir uma classe principal, com nome como **Main**, **App**, ou **Principal**.
- Implementar um **menu interativo** para navegação e utilização do sistema pelo usuário.

## Tecnologias Utilizadas
Para enriquecer a funcionalidade e agregar valor ao projeto, todas as seguintes tecnologias externas foram integradas:

1. **Banco de Dados**: Para persistência e manipulação eficiente dos dados.
2. **Manipulação de Arquivos CSV**: Importação e exportação dos dados em formato CSV para facilitar a análise e armazenamento externo.
3. **API de Geolocalização**: Para registro e manipulação das coordenadas das áreas monitoradas.
4. **Testes Unitários**: Para validação de métodos e funcionalidades, assegurando a integridade do código.
5. **Interface Gráfica**: Implementação de uma interface visual amigável para interação com o sistema.

## Estrutura do Projeto
O projeto foi desenvolvido seguindo as melhores práticas de programação e princípios de Orientação a Objetos (POO):

- **src/main/java/**: Código-fonte principal.
  - **models/**: Classes de modelos (Espécie, Área, Avistamento).
  - **services/**: Lógica de negócios e manipulação de dados.
  - **controllers/**: Controle de fluxo e gerenciamento de interação do usuário.
  - **utils/**: Funções auxiliares para manipulação de dados, integração com APIs, e outras ferramentas de suporte.
- **src/main/resources/**: Recursos estáticos e arquivos de configuração, como arquivos CSV.
- **src/test/java/**: Testes unitários para validação das funcionalidades.

## Instruções de Uso
### Pré-requisitos
- **JavaFX 23**
- **Java JDK 23** ou superior.
- **Maven** para gerenciamento de dependências.

### Como Executar
1. **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/Sistema-de-Gestao-de-Reserva-Florestal.git
    cd Sistema-de-Gestao-de-Reserva-Florestal
    ```
2. **Compile e execute o projeto com Maven:**
    ```bash
    mvn clean install
    mvn exec:java -Dexec.mainClass="caminho.da.sua.classe.Main"
    ```
3. **Menu Interativo**: Utilize o menu interativo para cadastrar espécies, monitorar áreas e registrar avistamentos.

### Importação/Exportação de Dados CSV
- O sistema oferece suporte à importação e exportação de dados em formato **CSV** para fácil manipulação e armazenamento.
- Os arquivos CSV devem ser armazenados na pasta **src/main/resources/csv/**.

## Critérios de Avaliação
A avaliação do projeto será baseada nos seguintes critérios:

1. **Cumprimento dos Requisitos Mínimos**: Todas as funcionalidades obrigatórias devem ser implementadas.
2. **Qualidade do Código Fonte**: Código limpo, organizado e bem documentado.
3. **Implementação Eficaz de Funcionalidades**: Boa prática de programação e otimização.
4. **Aplicação Correta de POO**: Uso adequado de classes, métodos, encapsulamento e outros princípios de Orientação a Objetos.
5. **Integração das Tecnologias Externas**: Funcionamento completo das tecnologias externas, incluindo banco de dados, manipulação de arquivos CSV, API de geolocalização, testes unitários e interface gráfica.

## Equipe
- **Brenda dos Santos Nascimento**: Desenvolvimento da **API de Geolocalização**, Corretora de **importação e exportação de arquivos CSV** e Implementação do **README**.
- **Felipe de Freitas**: Implementação de **testes Unitários** e **banco de dados**.
- **Benjamin Yuji Suzuki**: Desenvolvimento da **interface gráfica**, Corretor de **banco de dados**, **importação e exportação de arquivos CSV**, **API de Geolocalização**, **README** e Incrementação de **teste unitário**.
