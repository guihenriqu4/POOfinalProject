package src.br.com.hotel.model.Quarto;

public class ChaleFamilia extends Quarto{
    private int camasSolteiro;
    private int camasCasal;

    public ChaleFamilia(double valorBase, int capacidadeOriginal){
        super(valorBase, "Chale confortável para a família dotado de duas televisões, cozinha com fogão para preparo de alimentações, roupa de cama e dois banheiros equipados com toalhas");
        this.camasSolteiro = capacidadeOriginal;
        this.camasCasal = 0;
    }

    public void setCamas(int solteiro, int casal) {
        this.camasSolteiro = solteiro;
        this.camasCasal = casal;
    }

    public int getCamasSolteiro() {
        return camasSolteiro;
    }

    public int getCamasCasal() {
        return camasCasal;
    }

    @Override
    public double calcularValor(int dias) {
        // Taxa fixa de R$ 50 por cama extra por dia
        int totalCamasEquivalente = camasSolteiro + (camasCasal * 2);
        return (getValorBase() * dias) + (20.00 * totalCamasEquivalente);
    }

    @Override
    public void exibir(){
        super.exibir();
        System.out.println("Camas -> Solteiro: " + this.camasSolteiro + " | Casal: " + this.camasCasal);
    }
}
