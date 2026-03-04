package src.br.com.hotel.model.Quarto;
import java.io.Serializable;

// Molde abstrato para todos os quartos
public abstract class Quarto implements Serializable {
    // Variável estática que pertence à classe para gerar números automáticos
    private static int contadorId = 1;

    private int num;
    private boolean ocupado;
    private double valorBase;
    private String descricao;

    public Quarto(double v, String d){
        this.num = contadorId++; // Pega o número atual e depois soma 1 para o próximo
        this.ocupado = false; // Nasce desocupado
        this.valorBase = v;
        this.descricao = d;
    }

    // MÉTODO POLIMÓRFICO: Apenas a assinatura. As filhas implementam a lógica.
    public abstract double calcularValor(int dias);

    // Controles de estado do quarto
    public void liberarQuarto() { this.ocupado = false; }
    public void ocuparQuarto() { this.ocupado = true; }

    // Getters e Setters
    public int getNum() { return num; }
    public boolean isOcupado() { return ocupado; }
    public double getValorBase() { return valorBase; }
    public void setValorBase(double valorBase) { this.valorBase = valorBase; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public void exibir(){};

    // Formata o nome do objeto para aparecer bonito na interface gráfica
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (R$ " + this.getValorBase() + " /dia)";
    }
}