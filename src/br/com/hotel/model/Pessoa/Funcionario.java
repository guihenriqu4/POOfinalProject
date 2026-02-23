package src.br.com.hotel.model.Pessoa;
public class Funcionario extends Pessoa {
    private double salario;
    private String setor;
    private String senha;

    public Funcionario(String n, String cpf, String e, String c, double s, String setor, String senha){
        super(n, cpf, e, c);
        this.salario = s;
        this.setor = setor;
        this.senha = senha;
    }

    public boolean autenticar(String senhaDigitada){
        return this.senha.equals(senhaDigitada);
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
