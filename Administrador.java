public class Administrador extends Funcionario {
    private int nivelAcesso;

    public Administrador(String n, String cpf, String e, String c, double s, String setor, int nivel){
        super(n, cpf, e, c, s, setor);
        this.nivelAcesso = nivel;
    }

    public void saveFunc(Funcionario f){}
    
    public void deleteFunc(Funcionario f){}
}
