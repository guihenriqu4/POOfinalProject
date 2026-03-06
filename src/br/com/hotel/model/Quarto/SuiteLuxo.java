package src.br.com.hotel.model.Quarto;

public class SuiteLuxo extends Quarto{
    public SuiteLuxo(double valorBase){
        super(valorBase, "Suíte de luxo dotada de cama Queen Size e roupa de cama, jacuzzi, televisão, cadeira de massagem e dois banheiros equipados com toalhas");
    }

    // Aplica a lógica com 20% de acréscimo
    @Override
    public double calcularValor(int dias) {
        return (getValorBase() * dias) * 1.2; 
    }
}