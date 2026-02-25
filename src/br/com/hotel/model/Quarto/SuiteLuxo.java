package src.br.com.hotel.model.Quarto;
public class SuiteLuxo extends Quarto{
    public SuiteLuxo(double valorBase){
        super(valorBase, "Suíte de luxo dotada de cama Queen Size e roupa de cama, jacuzzi, televisão, cadeira de massagem e dois banheiros equipados com toalhas");
    }

    @Override
    public double calcularValor(int dias) {
        return (getValorBase() * dias) * 1.20; // 20% de taxa de luxo
    }
}
