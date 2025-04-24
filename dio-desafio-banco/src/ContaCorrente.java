public class ContaCorrente extends Conta {

    public ContaCorrente(Agencia agencia, Cliente titular) {
        super(agencia, titular);
    }

    public String toString() {
        return String.format("C/C %s %s %s-%d", this.banco.getCodigo(), this.agencia.getNumero(), 
        this.getNumero(), this.getDigito());
    }
}