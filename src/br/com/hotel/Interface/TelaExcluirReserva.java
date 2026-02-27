package src.br.com.hotel.Interface;

import src.br.com.hotel.services.Hotel;
import src.br.com.hotel.services.Reserva;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class TelaExcluirReserva {

    public static JPanel criarPainel(Hotel hotel) {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Excluir/Cancelar Reserva", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(titulo, BorderLayout.NORTH);

        JPanel painelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelCentro.add(new JLabel("Selecione a reserva:"));

        JComboBox<String> comboReservas = new JComboBox<>();
        painelCentro.add(comboReservas);

        JButton btnAtualizar = new JButton("Atualizar Lista");
        painelCentro.add(btnAtualizar);

        painel.add(painelCentro, BorderLayout.CENTER);

        JButton btnExcluir = new JButton("Cancelar Reserva Selecionada");
        btnExcluir.setBackground(new Color(83, 0, 0)); // Cor vermelha para dar destaque
        btnExcluir.setForeground(Color.RED);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Função para preencher a caixinha com as reservas atuais
        Runnable atualizarCaixa = () -> {
            comboReservas.removeAllItems();
            for (Reserva r : hotel.getReservasAtivas()) {
                String item = "Hóspede: " + r.getHospede().getNome() +
                        " | Quarto: " + r.getQuarto().getClass().getSimpleName() +
                        " | Início: " + r.getCheckIn().format(dtf);
                comboReservas.addItem(item);
            }
        };

        // Preenche a primeira vez que a tela abre
        atualizarCaixa.run();

        // Se o usuário clicar em atualizar, roda a função de novo
        btnAtualizar.addActionListener(e -> atualizarCaixa.run());

        btnExcluir.addActionListener(e -> {
            String selecionado = (String) comboReservas.getSelectedItem();
            if (selecionado == null) {
                JOptionPane.showMessageDialog(painel, "Nenhuma reserva selecionada!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(painel,
                    "Tem certeza que deseja cancelar esta reserva?\nO quarto será liberado imediatamente.",
                    "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                // Procura a reserva que bate com o texto selecionado
                Reserva reservaParaRemover = null;
                for (Reserva r : hotel.getReservasAtivas()) {
                    String textoReserva = "Hóspede: " + r.getHospede().getNome() +
                            " | Quarto: " + r.getQuarto().getClass().getSimpleName() +
                            " | Início: " + r.getCheckIn().format(dtf);
                    if (textoReserva.equals(selecionado)) {
                        reservaParaRemover = r;
                        break;
                    }
                }

                if (reservaParaRemover != null) {
                    // Libera o quarto para poder ser alugado novamente
                    reservaParaRemover.getQuarto().liberarQuarto();
                    // Remove da lista do hotel
                    hotel.getReservasAtivas().remove(reservaParaRemover);

                    JOptionPane.showMessageDialog(painel, "Reserva cancelada com sucesso!");
                    atualizarCaixa.run(); // Atualiza a caixinha para a reserva sumir da tela
                }
            }
        });

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotao.add(btnExcluir);
        painel.add(painelBotao, BorderLayout.SOUTH);

        return painel;
    }
}