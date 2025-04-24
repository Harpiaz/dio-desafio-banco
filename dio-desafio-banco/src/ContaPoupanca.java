public class ContaPoupanca extends Conta {

    public ContaPoupanca(Agencia agencia, Cliente titular) {
        super(agencia, titular);
    }

    public String toString() {
        return String.format("C/P %s %s %s-%d", this.banco.getCodigo(), this.agencia.getNumero(), 
        this.getNumero(), this.getDigito());
    }
}