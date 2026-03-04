package src.br.com.hotel.exceptions;

// Cria uma classe de exceção personalizada herdando do Exception padrão do Java
public class NivelAcessoException extends Exception {
    // Construtor que recebe a mensagem de erro e passa para a superclasse
    public NivelAcessoException(String m){
        super(m);
    }
}