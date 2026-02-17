public class Funcionario extends Pessoa {
    private double salario;
    private String setor;

    public Funcionario(String n, String cpf, String e, String c, double s, String setor){
        super(n, cpf, e, c);
        this.salario = s;
        this.setor = setor;
    }

    public boolean autenticar(String senha){
        return senha == "admin123" ? true:false;
    }

    public void exibir(){
        super.exibir();
        System.out.printf("Salário: %.2f\n", this.salario);
        System.out.printf("Setor: %s\n", this.setor);
    }
}
