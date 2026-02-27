package src.br.com.hotel.Interface;

import src.br.com.hotel.services.Hotel;
import src.br.com.hotel.services.Reserva;
import src.br.com.hotel.model.Quarto.Quarto;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TelaRelatorioHotel {

    public static JPanel criarPainel(Hotel hotel) {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelFiltro.add(new JLabel("Selecione uma data para verificar os quartos:"));

        JSpinner spinnerData = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(spinnerData, "dd/MM/yyyy");
        spinnerData.setEditor(dateEditor);
        painelFiltro.add(spinnerData);

        JButton btnGerar = new JButton("Gerar Relatório");
        painelFiltro.add(btnGerar);

        painel.add(painelFiltro, BorderLayout.NORTH);

        JTextArea txtRelatorio = new JTextArea("Selecione uma data e clique em 'Gerar Relatório'...");
        txtRelatorio.setEditable(false);
        txtRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 12));
        painel.add(new JScrollPane(txtRelatorio), BorderLayout.CENTER);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        btnGerar.addActionListener(e -> {
            Date dataSelecionadaUtil = (Date) spinnerData.getValue();
            LocalDate dataConsulta = dataSelecionadaUtil.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            StringBuilder sb = new StringBuilder();
            sb.append("===== RELATÓRIO GERAL DO HOTEL =====\n\n");
            sb.append("Total de Funcionários Cadastrados: ").append(hotel.getFuncionarios().size()).append("\n");
            sb.append("Total de Hóspedes Cadastrados: ").append(hotel.getHospedes().size()).append("\n");
            sb.append("Total de Reservas Ativas no Sistema: ").append(hotel.getReservasAtivas().size()).append("\n\n");

            sb.append("--- SITUAÇÃO DOS QUARTOS PARA O DIA ").append(dataConsulta.format(dtf)).append(" ---\n");

            int disponiveis = 0;
            int reservados = 0;

            for (Quarto q : hotel.getQuartos()) {
                Reserva reservaEncontrada = null;
                // Verifica se este quarto está em alguma reserva que sobrepõe a data consultada
                for (Reserva r : hotel.getReservasAtivas()) {
                    if (r.getQuarto().equals(q)) {
                        // Se a data de consulta estiver entre o check-in e check-out
                        if (!dataConsulta.isBefore(r.getCheckIn()) && !dataConsulta.isAfter(r.getCheckOut())) {
                            reservaEncontrada = r;
                            break;
                        }
                    }
                }

                if (reservaEncontrada != null) {
                    reservados++;
                    sb.append("[RESERVADO] ").append(q.getClass().getSimpleName())
                            .append(" - Hóspede: ").append(reservaEncontrada.getHospede().getNome())
                            .append(" (De ").append(reservaEncontrada.getCheckIn().format(dtf))
                            .append(" até ").append(reservaEncontrada.getCheckOut().format(dtf)).append(")\n");
                } else {
                    disponiveis++;
                    sb.append("[LIVRE] ").append(q.getClass().getSimpleName())
                            .append(" - ").append(q.getDescricao()).append("\n");
                }
            }

            sb.append("\nResumo do Dia:\n");
            sb.append("Quartos Disponíveis: ").append(disponiveis).append("\n");
            sb.append("Quartos Reservados: ").append(reservados).append("\n");

            txtRelatorio.setText(sb.toString());
        });

        return painel;
    }
}