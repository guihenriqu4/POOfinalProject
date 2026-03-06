package src.br.com.hotel.model.Quarto;

public class ChaleFamilia extends Quarto{
    private int camasSolteiro;
    private int camasCasal;

    public ChaleFamilia(double valorBase, int capacidadeOriginal){
        super(valorBase, "Chale confortável para a família dotado de duas televisões, cozinha com fogão para preparo de alimentações, roupa de cama e dois banheiros equipados com toalhas");
        this.camasSolteiro = capacidadeOriginal;
        this.camasCasal = 0;
    }

    // Método acessado pela interface para definir camas extras
    public void setCamas(int solteiro, int casal) {
        this.camasSolteiro = solteiro;
        this.camasCasal = casal;
    }

    public int getCamasSolteiro() { return camasSolteiro; }
    public int getCamasCasal() { return camasCasal; }

    // Aplica a lógica de taxas por camas extras
    @Override
    public double calcularValor(int dias) {
        int totalCamasEquivalente = camasSolteiro + (camasCasal * 2);
        return (getValorBase() * dias) + (20.00 * totalCamasEquivalente);
    }
}