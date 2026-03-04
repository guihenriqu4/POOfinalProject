package src.br.com.hotel.model.Pessoa;

// Administrador herda de Funcionário (que herda de Pessoa)
public class Administrador extends Funcionario {
    private final int nivelAcesso;

    // Construtor do Admin
    public Administrador(String n, String cpf, String e, String c, double s, String setor, String senha){
        // Chama o construtor de Funcionario
        super(n, cpf, e, c, s, setor, senha);
        this.nivelAcesso = 1; // Força o nível de acesso para 1 (máximo)
    }

    // Getter específico
    public int getNivelAcesso() { return nivelAcesso; }
    
    // Adiciona o nível na exibição
    public void exibir(){
        super.exibir();
        System.out.println("Nível de Acesso: " + this.nivelAcesso);
    }
}