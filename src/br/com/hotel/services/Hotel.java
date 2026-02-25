package src.br.com.hotel.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.br.com.hotel.model.Pessoa.Administrador;
import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.model.Pessoa.Hospede;
import src.br.com.hotel.model.Quarto.Quarto;

public class Hotel {
    private String nome;
    private List<Hospede> hospedes;
    private Administrador adm;
    private List<Funcionario> funcionarios;
    private List<Quarto> quartos;
    private List<Reserva> reservasAtivas;

    public Hotel(String nome, Administrador adm){
        this.nome = nome;
        this.adm = adm;
        this.hospedes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.quartos = new ArrayList<>();
        this.reservasAtivas = new ArrayList<>();
    }

    public void addHospede(Hospede h){
        this.hospedes.add(h);
    }

    public void addFuncionario(Funcionario f){
        this.funcionarios.add(f);
    }

    public void addQuarto(Quarto q){
        this.quartos.add(q);
    }

    public Reserva realizarCheckIn(Hospede h, Funcionario r, Quarto q, LocalDate out){
        Reserva novaReserva = new Reserva(h, r, q, LocalDate.now(), out);
        this.reservasAtivas.add(novaReserva);
        return novaReserva;
    }

    public void realizarCheckOut(Reserva r){
        r.finalizar();
        this.reservasAtivas.remove(r);
    }

    public void salvarDados(){}

    public void carregarDados(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Hospede> getHospedes() {
        return hospedes;
    }

    public void setHospedes(List<Hospede> hospedes) {
        this.hospedes = hospedes;
    }

    public Administrador getAdm() {
        return adm;
    }

    public void setAdm(Administrador adm) {
        this.adm = adm;
    }

    public List<Funcionario> getFuncionarios() {
        return funcionarios;
    }

    public void setFuncionarios(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<Quarto> getQuartos() {
        return quartos;
    }

    public void setQuartos(List<Quarto> quartos) {
        this.quartos = quartos;
    }

    public List<Reserva> getReservasAtivas() {
        return reservasAtivas;
    }

    public void setReservasAtivas(List<Reserva> reservasAtivas) {
        this.reservasAtivas = reservasAtivas;
    }
    
    
}
