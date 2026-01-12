import service.Banco;

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
        while (true) {
            System.out.println("Escolha uma operação\n1 - Consultar saldo\n" +
                    "2 - Consultar cheque especial\n" +
                    "3 - Depositar dinheiro\n" +
                    "4 - Sacar dinheiro\n" +
                    "5 - Pagar um boleto\n" +
                    "6 - Verificar uso do cheque especial\n" +
                    "7 - Sair do APP\n");
            opcao = scanner.nextInt();
            if (opcao == 1) {
                conta.consultarSaldo();
            } else if (opcao == 2) {
                System.out.println("   Limite do Cheque Especial: R$ "
                        + String.format("%.2f", conta.consultarChequeEspecial()));
            } else if (opcao == 3) {
                System.out.print("\n    Valor a depositar: ");
                BigDecimal valor = scanner.nextBigDecimal();
                conta.depositarDinheiro(valor);
            } else if (opcao == 4) {
                System.out.print("\n    Valor a retirar: ");
                BigDecimal valor = scanner.nextBigDecimal();
                conta.sacarDinheiro(valor);
            } else if (opcao == 5) {
                System.out.print("\n    Valor do boleto: ");
                BigDecimal valorBoleto = scanner.nextBigDecimal();
                conta.pagarUmBoleto(valorBoleto);
            } else if (opcao == 6) {
                conta.verificarSttsChequeEspecial();
            } else if (opcao == 7) {
                System.out.println("Obrigado por usar nossos serviços! ;)");
                break;
            } else {
                System.out.println("OPÇÃO INVÁLIDA");
            }
        }
    }
}