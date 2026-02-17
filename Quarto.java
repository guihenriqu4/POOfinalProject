import java.io.Serial;

public abstract class Quarto {
    private Serial num;
    private boolean ocupado = false;
    private final double valorBase;
    private String descricao;

    public Quarto(double v, String d){
        this.valorBase = v;
        this.descricao = d;
    }

    public abstract double calcularValor(int dias);

    public void reservar(){
        this.ocupado = true;
    }

    public void liberarQuarto(){
        this.ocupado = false;
    }

    public double getValor(){
        return this.valorBase;
    }

    public void exibir(){
        System.out.printf("Número: %d\n", this.num);
        System.out.printf("Disponibilidade: %s\n", this.ocupado ? "Ocupado" : "Livre");
        System.out.printf("Valor Base: %.2f\n", this.valorBase);
        System.out.printf("Descrição: %s\n", this.descricao);
    }
}
