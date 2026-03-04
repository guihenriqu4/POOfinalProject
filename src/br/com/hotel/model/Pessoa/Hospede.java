package src.br.com.hotel.model.Pessoa;

// Hóspede herda os atributos de Pessoa
public class Hospede extends Pessoa {
    // Atributo exclusivo do hóspede
    private String senhaHospede;

    // Construtor do Hóspede
    public Hospede(String n, String cpf, String e, String c, String num){
        // Chama o construtor da classe pai (Pessoa)
        super(n, cpf, e, c);
        this.senhaHospede = num;
    }

    // Getters e Setters
    public String getSenhaHospede() { return senhaHospede; }
    public void setSenhaHospede(String senhaHospede) { this.senhaHospede = senhaHospede; }

    // Sobrescreve o método exibir para adicionar a senha antes dos dados comuns
    public void exibir(){
        System.out.printf("Número: %s\n", this.senhaHospede);
        super.exibir(); // Chama o exibir() da classe Pessoa
    }
}