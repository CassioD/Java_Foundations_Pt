# PROGRAMAÇÃO DE SOLUÇÕES COMPUTACIONAIS - Trabalho A3

## 📌 Visão Geral do Repositório

Este repositório contém o **Trabalho A3** da disciplina de **PROGRAMAÇÃO DE SOLUÇÕES COMPUTACIONAIS**.  
O trabalho foi desenvolvido pelos alunos do curso de graduação em **Big Data e Inteligência Analítica** da **Universidade Anhembi Morumbi**.

---

## 🖥️ Tecnologias e Conceitos Fundamentais

O desenvolvimento deste trabalho utiliza a linguagem de programação **Java**.  
O curso *Java Foundations* serve como base para a compreensão dos conceitos e práticas aplicadas.  

### Principais Tópicos Abordados

- **Fundamentos de Programação:** Entendimento do processo de desenvolvimento de software (Modelo Espiral), organização do código com espaços em branco e comentários, e a importância do método `main` como condutor do programa.  

- **Conceitos de Orientação a Objetos (POO):** Uma classe é concebida como um plano gráfico para um objeto, que é então usado para criar instâncias de objetos. Objetos são modelados como uma combinação de propriedades (campos de dados) e comportamentos (métodos).  

- **Tipos de Dados Java:**
  - **Variáveis:** Compreensão dos benefícios das variáveis e identificação dos principais tipos como `boolean`, `int`, `double` e `String`.  
  - **Dados Numéricos:** Diferenciação entre tipos de dados inteiros (`byte`, `short`, `int`, `long`) e de ponto flutuante (`float`, `double`), além de manipulação e cálculos matemáticos com a ordem das operações.  
  - **Dados Textuais:** Utilização do tipo `char` para caracteres únicos e `String` para sequências de caracteres, incluindo concatenação e sequências de escape para formatação de texto.  
  - **Conversão de Tipos:** Gerenciamento da promoção automática e conversão explícita (casting) entre tipos de dados, além da conversão de `Strings` para valores numéricos.  

- **Entrada de Usuário:** Métodos para coletar informações do usuário, como `JOptionPane` para caixas de diálogo e `Scanner` para entrada via console ou arquivos.  

- **Métodos e Classes da Biblioteca:**
  - **Métodos:** Estruturação do código, instanciação de objetos, uso do operador `.` para acessar campos e métodos, fornecimento de argumentos e retorno de valores.  
  - **Pacotes e `import`:** Acesso a classes por seu nome totalmente qualificado, função da instrução `import` e compreensão dos pacotes importados automaticamente (como `java.lang`).  
  - **Classes `String`, `Random`, `Math`:** Métodos e campos importantes dessas classes para manipulação de texto, geração de números aleatórios e cálculos matemáticos, respectivamente.  

- **Estruturas de Decisão e Loop:**
  - **Decisão:** Uso de variáveis `boolean`, operadores relacionais, construções `if`/`else`, operadores lógicos (`&&`, `||`, `!`), instruções `if` encadeadas e aninhadas, e a instrução `switch` para controle de fluxo.  
  - **Loop:** Implementação de loops `for`, `while` e `do-while` para repetição de código, incluindo o uso de `break` e `continue` para controlar o fluxo do loop.  

- **Criação Avançada de Classes:**
  - **Construtores:** Entendimento dos valores padrão, referências `null`, construtor padrão e escrita de construtores com argumentos para inicializar campos, além do uso da palavra-chave `this`.  
  - **Sobrecarga de Métodos:** Definição de múltiplos construtores e métodos com o mesmo nome, mas com assinaturas diferentes (número, tipo ou ordem dos parâmetros).  
  - **Interação e Encapsulamento:** Detalhamento da interação entre objetos e o uso do modificador `private` com campos, além da criação de métodos `getter` e `setter`.  
  - **Variáveis e Métodos Estáticos:** Descrição e uso de variáveis e métodos estáticos, incluindo a palavra-chave `final` com variáveis estáticas.  

- **Estruturas de Dados Dinâmicas:**
  - **Arrays:** Criação, inicialização, modificação e iteração de arrays unidimensionais, e identificação da exceção `ArrayIndexOutOfBoundsException`.  
  - **ArrayLists:** Criação e manipulação de `ArrayLists` usando métodos como `add`, `get`, `remove`, e percorrimento com iteradores e loops `for-each`. Inclui ainda o uso de classes wrapper e autoboxing para tipos primitivos.  

- **Tratamento de Exceções:** Explicação da finalidade do tratamento de exceções, uso das construções `try`/`catch` e descrição de exceções comuns em Java.  

- **Depuração:** Técnicas de teste e depuração de programas Java, identificação de erros (compilação, lógicos, de tempo de execução) e uso de instruções `print` e do depurador da IDE.  

- **JavaFX (Interfaces Gráficas):** Introdução à criação de aplicativos GUI com JavaFX, incluindo componentes como *Nodes*, *Panels*, *Scene Graph*, *Scenes* e *Stages*. Também manipulação de cores, formas, elementos gráficos, áudio e eventos do mouse, com a utilização de expressões Lambda.  

---

## 🚀 Como Abrir e Executar o Projeto

## 📋 Requisitos

Para compilar e executar este projeto, você precisará ter o seguinte software instalado e configurado no seu ambiente:

1. **Java Development Kit (JDK)**  
   - **Versão:** 21 ou superior.  
   - **Download:** Você pode baixar o JDK do Oracle ou de uma distribuição OpenJDK como o *Eclipse Temurin (Adoptium)*.  
   - **Verificação:** Abra um terminal e execute:  
     ```bash
     java --version
     ```

2. **Apache Maven**  
   - **Versão:** 3.8.x ou superior.  
   - **Download e Instalação:** Siga o [guia oficial de instalação do Maven](https://maven.apache.org/install.html).  
   - É crucial configurar as variáveis de ambiente `M2_HOME` e `PATH`.  
   - **Verificação:**  
     ```bash
     mvn --version
     ```

3. **Visual Studio Code**  
   - O editor de código onde o projeto será gerenciado.  

4. **Extensões do VS Code**  
   - Instale o pacote de extensões **"Extension Pack for Java"** da Microsoft.  
   - Ele inclui todo o suporte necessário para desenvolvimento Java e Maven no VS Code.  

5. **Servidor de Banco de Dados MySQL**  
   - **Versão:** 8.0 ou superior.  
   - Pode ser usado via instalação local, container Docker ou serviço em nuvem.  
   - **Ferramenta de Gestão:** Recomenda-se **MySQL Workbench** ou **DBeaver** para facilitar a administração do banco de dados.  

---

## ⚙️ Passo a Passo para Instalação e Execução

### 1. Clonar o Repositório
Abra um terminal ou Git Bash e clone o projeto para a sua máquina local:  

```bash
git clone <URL_DO_SEU_REPOSITORIO>
cd sistema-gestao-ccks
```
## 2. Configurar o Banco de Dados

1. Conecte-se ao seu servidor **MySQL** usando sua ferramenta preferida.  
2. Abra o arquivo `gestao_projetos_db.sql` que está na raiz do projeto.  
3. Execute o script completo.  

O script irá:  
- Criar o banco de dados `gestao_projetos_db`.  
- Criar as tabelas necessárias (`users`, `projects`, etc.).  
- Inserir um usuário administrador inicial:  
  - **Login:** `admin`  
  - **Senha:** `admin123`  

---

## 3. Configurar a Conexão com o Banco

No código do projeto, ajuste as credenciais de conexão.
Altere as variáveis de conexão para refletirem o seu ambiente:

```bash
private static final String URL = "jdbc:mysql://localhost:3306/gestao_projetos_db";
private static final String USER = "seu_usuario";
private static final String PASSWORD = "sua_senha";
```
## 4. Abrir o Projeto no VS Code

1. Abra o **Visual Studio Code**.  
2. Vá em `File > Open Folder...` e selecione a pasta `sistema-gestao-ccks`.  
3. Aguarde o reconhecimento do `pom.xml` e o download das dependências do **Maven**.  

---

## 5. Compilar e Executar a Aplicação

### 🔨 Compilando e Gerando o Executável

Abra um terminal na pasta raiz do projeto:

```bash
cd c:\projeto\Java_Foundations_Pt\sistema-gestao-ccks\
mvn clean package
```
- **clean:** Remove compilações anteriores.  
- **package:** Compila, executa os testes e gera o `.jar`.  

Após a compilação, o Maven criará o arquivo executável em:  
```bash
target/sistema-gestao-ccks-1.0-SNAPSHOT.jar
```
### ▶️ Executar a Aplicação

Para iniciar o sistema:
```bash
java -jar target/sistema-gestao-ccks-1.0-SNAPSHOT.jar
```
Se tudo estiver correto, a tela de login será exibida.

--- 
Oracle, Java e MySQL são marcas comerciais registradas da Oracle Corporation e/ou de suas empresas afiliadas.  
Outros nomes podem ser marcas comerciais de seus respectivos proprietários.
