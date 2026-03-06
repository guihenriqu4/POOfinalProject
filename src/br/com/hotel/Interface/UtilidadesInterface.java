package src.br.com.hotel.Interface;

import javax.swing.*;

// Classe criada para seguir o princípio "DRY" (Don't Repeat Yourself) de POO
public class UtilidadesInterface {

    // Método que gera caixas de texto padronizadas para formulários e injeta direto no painel gráfico recebido
    public static JTextField[] BasePessoa(JPanel painelDestino) {
        JTextField[] campos = new JTextField[4];
        String[] labels = {"Nome Completo:", "CPF:", "E-mail:", "Celular (apenas  números):"};

        // Renderiza os campos dinamicamente e já os adiciona no painel
        for (int i = 0; i < 4; i++) {
            campos[i] = new JTextField();
            painelDestino.add(new JLabel(labels[i]));
            painelDestino.add(campos[i]);
        }
        return campos;
    }
}