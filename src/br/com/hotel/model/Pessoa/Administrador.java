package src.br.com.hotel.model.Pessoa;
public class Administrador extends Funcionario {
    private int nivelAcesso;

    public Administrador(String n, String cpf, String e, String c, double s, String setor, String senha, int nivel){
        super(n, cpf, e, c, s, setor, senha);
        this.nivelAcesso = nivel;
    }

    public int getNivelAcesso() {
        return nivelAcesso;
    }

    public void setNivelAcesso(int nivelAcesso) {
        this.nivelAcesso = nivelAcesso;
    }
    
}
