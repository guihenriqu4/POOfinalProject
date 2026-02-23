package src.br.com.hotel.services;
import java.util.ArrayList;
import java.util.List;

import src.br.com.hotel.model.Quarto.ChaleFamilia;
import src.br.com.hotel.model.Quarto.Quarto;
import src.br.com.hotel.model.Quarto.QuartoPadrao;
import src.br.com.hotel.model.Quarto.SuiteLuxo;

public class Hotel {
    private String nome;
    List<Quarto> quartos;

    public Hotel(String nome){
        this.nome = nome;

        this.quartos = new ArrayList<Quarto>();

        this.quartos.add(new QuartoPadrao(60, "Quarto básico com uma cama de casal, uma televisão, guarda-roupa e roupa de cama, bem como banheiro equipado com toalhas."));
        this.quartos.add(new SuiteLuxo(250, "Suíte de luxo dotada de cama Queen Size e roupa de cama, jacuzzi, televisão, cadeira de massagem e dois banheiros equipados com toalhas"));
        this.quartos.add(new ChaleFamilia(120, "Chale confortável para a família dotado de duas televisões, cozinha com fogão para preparo de alimentações, roupa de cama e dois banheiros equipados com toalhas", 3));
    }

    public void exibirDados(){
        System.out.println("Nome hotel: " + this.nome);
        System.out.println("-".repeat(50));
        System.out.println("Informações dos quartos: ");
        System.out.println("");
        for(int i = 0; i < this.quartos.size(); i++){
            quartos.get(i).exibir();
        }
    }

    
}
