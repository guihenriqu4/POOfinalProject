package src.br.com.hotel.model.Pessoa;
public class Hospede extends Pessoa {
    private String numFidelidade;

    public Hospede(String n, String cpf, String e, String c, String num){
        super(n, cpf, e, c);
        this.numFidelidade = num;
    }

    public String getNumFidelidade() {
        return numFidelidade;
    }

    public void setNumFidelidade(String numFidelidade) {
        this.numFidelidade = numFidelidade;
    }

}
