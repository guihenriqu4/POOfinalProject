package src.br.com.hotel.model.Quarto;
public class ChaleFamilia extends Quarto{
    private int qtdCamas;

    public ChaleFamilia(double valorBase, int qtd){
        super(valorBase, "Chale confortável para a família dotado de duas televisões, cozinha com fogão para preparo de alimentações, roupa de cama e dois banheiros equipados com toalhas");
        this.qtdCamas = qtd;
    }

    @Override
    public double calcularValor(int dias) {
        // Taxa fixa de R$ 50 por cama extra por dia
        return (getValorBase() * dias) + (20.00 * qtdCamas); 
    }

    public void exibir(){
        super.exibir();
        System.out.println("Número de camas: " + this.qtdCamas);
    }
}
