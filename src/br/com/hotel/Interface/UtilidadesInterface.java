package src.br.com.hotel.Interface;

import javax.swing.*;

public class UtilidadesInterface {

    public static JTextField[] BasePessoa(JPanel painelDestino) {
        JTextField[] campos = new JTextField[4];
        String[] labels = {"Nome Completo:", "CPF:", "E-mail:", "Celular:"};

        for (int i = 0; i < 4; i++) {
            campos[i] = new JTextField();
            painelDestino.add(new JLabel(labels[i]));
            painelDestino.add(campos[i]);
        }
        return campos;
    }
}