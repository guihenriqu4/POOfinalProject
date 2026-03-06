package src.br.com.hotel.services;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import src.br.com.hotel.exceptions.CalculoTotalException;
import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.model.Pessoa.Hospede;
import src.br.com.hotel.model.Quarto.Quarto;

// Classe central que interliga atores e itens
public class Reserva implements Serializable {
    // Relacionamentos com os atores
    private Hospede hospede; // AGREGAÇÃO
    private Funcionario responsavel; // AGREGAÇÃO
    private Quarto quarto; // ASSOCIAÇÃO

    // Dados de temporalidade
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String horario;

    public Reserva(Hospede h, Funcionario r, Quarto q, LocalDate in, LocalDate out, String horario){
        this.hospede = h;
        this.responsavel = r;
        this.quarto = q;
        this.checkIn = in;
        this.checkOut = out;
        this.horario = horario;

        this.quarto.ocuparQuarto(); // Ocupa o quarto fisicamente ao reservar
    }

    // Calcula o valor total invocando o polimorfismo do quarto
    public double calcularTotal() throws CalculoTotalException {
        int dias = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        if (dias <= 0) dias = 1; // Seta 1 diária mínima padrão

        // O quarto decide sozinho quanto custa baseado no seu tipo
        double total = quarto.calcularValor(dias);

        if(total <= 0 ) throw new CalculoTotalException("Erro ao calcular valor total de pagamento!");

        return total;
    }

        // Getters
        public Hospede getHospede() { return hospede; }
        public Funcionario getResponsavel() { return responsavel; }
        public Quarto getQuarto() { return quarto; }
        public void setQuarto(Quarto quarto) { this.quarto = quarto; }
        public LocalDate getCheckIn() { return checkIn; }
        public LocalDate getCheckOut() { return checkOut; }
        public String getHorario() { return horario; }
        public String getValorFormatado() {
        try {
            return String.format("R$ %.2f", calcularTotal());
            }  catch (CalculoTotalException e) {
            return "Erro no cálculo";
            }
        }
}
