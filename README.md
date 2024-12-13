
# Implementação de Blockchain

Criação de uma blockchain simples em Java, simulando um sistema básico de cadeia de blocos com transações, hashing e funcionalidades como propagação de blocos e transações, resolução de conflitos (forks), controle de saldos e taxas de transação. Buscando garantir a integridade dos dados e a sincronização entre nós, com foco na aprendizagem dos conceitos fundamentais de blockchain. Atividade proposta durante a trilha do programa de bolsas de Blockchain da Compass - UOL.

---

## Pré-Requisitos

- Java 8 ou superior;
- Não são necessárias bibliotecas adicionais.

---

## Como Executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/AugNuneS/Blockchain_Project.git
   ```

2. Abra o projeto em sua IDE (como VSCode, IntelliJ ou Eclipse).

3. Compile e execute a classe `Main`:
   - As transações são criadas;
   - Novos blocos são adicionados à blockchain;
   - A blockchain é validada para garantir sua integridade.

---

## Funcionalidades

- **Bloco Gênesis**: O primeiro bloco na cadeia é criado automaticamente durante a inicialização.
- **Validação de Blocos**: Cada bloco contém uma referência ao hash do bloco anterior, e a cadeia pode ser validada para garantir sua integridade.
- **Validação de Transações**: As transações dentro dos blocos são verificadas para garantir que não existam valores inválidos, como valores negativos, e que os endereços dos remetentes e destinatários sejam válidos (endereços de 48 caracteres hexadecimais).
- **Proof of Work (PoW)**: O algoritmo Proof of Work é implementado, garantindo que os blocos sejam minerados com uma dificuldade configurável.
- **Hashing**: Os dados de cada bloco são protegidos por hashing usando o algoritmo SHA-256, mantendo a segurança e a imutabilidade dos dados.
- **Propagação de Blocos e Transações**: Os nós podem compartilhar novos blocos e transações entre si, garantindo que todos mantenham uma versão atualizada da blockchain. Isso foi implementado com métodos que simulam a troca de informações e a disseminação pela rede.
- **Resolução de Conflitos (Forks)**: Quando há dois blocos competindo para ser o próximo, a rede entra em um "fork". Para resolver isso, a cadeia mais longa é adotada como válida. Cada nó compara as cadeias recebidas e escolhe a maior.
- **Estado Global e Controle de Saldos**: Cada endereço tem um saldo associado, e as transações não podem ser feitas se o saldo for insuficiente. O controle de saldos é feito por um mapa, atualizando os saldos sempre que um bloco é minerado e adicionado à cadeia.
- **Taxas de Transação e Recompensas**: Cada transação agora inclui uma taxa, que é calculada subtraindo o valor da transação do saldo do remetente. Essas taxas são somadas à recompensa do minerador no momento da mineração do bloco. A recompensa total do minerador é composta por uma quantia base e a soma das taxas de todas as transações incluídas no bloco.

---

## Exemplo de Saída:

```
A blockchain eh valida? Sim

No 01 recebeu uma nova transacao:
Remetente: aabbccddeeff00112233445566778899aabbccddeeff0011, Destinatario: 112233445566778899aabbccddeeff001122334455667788, Quantidade: 50.0, Taxa: 1.0

No 01 recebeu uma nova transacao:
Remetente: 112233445566778899aabbccddeeff001122334455667788, Destinatario: aabbccddeeff00112233445566778899aabbccddeeff0011, Quantidade: 20.0, Taxa: 0.5

No 02 recebeu uma nova transacao:
Remetente: aabbccddeeff00112233445566778899aabbccddeeff0011, Destinatario: 112233445566778899aabbccddeeff001122334455667788, Quantidade: 50.0, Taxa: 1.0

No 03 recebeu uma nova transacao:
Remetente: aabbccddeeff00112233445566778899aabbccddeeff0011, Destinatario: 112233445566778899aabbccddeeff001122334455667788, Quantidade: 50.0, Taxa: 1.0

Saldos no 01 antes da mineracao:
Endereco: 112233445566778899aabbccddeeff001122334455667788 | Saldo: 500.0
Endereco: aabbccddeeff00112233445566778899aabbccddeeff0011 | Saldo: 1000.0

No 01 minerou um novo bloco! Recompensa total: 53.0
Bloco: 0003e26e20f2a2c29affec63dcbdd3a25c6e88e3660f3370aca325b32bbe1b36
Hash Anterior: 0003921ce1265dc5e19626744bdc5da3135cb69fb8ea254fcfc643df02546d7f
Timestamp: 1734101906904
Nonce: 1842
A cadeia mais longa foi adotada!
No 01 propagou o bloco para o 02
A cadeia mais longa foi adotada!
No 01 propagou o bloco para o 03

Saldos no 01 apos a mineracao:
Endereco: 112233445566778899aabbccddeeff001122334455667788 | Saldo: 559.0
Endereco: aabbccddeeff00112233445566778899aabbccddeeff0011 | Saldo: 938.0

No 02 minerou um novo bloco! Recompensa total: 51.5
Bloco: 000df43726cce67ca35a58b47980f2343fd7f86a45a79dd29092e0f18d860518
Hash Anterior: 0003e26e20f2a2c29affec63dcbdd3a25c6e88e3660f3370aca325b32bbe1b36
Timestamp: 1734101907000
Nonce: 23993

No 02 propagou o bloco para o 01
No 02 propagou o bloco para o 03

Saldos no 02 apos a mineracao:
Endereco: 112233445566778899aabbccddeeff001122334455667788 | Saldo: 529.5
Endereco: aabbccddeeff00112233445566778899aabbccddeeff0011 | Saldo: 969.0
```

---

## Estrutura do Projeto

### Blockchain.java
- Gerencia a blockchain como uma lista encadeada de objetos `Bloco` utilizando `LinkedList`.
- Possui o método `adicionarBloco()` para adicionar blocos com transações validadas.
- Contém o método `validarBlockchain()` para garantir a integridade da cadeia, verificando os hashes.
- Verifica a validade dos endereços de remetentes e destinatários nas transações.
- Implementa uma validação para impedir a adição de blocos com transações vazias.
- Implementa a propagação de blocos e transações entre nós para garantir que todos os nós tenham as informações mais recentes.
- Contém a lógica de resolução de forks, adotando sempre a cadeia mais longa.

### Bloco.java
- Cada bloco contém uma referência ao hash do bloco anterior, um timestamp, uma lista de transações, o nonce e a dificuldade.
- Utiliza o algoritmo SHA-256 para calcular o hash e garantir a integridade dos dados por meio do método `calcularHash()`.
- Implementa o algoritmo de Proof of Work (PoW) no método `realizarProofOfWork()` para garantir que os blocos sejam minerados com a dificuldade desejada.
- Adiciona a lógica para incluir transações com taxas de transação e recompensa no minerador.

### Transacao.java
- Representa uma transação entre dois usuários, com detalhes como remetente, destinatário e valor.
- Inclui uma validação para garantir que os endereços dos remetentes e destinatários tenham 48 caracteres hexadecimais válidos.
- Garante que os valores das transações sejam positivos.
- Inclui o campo de taxa de transação, que é calculado e somado à recompensa do minerador.

### Main.java
- Demonstra o uso da blockchain, adicionando blocos e imprimindo os detalhes da cadeia de blocos.
- Exibe o histórico de transações para um endereço específico.
- Simula a comunicação entre nós para propagar novos blocos e transações.
- Implementa a resolução de forks, garantindo que a rede adote a cadeia mais longa.
- Exibe as recompensas dos mineradores e as taxas das transações.

---

## Exemplo de Uso

Esta implementação pode ser usada como base para entender conceitos de blockchain. Ela simula princípios básicos, como:
- Blocos encadeados por meio de hashes;
- Imutabilidade dos dados por meio de hashing;
- Validações simples de transações;
- Proof of Work para garantir a dificuldade na mineração de blocos;
- Propagação de blocos e transações entre nós;
- Resolução de forks e adoção da cadeia mais longa;
- Controle de saldos de endereços e validação de transações;
- Taxas de transação como incentivo para mineradores.
