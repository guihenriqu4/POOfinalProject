package src.br.com.hotel.services;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.br.com.hotel.exceptions.CalculoTotalException;
import src.br.com.hotel.exceptions.FinalizacaoException;
import src.br.com.hotel.exceptions.NivelAcessoException;
import src.br.com.hotel.exceptions.ReservaInexistenteException;
import src.br.com.hotel.model.Pessoa.Administrador;
import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.model.Pessoa.Hospede;
import src.br.com.hotel.model.Quarto.Quarto;
import src.br.com.hotel.services.Reserva;

public class Hotel implements Serializable{
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

    private static final String ARQUIVO_DADOS = "dados_do_hotel.dat";

    public void addHospede(Hospede h){
        this.hospedes.add(h);
    }

    public void addFuncionario(Funcionario f){
        this.funcionarios.add(f);
    }

    public void addQuarto(Quarto q){
        this.quartos.add(q);
    }

    public Reserva realizarReserva(Hospede h, Funcionario r, Quarto q, LocalDate in, LocalDate out, String horario){
        Reserva novaReserva = new Reserva(h, r, q, in, out, horario);
        this.reservasAtivas.add(novaReserva);
        return novaReserva;
    }

    public void realizarCheckIn(Hospede h){
        try{
            Reserva ativar = null;
            for(Reserva r : reservasAtivas){
                if(h == r.getHospede()){
                    ativar = r;
                }
            }
            if(ativar == null){
                throw new ReservaInexistenteException("O hospéde informado não possui reserva ativa.");
            }

        } catch(ReservaInexistenteException e){
            System.out.println(e.getMessage());
        }

        
    }

    public void realizarCheckOut(Reserva r){
        try{
            System.out.println(r.calcularTotal());
            r.setStatusPagamento(true);


            r.finalizar();
            this.reservasAtivas.remove(r);

        } catch (FinalizacaoException e){
            System.out.println(e.getMessage());

        } catch (CalculoTotalException e){
            System.out.println(e.getMessage());
        }
    }

    public void salvarDados(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(this.hospedes);
            oos.writeObject(this.funcionarios);
            oos.writeObject(this.quartos);
            oos.writeObject(this.reservasAtivas);
            System.out.println("Dados salvos com sucesso no HD!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    public void carregarDados(){
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            return; // Se é a primeira vez rodando, o arquivo não existe, então ignora
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS))) {
            this.hospedes = (List<Hospede>) ois.readObject();
            this.funcionarios = (List<Funcionario>) ois.readObject();
            this.quartos = (List<Quarto>) ois.readObject();
            this.reservasAtivas = (List<Reserva>) ois.readObject();
            System.out.println("Dados carregados com sucesso do HD!");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }
    }

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
