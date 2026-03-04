package src.br.com.hotel.exceptions;

// Cria uma classe de exceção personalizada herdando do Exception padrão do Java
public class CheckInException extends Exception {
    // Construtor que recebe a mensagem de erro e passa para a superclasse
    public CheckInException(String m){
        super(m);
    }
}