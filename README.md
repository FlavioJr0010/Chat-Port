# Chat Server

Um projeto de chat em Java que implementa comunicação cliente-servidor via sockets TCP. O servidor registra mensagens, conexões e desconexões em um arquivo de log (`chat_log.txt`) com timestamp, nome do cliente e conteúdo. Os clientes podem enviar mensagens e encerrar a conexão digitando `exit` ou `sair`.

## ✅ Pré-requisitos

Para executar o projeto, você precisa dos seguintes itens:

- **JDK 17 ou superior** (recomendado: JDK 21)
    - O projeto utiliza features compatíveis com Java 21 (bytecode 65.0).
    - Verifique sua versão do Java com:
      ```bash
      java -version
      ```
    - Caso esteja desatualizado, baixe a versão mais recente do JDK em: [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) ou [OpenJDK](https://openjdk.java.net/).

- **IDE (opcional, mas recomendada)**:
    - IntelliJ IDEA
    - Eclipse
    - Visual Studio Code com extensão de Java

- **Terminal ou prompt de comando**:
    - Certifique-se de que `javac` e `java` estão configurados no PATH do sistema.

## 🧪 Como rodar o projeto

Siga os passos abaixo para compilar e executar o projeto:

### 1. Compile os arquivos
Navegue até o diretório raiz do projeto, onde estão os arquivos `ChatServer.java`, `Client.java` e `ClientHandler.java`, e compile-os:

```bash
javac Server/ChatServer.java Client/Client.java Client/Handler/ClientHandler.java
```

### 2. Inicie o servidor
No mesmo diretório raiz do projeto, execute o servidor usando o comando abaixo:

```bash
java -cp . Server.ChatServer 
```

Nota sobre o comando:
O -cp . define o classpath para o diretório atual (.), que é necessário porque os arquivos .class estão em subpastas (Server/ e Client/).

Server.ChatServer é o nome da classe principal, seguindo a estrutura de pacotes.

Quando solicitado, digite uma porta (exemplo: 5000). Certifique-se de que a porta escolhida não está em uso por outros aplicativos.

Se tudo estiver correto, o servidor exibirá a mensagem: [Servidor] Aguardando conexão na porta 5000.

O servidor ficará ativo, esperando conexões de clientes, e criará (ou atualizará) o arquivo chat_log.txt no mesmo diretório para registrar eventos.

### 3. Inicie o cliente

Abra um novo terminal, navegue até o mesmo diretório raiz do projeto e execute o cliente:

```bash
java -cp . Client.Client 
```

Forneça as informações solicitadas:
Host: Digite localhost (se o servidor estiver na mesma máquina).

Porta: Use a mesma porta do servidor (exemplo: 5000).

Nome do cliente: Escolha um nome de usuário, como flavio123.

Após conectar, você verá: Conectado ao servidor! e Nome enviado ao servidor!.

Digite mensagens para enviar ao servidor. Elas serão exibidas no terminal do servidor e registradas no arquivo chat_log.txt.

Para encerrar a conexão, digite exit ou sair. O cliente exibirá Encerrando conexão... e Conexão encerrada., e o servidor registrará a desconexão no log.

### 4. Conecte múltiplos clientes (opcional)

Para testar com vários clientes, repita o passo 3 em novos terminais, fornecendo diferentes nomes de usuário. O servidor lidará com todos os clientes simultaneamente, registrando as mensagens de cada um no chat_log.txt.


## 📁Arquivo de log
O servidor cria automaticamente um arquivo chat_log.txt no mesmo diretório onde é executado.

O log registra:
Conexões e desconexões de clientes

Mensagens enviadas pelos clientes, com timestamp, nome do cliente e conteúdo

Erros, se ocorrerem

#### Exemplo de entradas no log:

```bash
[2025-05-22T14:50:00] flavio123 conectou.
[2025-05-22T14:50:05] flavio123: Olá, mundo!
[2025-05-22T14:50:10] flavio123 desconectou.
```

## 📝Estrutura do projeto

```bash
ChatServer/
├── Server/
│   └── ChatServer.java       # Servidor principal que aceita conexões
├── Client/
│   └── Client.java           # Cliente que se conecta ao servidor
│   └── Handler/
│       └── ClientHandler.java # Manipula mensagens de um cliente no servidor
└── chat_log.txt              # Arquivo de log gerado pelo servidor
```

## ⚙️Funcionalidades
Comunicação cliente-servidor via sockets TCP.

Suporte a múltiplos clientes simultâneos, cada um gerenciado em uma thread separada.

Registro de mensagens, conexões, desconexões e erros em um arquivo de log (chat_log.txt).

Encerramento da conexão pelo cliente ao digitar exit ou sair.

Interface simples via terminal para entrada e saída de dados.

