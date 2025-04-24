import java.util.Map;
import java.util.HashMap;

public class Cliente {
    private String nome;
    private String cpf;
    private Map<String, Conta> contas;

    public Cliente(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
        this.contas = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Map<String, Conta> getContas() {
        return contas;
    }

    public String toString() {
        return String.format("%s - %s", this.nome, this.cpf);
    }

    public void adicionarConta(Conta conta) {
        this.contas.put(conta.getNumero(), conta);
    }
}