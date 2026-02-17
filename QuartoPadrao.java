public class QuartoPadrao extends Quarto{
    public QuartoPadrao(double valorBase, String descricao){
        super(valorBase, descricao);
    }

    public double calcularValor(int dias){
        double diarias = super.getValor() * dias;
        double taxas = diarias * 0.05;
        return diarias + taxas;
    }
}
