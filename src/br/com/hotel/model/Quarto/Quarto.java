package src.br.com.hotel.model.Quarto;
import java.io.Serial;

public abstract class Quarto {
    private Serial num;
    private boolean ocupado;
    private double valorBase;
    private String descricao;

    public Quarto(double v, String d){
        this.ocupado = false;
        this.valorBase = v;
        this.descricao = d;
    }

    public abstract double calcularValor(int dias);

    public void liberarQuarto() {
        this.ocupado = false;
    }

    public void ocuparQuarto() {
        this.ocupado = true;
    }

    public Serial getNum() {
        return num;
    }

    public void setNum(Serial num) {
        this.num = num;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public double getValorBase() {
        return valorBase;
    }

    public void setValorBase(double valorBase) {
        this.valorBase = valorBase;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void exibir(){
        System.out.printf("Número: %d\n", this.num);
        System.out.printf("Disponibilidade: %s\n", this.ocupado ? "Ocupado" : "Livre");
        System.out.printf("Valor Base: %.2f\n", this.valorBase);
        System.out.printf("Descrição: %s\n", this.descricao);
    }
}
