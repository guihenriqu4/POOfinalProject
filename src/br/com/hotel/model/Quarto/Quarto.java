package src.br.com.hotel.model.Quarto;
import java.io.Serial;
import java.io.Serializable;

public abstract class Quarto implements Serializable {
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

    public void exibir(){};

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " (R$ " + this.getValorBase() + " /dia)";
    }
}
