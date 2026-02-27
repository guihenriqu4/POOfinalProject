package src.br.com.hotel.model.Pessoa;
public class Administrador extends Funcionario {
    private final int nivelAcesso;

    public Administrador(String n, String cpf, String e, String c, double s, String setor, String senha){
        super(n, cpf, e, c, s, setor, senha);
        this.nivelAcesso = 1;
    }

    public int getNivelAcesso() {
        return nivelAcesso;
    }
    
    public void exibir(){
        super.exibir();
        System.out.println("Nível de Acesso: " + this.nivelAcesso);
    }
}
