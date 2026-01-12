# 🏦 Simulador de Conta Bancária (CLI)

Um projeto de console em Java que simula operações bancárias, demonstrando a implementação robusta de lógica financeira com foco na precisão monetária usando `BigDecimal`.

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Object-Oriented Programming](https://img.shields.io/badge/POO-Blue?style=for-the-badge&logo=databricks&logoColor=white)

---

## ✨ Funcionalidades

- **Depósito:** Adiciona valores à conta.
- **Saque:** Retira valores, com validação de saldo.
- **Cheque Especial:** Permite saques mesmo com saldo insuficiente, utilizando um limite pré-definido e registrando a dívida.
- **Amortização de Dívida:** Depósitos em conta com dívida no cheque especial quitam o débito prioritariamente.
- **Taxa de Juros:** Aplicação de taxa sobre o valor do cheque especial utilizado.

---

## 💡 Destaques Técnicos e Boas Práticas

Este projeto não é apenas sobre a lógica, mas sobre a forma **correta** de implementá-la em um contexto financeiro.

### A Escolha Certa para Dinheiro: `BigDecimal`
Para evitar os perigos de arredondamento e imprecisão de `float` e `double`, `BigDecimal` foi a escolha técnica central.

- **Operações Imutáveis e Seguras:** Toda aritmética retorna um novo objeto, garantindo que o estado da conta seja atualizado de forma explícita e segura.
  ```java
  // A variável `saldo` é reatribuída com o resultado da operação
  this.saldo = this.saldo.subtract(valorSaque);
  ```

- **Comparações Confiáveis:** Uso do `.compareTo()` para implementar regras de negócio, em vez de operadores não confiáveis para objetos.
  ```java
  // Verifica se o valor a sacar é maior que o saldo disponível
  if (valor.compareTo(this.saldo) > 0) {
      // ... Lógica do cheque especial
  }
  ```

- **Controle de Escala e Arredondamento:** Garante a precisão de 2 casas decimais, essencial em transações monetárias, usando `.setScale()`.
  ```java
  // Calcula e arredonda a taxa para garantir a precisão monetária
  BigDecimal taxa = valor.multiply(TAXA).setScale(2, RoundingMode.HALF_UP);
  ```

- **Lógica Elegante com `.min()`:** Determina de forma concisa o valor a ser quitado da dívida do cheque especial.
  ```java
  // Pega o menor valor entre o depósito e a dívida para amortização
  BigDecimal valorPago = valorDeposito.min(this.dividaChequeEspecial);
  ```

### Outros Conceitos Aplicados
- **Pilares da POO:** **Encapsulamento** para proteger os dados da conta e **Abstração** para modelar o comportamento do banco.
- **Código Limpo:** Uso de **Guard Clauses** para validações claras e redução de aninhamento de `if/else`.
- **Interface Interativa:** `Scanner` para criar um menu de usuário funcional no console.

---

## 🚀 Como Executar

1.  **Clone o repositório:**
    ```bash
    git clone https://github.com/seu-usuario/Sistema-Bancario.git
    ```

2.  **Navegue até a pasta do projeto:**
    ```bash
    cd Sistema-Bancario
    ```

3.  **Compile os arquivos-fonte:**
    O comando abaixo compila os fontes e coloca os arquivos `.class` no diretório `DB-Bank/out`, respeitando a estrutura de pacotes.
    ```bash
    javac -d DB-Bank/out DB-Bank/src/service/Banco.java DB-Bank/src/StartupBanco.java
    ```

4.  **Execute a aplicação:**
    Use a flag `-cp` para adicionar o diretório de saída ao *classpath* e execute a classe principal.
    ```bash
    java -cp DB-Bank/out StartupBanco
    ```
