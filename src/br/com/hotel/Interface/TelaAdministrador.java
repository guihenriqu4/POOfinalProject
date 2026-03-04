package src.br.com.hotel.Interface;

import src.br.com.hotel.services.Hotel;
import javax.swing.*;
import java.awt.*;

// Classe responsável por renderizar o painel exclusivo da gerência
public class TelaAdministrador {

    public static JPanel criarPainel(Hotel hotel) {
        JPanel painelAdmin = new JPanel(new BorderLayout());
        painelAdmin.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Painel de Controle Administrativo", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelAdmin.add(titulo, BorderLayout.NORTH);

        JPanel painelEstatisticas = new JPanel(new BorderLayout(10, 15));
        painelEstatisticas.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        JButton btnVerEstatisticas = new JButton("Atualizar Estatísticas do Hotel");

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotao.add(btnVerEstatisticas);

        JTextArea txtEstatisticas = new JTextArea("Clique no botão para carregar as estatísticas...");
        txtEstatisticas.setEditable(false);
        txtEstatisticas.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        txtEstatisticas.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Fonte monoespaçada para alinhar tabelas

        // Coleta dados das listas do hotel e exibe no formato texto
        btnVerEstatisticas.addActionListener(e -> {
            int totalHospedes = hotel.getHospedes().size();
            int totalFuncionarios = hotel.getFuncionarios().size();
            int totalReservas = hotel.getReservasAtivas().size();

            String relatorio = "--- RELATÓRIO DO HOTEL ---\n\n" +
                    "Total de Funcionários Cadastrados: " + totalFuncionarios + "\n" +
                    "Total de Hóspedes Cadastrados: " + totalHospedes + "\n" +
                    "Total de Reservas Ativas: " + totalReservas;

            txtEstatisticas.setText(relatorio);
        });

        painelEstatisticas.add(painelBotao, BorderLayout.NORTH);
        painelEstatisticas.add(new JScrollPane(txtEstatisticas), BorderLayout.CENTER);

        painelAdmin.add(painelEstatisticas, BorderLayout.CENTER);

        return painelAdmin;
    }
}