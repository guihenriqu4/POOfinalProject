public class SuiteLuxo extends Quarto{
    public SuiteLuxo(double valorBase, String descricao){
        super(valorBase, descricao);
    }

    public double calcularValor(int dias){
        double diarias = super.getValor() * dias;
        double taxas = diarias * 0.2;
        return diarias + taxas;
    }
}
