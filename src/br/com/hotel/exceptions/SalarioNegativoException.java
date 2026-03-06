package src.br.com.hotel.exceptions;

// Exceção personalizada para impedir valores financeiros inconsistentes
public class SalarioNegativoException extends Exception {
    public SalarioNegativoException(String mensagem) {
        super(mensagem);
    }
}