public class ChaleFamilia extends Quarto{
    private int qtdCamas;

    public ChaleFamilia(double valorBase, String descricao, int qtd){
        super(valorBase, descricao);
        this.qtdCamas = qtd;
    }

    public double calcularValor(int dias){
        double diarias = super.getValor() * dias;
        double taxas = diarias * 0.03 + this.qtdCamas * 5;
        return diarias + taxas;
    }
}
