package src.br.com.hotel.model.Pessoa;
import java.io.Serializable;

public abstract class Pessoa implements Serializable{
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
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) { this.email = email;}

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }
 
    public void exibir(){
        System.out.printf("Nome: %s\n", this.nome);
        System.out.printf("CPF: %s\n", this.cpf);
        System.out.printf("E-mail: %s\n", this.email);
        System.out.printf("Celular: %s\n", this.celular);
    }
}