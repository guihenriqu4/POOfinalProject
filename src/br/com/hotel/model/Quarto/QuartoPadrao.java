package src.br.com.hotel.model.Quarto;

public class QuartoPadrao extends Quarto{
    public QuartoPadrao(double valorBase){
        // Envia o valor e a descrição fixa para a classe pai
        super(valorBase, "Quarto básico com uma cama de casal, uma televisão, guarda-roupa e roupa de cama, bem como banheiro equipado com toalhas.");
    }

    // Aplica a lógica básica de cobrança sem taxas extras
    @Override
    public double calcularValor(int dias) {
        return getValorBase() * dias; 
    }
}