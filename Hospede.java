public class Hospede extends Pessoa {
    private String numFidelidade;

    public Hospede(String n, String cpf, String e, String c, String num){
        super(n, cpf, e, c);
        this.numFidelidade = num;
    }

    public void exibir(){
        super.exibir();
        System.out.printf("Número de Fidelidade: %s\n", this.numFidelidade);
    }
}
