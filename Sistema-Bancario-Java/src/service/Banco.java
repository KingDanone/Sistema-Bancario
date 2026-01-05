package abstracao.banco;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Banco {
    private BigDecimal saldo;
    private BigDecimal limiteChequeEspecial;
    private BigDecimal saldoEspecialUsado;
    private final BigDecimal LIMITE_BASE = new BigDecimal("500,00");
    private final BigDecimal CHEQUE_ESPECIAL_MINIMO = new BigDecimal("50,00");
    private final BigDecimal PERCENTUAL_CHEQUE = new BigDecimal("0,50");


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
        System.out.println("\n--- Saldo em conta: " + saldo + " ---\n");
    }

    public void consultarChequeEspecial() {
        System.out.println("In Involvement");
    }

    public void depositarDinheiro(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            System.out.println("\nERROR: Invalid value\n");
            return;
        }
        this.saldo = this.saldo.add(valor);
        System.out.println("You addiction R$" + valor + "\n");
    }

    public void sacarDinheiro(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0){
            System.out.println("\nERROR: Invalid value\n");
            return;
        }
        if (this.saldo.compareTo(valor) >= 0) {
            this.saldo = this.saldo.subtract(valor);
            System.out.println("Amount of R$" + valor + " successfully withdrawn!\n");
        } else {
            System.out.println("Insufficient balance\n");
        }
    }

    public void pagarUmBoleto() {
        System.out.println("In Involvement");
    }

    public void verificarSttsChequeEspecial() {
        System.out.println("In Involvement");
    }
}
