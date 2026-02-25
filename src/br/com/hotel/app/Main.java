package src.br.com.hotel.app;

import src.br.com.hotel.model.Pessoa.Administrador;
import src.br.com.hotel.services.Hotel;

public class Main {
    public static void main(String[] args) {
        Administrador adm = new Administrador("Anacleto Cebolão", "111.222.333-44", "anacletocebolao@gmail.com", "34987651234", 2512.00, "Administrativo", "admin123", 1);
        Hotel hotel = new Hotel("Hotel Strada", adm);

        
    }
}
