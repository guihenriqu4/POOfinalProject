package src.br.com.hotel.exceptions;

public class CamposInvalidosException extends Exception {

    // Construtor que recebe a mensagem personalizada de erro
    public CamposInvalidosException(String mensagem) {
        super(mensagem);
    }
}