package src.br.com.hotel.model.Pessoa;
import java.io.Serializable;

// Classe abstrata (molde) que permite salvar em arquivo (Serializable)
public abstract class Pessoa implements Serializable{
    // Atributos base privados para encapsulamento
    private String nome;
    private String cpf;
    private String email;
    private long celular;
    
    // Construtor que força a inserção dos dados obrigatórios
    public Pessoa(String n, String cpf, String e, long c){
        this.nome = n;
        this.cpf = cpf;
        this.email = e;
        this.celular = c;
    }
    
    // Getters e Setters para permitir leitura e alteração segura dos dados
    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public String getEmail() { return email; }
    public long getCelular() { return celular; }
}