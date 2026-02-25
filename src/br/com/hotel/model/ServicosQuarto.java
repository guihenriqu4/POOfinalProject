package src.br.com.hotel.model;

import java.time.LocalDate;

public class ServicosQuarto {
    private String descricao;
    private double valor;
    private LocalDate dataSolicitacao;

    public ServicosQuarto(String d, double v){
        this.descricao = d;
        this.valor = v;
        this.dataSolicitacao = LocalDate.now();
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public void exibir(){
        System.out.println("Descrição: " + this.descricao);
        System.out.println("Valor: " + this.valor);
        System.out.println("Data de Solicitação: " + this.dataSolicitacao);
    }
}
