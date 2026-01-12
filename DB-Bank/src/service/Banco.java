package service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Banco {
    private BigDecimal saldo;
    private BigDecimal limiteChequeEspecial;
    private BigDecimal saldoEspecialUsado;
    private final BigDecimal LIMITE_BASE = new BigDecimal("500.00");
    private final BigDecimal CHEQUE_ESPECIAL_MINIMO = new BigDecimal("50.00");
    private final BigDecimal PERCENTUAL_CHEQUE = new BigDecimal("0.50");
    private final BigDecimal TAXA_USO_CHEQUE = new BigDecimal("0.20");


    public Banco(BigDecimal depositoInicial) {
        this.saldo = depositoInicial;
        this.saldoEspecialUsado = BigDecimal.ZERO;

        if (depositoInicial.compareTo(LIMITE_BASE) <= 0) {
            this.limiteChequeEspecial = CHEQUE_ESPECIAL_MINIMO;
        } else {
            this.limiteChequeEspecial = depositoInicial.multiply(PERCENTUAL_CHEQUE).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public void consultarSaldo() {
        System.out.println("\n--- Saldo em conta: " + this.saldo + " ---\n");
        System.out.println("--- Dívida no Cheque Especial: R$ " + this.saldoEspecialUsado + " ---\n");

    }

    public BigDecimal consultarChequeEspecial() {
        return this.limiteChequeEspecial;
    }

    public void depositarDinheiro(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("\nERROR: Invalid value\n");
            return;
        }
        if (this.saldoEspecialUsado.compareTo(BigDecimal.ZERO) <= 0) {
            this.saldo = this.saldo.add(valor).setScale(2, RoundingMode.HALF_UP);
            System.out.println("Depósito de R$ " + valor + " realizado com sucesso.\n");
            return;
        }
        System.out.println("--- Realizando depósito para quitar dívida do Cheque Especial ---");
        BigDecimal valorPagoDaDivida = valor.min(this.saldoEspecialUsado);
        this.saldoEspecialUsado = this.saldoEspecialUsado.subtract(valorPagoDaDivida).setScale(2, RoundingMode.HALF_UP);

        BigDecimal restoDoDeposito = valor.subtract(valorPagoDaDivida).setScale(2, RoundingMode.HALF_UP);
        this.saldo = this.saldo.add(restoDoDeposito).setScale(2, RoundingMode.HALF_UP);

        BigDecimal taxa = valorPagoDaDivida.multiply(TAXA_USO_CHEQUE).setScale(2, RoundingMode.HALF_UP);
        System.out.println("Taxa de 20% calculada sobre o pagamento: R$" + taxa);
        if (this.saldo.compareTo(taxa) >= 0) {
            System.out.println("Pagando taxa a partir do saldo...");
            this.saldo = this.saldo.subtract(taxa).setScale(2, RoundingMode.HALF_UP);
        } else {
            System.out.println("Saldo insuficiente para pagar a taxa. Adicionando taxa à dívida do Cheque Especial.");
            this.saldoEspecialUsado = this.saldoEspecialUsado.add(taxa).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public void sacarDinheiro(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("\nERROR: Invalid value\n");
            return;
        }
        BigDecimal chequeEspecialDisponivel = this.limiteChequeEspecial.subtract(saldoEspecialUsado);
        if (valor.compareTo(this.saldo.add(chequeEspecialDisponivel).setScale(2, RoundingMode.HALF_UP)) > 0) {
            System.out.println("--- Valor em conta insuficinete para este saque ---");
            System.out.println("    Adicione mais capital ao seu caixa usando a opção -> [3]");
            return;
        }
        if (this.saldo.compareTo(valor) >= 0) {
            this.saldo = this.saldo.subtract(valor).setScale(2, RoundingMode.HALF_UP);
            System.out.println("Amount of R$" + valor + " successfully withdrawn!\n");
            return;
        }
        if (valor.compareTo(this.saldo) > 0) {
            BigDecimal valorDoEspecial = valor.subtract(this.saldo).setScale(2, RoundingMode.HALF_UP);
            this.saldoEspecialUsado = this.saldoEspecialUsado.add(valorDoEspecial).setScale(2, RoundingMode.HALF_UP);
            this.saldo = BigDecimal.ZERO;
            System.out.println("    !Saque efetuado com Saldo Especial!" +
                    "\n  Saldo em conta: R$: " + this.saldo +
                    "\n  Divida no cheque especial agora é de: R$ " + this.saldoEspecialUsado);
            return;
        }
    }

    public void pagarUmBoleto(BigDecimal valorBoleto) {
        // validação val boleto
        if (valorBoleto.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("    Valor do boleto Inválido!");
            return;
        }
        BigDecimal chequeEspecialDisponivel = this.limiteChequeEspecial.subtract(saldoEspecialUsado).setScale(2, RoundingMode.HALF_UP);
        if (valorBoleto.compareTo(this.saldo.add(chequeEspecialDisponivel).setScale(2, RoundingMode.HALF_UP)) > 0) {
            System.out.println("    !Saldo insuficiente!\n   Saldo em conta: R$ " + this.saldo +
                    "\n   Valor cheque especial: R$ " + limiteChequeEspecial);
            return;
        }
        if (valorBoleto.compareTo(this.saldo) <= 0) {
            this.saldo = this.saldo.subtract(valorBoleto).setScale(2, RoundingMode.HALF_UP);
            System.out.println("    Boleto pago com sucesso!\n  Saldo em conta: R$ " + this.saldo);
            return;
        }
        if (valorBoleto.compareTo(this.saldo) > 0) {
            BigDecimal valorDoEspecial = valorBoleto.subtract(this.saldo).setScale(2, RoundingMode.HALF_UP);
            this.saldoEspecialUsado = this.saldoEspecialUsado.add(valorDoEspecial).setScale(2, RoundingMode.HALF_UP);
            this.saldo = BigDecimal.ZERO;
            System.out.println("    !Boleto pago com Saldo Especial!" +
                    "\n  Saldo em conta: R$: " + this.saldo +
                    "\n  Divida no cheque especial agora é de: R$ " + this.saldoEspecialUsado);
            return;
        }
    }

    public void verificarSttsChequeEspecial() {
        if (saldoEspecialUsado.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println(" --- !Sem dividas no cheque especial! --- ");
            System.out.println("Valor disponível para uso no cheque especial: R$ " + this.limiteChequeEspecial.subtract(this.saldoEspecialUsado).setScale(2, RoundingMode.HALF_UP));
            return;
        }
        System.out.println(" Valor disponível para uso no cheque especial: R$ " + this.limiteChequeEspecial.subtract(this.saldoEspecialUsado).setScale(2, RoundingMode.HALF_UP));
    }
}
