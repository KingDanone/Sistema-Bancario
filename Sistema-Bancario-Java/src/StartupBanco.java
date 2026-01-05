package abstracao.banco;

import java.math.BigDecimal;
import java.util.Scanner;

public class StartupBanco {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Banco conta = null;

        System.out.println("--- Bem vindo ao BDbank ---\n");
        System.out.print("Digite o valor do depósito inicial: R$ ");
        BigDecimal depositoInicial = scanner.nextBigDecimal();

        conta = new Banco(depositoInicial);
        System.out.println("\n✅ Conta criada com sucesso!");
        System.out.println("   Saldo inicial: R$ " + String.format("%.2f", depositoInicial));
        System.out.println("   Limite cheque especial: R$ " + String.format("%.2f", conta.consultarChequeEspecial()));

        int opcao;
        do {
            System.out.println("Escolha uma operação\n1 - Consultar saldo\n" +
                    "2 - Consultar cheque especial\n" +
                    "3 - Depositar dinheiro\n" +
                    "4 - Sacar dinheiro\n" +
                    "5 - Pagar um boleto\n" +
                    "6 - Verificar uso do cheque especial\n" +
                    "7 - Sair do APP\n");
            opcao = scanner.nextInt();
        } while (true);

    }
}