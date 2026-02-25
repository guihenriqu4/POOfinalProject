package src.br.com.hotel.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import src.br.com.hotel.model.ServicosQuarto;
import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.model.Pessoa.Hospede;
import src.br.com.hotel.model.Quarto.Quarto;

public class Reserva {
    private Hospede hospede;
    private Funcionario responsavel;
    private Quarto quarto;
    List<ServicosQuarto> servicos;

    private LocalDate checkIn;
    private LocalDate checkOut;
    private boolean statusPagamento;

    public Reserva(Hospede h, Funcionario r, Quarto q, LocalDate in, LocalDate out){
        this.hospede = h;
        this.responsavel = r;
        this.quarto = q;
        this.checkIn = in;
        this.checkOut = out;

        this.statusPagamento = false;
        this.servicos = new ArrayList<>();

        this.quarto.ocuparQuarto();
    }

    public void addServicos(ServicosQuarto s){
        this.servicos.add(s);
    }

    public double calcularTotal(){
        int dias = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        if (dias <= 0) dias = 1; // Seta 1 diária como padrão

        double total = quarto.calcularValor(dias);

        double totalServicos = 0;
        for (ServicosQuarto s : servicos) {
            totalServicos += s.getValor();
        }

        return total + totalServicos;
    }

    public void finalizar(){
        this.statusPagamento = true;
        this.quarto.liberarQuarto();
    }

    public Hospede getHospede() {
        return hospede;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public Funcionario getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Funcionario responsavel) {
        this.responsavel = responsavel;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public List<ServicosQuarto> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicosQuarto> servicos) {
        this.servicos = servicos;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public boolean isStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(boolean statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    
    
}
