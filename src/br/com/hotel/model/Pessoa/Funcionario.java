package src.br.com.hotel.model.Pessoa;

// Funcionário também herda de Pessoa
public class Funcionario extends Pessoa {
    // Atributos exclusivos do funcionário
    private double salario;
    private String setor;
    private String senha;

    // Construtor do Funcionário
    public Funcionario(String n, String cpf, String e, String c, double s, String setor, String senha){
        super(n, cpf, e, c); // Envia os dados básicos para a classe pai
        this.salario = s;
        this.setor = setor;
        this.senha = senha;
    }

    // Valida se a senha informada confere com a salva
    public boolean autenticar(String senhaDigitada){
        return this.senha.equals(senhaDigitada);
    }

    // Getters e Setters
    public double getSalario() { return salario; }
    public void setSalario(double salario) { this.salario = salario; }
    public String getSetor() { return setor; }
    public void setSetor(String setor) { this.setor = setor; }
    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    // Adiciona salário e setor na exibição
    public void exibir(){}
}