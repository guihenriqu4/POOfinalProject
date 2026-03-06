package src.br.com.hotel.Interface;

import src.br.com.hotel.model.Pessoa.Hospede;
import src.br.com.hotel.services.Hotel;
import src.br.com.hotel.services.Reserva;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

// Tela de self-service exclusiva para clientes logados
public class TelaMinhasReservas {

    public static JPanel criarPainel(Hotel hotel, Hospede hospedeLogado) {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Minhas Reservas Ativas", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(titulo, BorderLayout.NORTH);

        JTextArea txtReservas = new JTextArea();
        txtReservas.setEditable(false);
        txtReservas.setFont(new Font("Monospaced", Font.PLAIN, 14));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringBuilder relatorio = new StringBuilder();

        // Filtra a lista do hotel para mostrar apenas as que batem com o usuário logado
        boolean temReserva = false;
        for (Reserva r : hotel.getReservasAtivas()) {
            if (r.getHospede().equals(hospedeLogado)) {
                temReserva = true;
                relatorio.append("=====================================\n");
                relatorio.append("Quarto: ").append(r.getQuarto().getDescricao()).append("\n");
                relatorio.append("Data de Check-in: ").append(r.getCheckIn().format(formatter)).append("\n");
                relatorio.append("Data de Check-out: ").append(r.getCheckOut().format(formatter)).append("\n");
                relatorio.append("Horário (Entrada-Saída): ").append(r.getHorario()).append("\n");
                relatorio.append("Responsável (Funcionário): ").append(r.getResponsavel().getNome()).append("\n");
                relatorio.append("Valor Total da Hospedagem: ").append(r.getValorFormatado()).append("\n");
                relatorio.append("=====================================\n\n");
            }
        }

        if (!temReserva) {
            relatorio.append("Você não possui reservas ativas no momento.");
        }

        txtReservas.setText(relatorio.toString());
        painel.add(new JScrollPane(txtReservas), BorderLayout.CENTER);

        // Botão apenas informativo
        JButton btnAjuda = new JButton("Solicitar ajuda de um funcionário para editar reserva");
        btnAjuda.addActionListener(e -> {
            JOptionPane.showMessageDialog(painel,
                    "Ligue para nossa recepção: (34) 99715-1124",
                    "Entre em contato conosco!", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotao.add(btnAjuda);
        painel.add(painelBotao, BorderLayout.SOUTH);

        return painel;
    }
}