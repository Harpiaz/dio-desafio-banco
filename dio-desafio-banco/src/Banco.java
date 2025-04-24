import java.util.Map;
import java.text.DecimalFormat;
import java.util.HashMap;

public class Banco {
    private String nome;
    private int codigo;
    private Map<Integer, Agencia> agencias;
    private Map<String, Cliente> clientes;
    private Map<String, Conta> contas;

    public Banco(String nome, int codigo) {
        this.nome = nome;
        this.codigo = codigo;
        this.agencias = new HashMap<>();
        this.clientes = new HashMap<>();
        this.contas = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCodigo() {
        DecimalFormat df = new DecimalFormat("0000");
        return String.format("%s", df.format(this.codigo));
    }

    public Map<Integer, Agencia> getAgencias() {
        return agencias;
    }

    public Map<String, Cliente> getClientes() {
        return clientes;
    }

    public Map<String, Conta> getContas() {
        return contas;
    }
    
    public String toString() {
        return String.format("Banco %s (%s)", this.nome, this.getCodigo());
    }

    public Agencia adicionarAgencia(String nome, int numero) {
        Agencia agencia = new Agencia(this, nome, numero);
        this.agencias.put(numero, agencia);
        return agencia;
    }

    public void adicionarCliente(String nome, String cpf) {
        Cliente cliente = new Cliente(nome, cpf);
        this.clientes.put(cpf, cliente);
    }

    public void adicionarConta(Conta conta) {
        this.contas.put(conta.getNumero(), conta);
    }
}
