public abstract class Pessoa{
    private String nome;
    private String cpf;
    private String email;
    private String celular;

    public Pessoa(String n, String cpf, String e, String c){
        this.nome = n;
        this.cpf = cpf;
        this.email = e;
        this.celular = c;
    }

    public void exibir(){
        System.out.printf("Nome: %s\n", this.nome);
        System.out.printf("CPF: %s\n", this.cpf);
        System.out.printf("E-mail: %s\n", this.email);
        System.out.printf("Celular: %s\n", this.celular);
    }
}