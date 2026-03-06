package src.br.com.hotel.model.Pessoa;

// Funcionário também herda de Pessoa
public class Funcionario extends Pessoa {
    // Atributos exclusivos do funcionário
    private double salario;
    private String setor;
    private String senha;

    // Construtor do Funcionário
    public Funcionario(String n, String cpf, String e, long c, double s, String setor, String senha){
        super(n, cpf, e, c); // Envia os dados básicos para a classe pai
        this.salario = s;
        this.setor = setor;
        this.senha = senha;
    }

    // Valida se a senha informada confere com a salva
    public boolean autenticar(String senhaDigitada){
        return this.senha.equals(senhaDigitada);
    }

    // Getters
    public double getSalario() { return salario; }
    public String getSetor() { return setor; }
    public String getSenha() { return senha; }
}