package src.br.com.hotel.model.Quarto;
public class QuartoPadrao extends Quarto{
    public QuartoPadrao(double valorBase){
        super(valorBase, "Quarto básico com uma cama de casal, uma televisão, guarda-roupa e roupa de cama, bem como banheiro equipado com toalhas.");
    }

    @Override
    public double calcularValor(int dias) {
        return getValorBase() * dias; // Sem taxas extras
    }
}
