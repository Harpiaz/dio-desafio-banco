import java.util.Map;
import java.text.DecimalFormat;
import java.util.HashMap;

public class Agencia {
    private Banco banco;
    private String nome;
    private Integer numero;
    private Map<String, Cliente> clientes;
    private Map<String, Conta> contas;

    public Agencia(Banco banco, String nome, Integer numero) {
        this.banco = banco;
        this.nome = nome;
        this.numero = numero;
        this.clientes = new HashMap<>();
        this.contas = new HashMap<>();
    }

    public Banco getBanco() {
        return banco;
    }

    public String getNome() {
        return nome;
    }

    public String getNumero() {
        DecimalFormat df = new DecimalFormat("0000");
        return String.format("%s", df.format(this.numero));
    }

    public Map<String, Cliente> getClientes() {
        return clientes;
    }

    public Map<String, Conta> getContas() {
        return contas;
    }

    public String toString() {
        return String.format("%s - Agência %s", this.getNumero(), this.nome);
    }

    public Cliente cadastrarCliente(String nome, String cpf) {
        Cliente cliente = new Cliente(nome, cpf);
        this.clientes.put(cpf, cliente);
        this.banco.adicionarCliente(nome, cpf);
        return cliente;
    }

    public Conta abrirConta(Cliente cliente, char tipo) throws IllegalArgumentException{
        if (tipo != 'C' && tipo != 'P') {
            throw new IllegalArgumentException("Tipo de conta inválido. Use 'C' para Conta Corrente ou 'P' para Conta Poupança.");
        }

        Conta conta = tipo == 'C' ? this.abrirContaCorrente(cliente) : this.abrirContaPoupanca(cliente);
        this.banco.adicionarConta(conta);;
        this.contas.put(conta.getNumero(), conta);
        cliente.adicionarConta(conta);
        return conta;
    }

    private Conta abrirContaCorrente(Cliente cliente) {
        return new ContaCorrente(this, cliente);
        
    }

    private Conta abrirContaPoupanca(Cliente cliente) {
        return new ContaPoupanca(this, cliente);
    }
}
