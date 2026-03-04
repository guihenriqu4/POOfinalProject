package src.br.com.hotel.model.Pessoa;
public class Hospede extends Pessoa {
    private String senhaHospede;

    public Hospede(String n, String cpf, String e, String c, String num){
        super(n, cpf, e, c);
        this.senhaHospede = num;
    }

    public String getSenhaHospede() {
        return senhaHospede;
    }

    public void setSenhaHospede(String senhaHospede) {
        this.senhaHospede = senhaHospede;
    }

    public void exibir(){
        System.out.printf("Número: %s\n", this.senhaHospede);
        super.exibir();
    }
}
