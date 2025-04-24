import java.time.LocalDate;

public class Transacao {
    private LocalDate data;
    private double valor;
    private String descricao;
    private double saldoResultante;

    public Transacao(double valor, String descricao, double saldoResultante) {
        this.data = LocalDate.now();
        this.valor = valor;
        this.descricao = descricao;
        this.saldoResultante = saldoResultante;
    }
    
    public LocalDate getData() {
        return data;
    }
    public double getValor() {
        return valor;
    }
    public String getDescricao() {
        return descricao;
    }
    public double getSaldoResultante() {
        return saldoResultante;
    }

    public String toString() {
        String valorString = String.format("%.2f", this.valor);
        String saldoResultanteString = String.format("%.2f", this.saldoResultante);
        return String.format("%s  %-14s %-14s %14s", this.data, valorString, this.descricao.toUpperCase(), saldoResultanteString);
    }
}