import java.util.List;
import java.text.DecimalFormat;
import java.util.ArrayList;

public abstract class Conta {
    protected Banco banco;
    protected Agencia agencia;
    protected Integer numero;
    protected int digito;
    protected Cliente titular;
    protected double saldo;
    protected List<Transacao> transacoes;
    
    public Conta(Agencia agencia, Cliente titular) {
        this.banco = agencia.getBanco();
        this.agencia = agencia;
        this.titular = titular;
        this.saldo = 0.0;
        this.transacoes = new ArrayList<>();

        // Criar-se-á um número aleatório e então se verificará se já existe uma conta com esse número na agência.
        // Se já existir, o número será gerado novamente até que um número único seja encontrado.
        while (true) {
            this.numero = (int) (Math.random() * 100000);
            if (!agencia.getContas().keySet().contains(this.getNumero()))
                break;
        }

        // O dígito é a sobra da divisão da soma dos dígitos do número da conta por 10.
        this.digito = this.getNumero().chars()
                .map(Character::getNumericValue)
                .sum() % 10;
    }

    public Banco getBanco() {
        return banco;
    }

    public Agencia getAgencia() {
        return agencia;
    }
    
    public String getNumero() {
        DecimalFormat df = new DecimalFormat("00000");
        return String.format("%s", df.format(this.numero));
    }

    public int getDigito() {
        return digito;
    }

    public Cliente getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public abstract String toString();

    public void depositar(double valor, boolean transferencia) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de depósito deve ser positivo.");
        }

        this.saldo += valor;
        if (transferencia) {
            Transacao transacao = new Transacao(valor, "Cr transf", this.saldo);
            this.transacoes.add(transacao);
        } else {
            Transacao transacao = new Transacao(valor, "Deposito", this.saldo);
            this.transacoes.add(transacao);
        }
    }

    public void sacar(double valor, boolean transferencia) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor de saque deve ser positivo.");
        }
        if (valor > this.saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para saque.");
        }

        this.saldo -= valor;
        if (transferencia) {
            Transacao transacao = new Transacao(valor, "Db transf", this.saldo);
            this.transacoes.add(transacao);
        } else {
            Transacao transacao = new Transacao(valor, "Saque", this.saldo);
            this.transacoes.add(transacao);
        }
    }

    public void imprimirSaldo() {
        System.out.println("--------------------------------------------------------");
        System.out.println("AGENCIA: " + this.agencia.getNumero());
        System.out.println("CONTA: " + this.getNumero() + "-" + this.getDigito());
        System.out.println("TITULAR: " + this.titular.getNome());
        System.out.println();
        // O texto tem 30 caracteres, enquanto o número máximo de caracteres que pode ser exibido é 56.
        // Sendo assim, o saldo é impresso com 13 espaços à direita, totalizando 43 caracteres.
        System.out.println(String.format("%43s", "SALDO PARA SIMPLES CONFERENCIA"));
        System.out.println();
        System.out.println(String.format("R$%.2f", this.getSaldo()));
        System.out.println("--------------------------------------------------------");
    }
    
    public void imprimirExtrato() {
        this.imprimirSaldo();
        // Nesse caso, o texto tem 16 caracteres; assim, o saldo é impresso com 20 espaços à direita.
        System.out.println(String.format("%36s", "EXTRATO DE CONTA"));
        System.out.println();
        System.out.println(String.format("%-10s  %-14s %-14s %14s", "Data", "Valor", "Desc.", "Saldo"));
        this.transacoes.forEach(System.out::println);
        System.out.println("--------------------------------------------------------");
    }
}
