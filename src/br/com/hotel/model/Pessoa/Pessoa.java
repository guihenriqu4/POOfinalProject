package src.br.com.hotel.model.Pessoa;
import java.io.Serializable;

// Classe abstrata (molde) que permite salvar em arquivo (Serializable)
public abstract class Pessoa implements Serializable{
    // Atributos base privados para encapsulamento
    private String nome;
    private String cpf;
    private String email;
    private String celular;
    
    // Construtor que força a inserção dos dados obrigatórios
    public Pessoa(String n, String cpf, String e, String c){
        this.nome = n;
        this.cpf = cpf;
        this.email = e;
        this.celular = c;
    }
    
    // Getters e Setters para permitir leitura e alteração segura dos dados
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email;}
    public String getCelular() { return celular; }
    public void setCelular(String celular) { this.celular = celular; }
 
    // Imprime os dados no terminal (útil para debug)
    public void exibir(){};
}