package src.br.com.hotel.model.Pessoa;

// Administrador herda de Funcionário (que herda de Pessoa)
public class Administrador extends Funcionario {

    // Construtor do Admin
    public Administrador(String n, String cpf, String e, long c, double s, String setor, String senha){
        // Chama o construtor de Funcionario
        super(n, cpf, e, c, s, setor, senha);
    }
}