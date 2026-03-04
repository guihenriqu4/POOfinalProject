package src.br.com.hotel.services;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import src.br.com.hotel.exceptions.CalculoTotalException;
import src.br.com.hotel.exceptions.FinalizacaoException;
import src.br.com.hotel.exceptions.ReservaInexistenteException;
import src.br.com.hotel.model.Pessoa.Administrador;
import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.model.Pessoa.Hospede;
import src.br.com.hotel.model.Quarto.Quarto;

// Gerenciador central do Banco de Dados em Memória
public class Hotel implements Serializable{
    private String nome;
    private Administrador adm;
    
    // Listas globais do sistema
    private List<Hospede> hospedes; // AGREGAÇÃO
    private List<Funcionario> funcionarios; // AGREGAÇÃO
    private List<Quarto> quartos; // COMPOSIÇÃO
    private List<Reserva> reservasAtivas;

    // Nome do arquivo de persistência
    private static final String ARQUIVO_DADOS = "dados_do_hotel.dat";

    public Hotel(String nome, Administrador adm){
        this.nome = nome;
        this.adm = adm;
        this.hospedes = new ArrayList<>();
        this.funcionarios = new ArrayList<>();
        this.quartos = new ArrayList<>();
        this.reservasAtivas = new ArrayList<>();
    }

    // Adiciona instâncias nas listas
    public void addHospede(Hospede h){ this.hospedes.add(h); }
    public void addFuncionario(Funcionario f){ this.funcionarios.add(f); }
    public void addQuarto(Quarto q){ this.quartos.add(q); }

    // Cria reserva e injeta na lista
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

            r.finalizar(); // Libera o quarto
            this.reservasAtivas.remove(r); // Tira a reserva da memória

        } catch (FinalizacaoException | CalculoTotalException e){
            System.out.println(e.getMessage());
        }
    }

    // PERSISTÊNCIA: Transforma as listas em fluxo de bytes e salva no arquivo local
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

    // PERSISTÊNCIA: Lê o fluxo de bytes do arquivo e popula as listas ao abrir o app
    @SuppressWarnings("unchecked")
    public void carregarDados(){
        File arquivo = new File(ARQUIVO_DADOS);
        if (!arquivo.exists()) {
            return; // Se não houver dados, simplesmente ignora e segue a vida
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

    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public List<Hospede> getHospedes() { return hospedes; }
    public Administrador getAdm() { return adm; }
    public void setAdm(Administrador adm) { this.adm = adm; }
    public List<Funcionario> getFuncionarios() { return funcionarios; }
    public List<Quarto> getQuartos() { return quartos; }
    public List<Reserva> getReservasAtivas() { return reservasAtivas; }
}