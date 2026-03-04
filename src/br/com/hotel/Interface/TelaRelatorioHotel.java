package src.br.com.hotel.Interface;

import src.br.com.hotel.services.Hotel;
import src.br.com.hotel.services.Reserva;
import src.br.com.hotel.model.Quarto.Quarto;
import src.br.com.hotel.model.Quarto.QuartoPadrao;
import src.br.com.hotel.model.Quarto.SuiteLuxo;
import src.br.com.hotel.model.Quarto.ChaleFamilia;

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

            // --- BLOCO 1: DESCRIÇÃO ÚNICA DOS QUARTOS ---
            sb.append(">> GUIA DE ACOMODAÇÕES:\n");
            boolean descPadrao = false, descSuite = false, descChale = false;
            for (Quarto q : hotel.getQuartos()) {
                if (!descPadrao && q instanceof QuartoPadrao) {
                    sb.append(" • Padrão: ").append(q.getDescricao()).append("\n");
                    descPadrao = true;
                }
                if (!descSuite && q instanceof SuiteLuxo) {
                    sb.append(" • Suíte Luxo: ").append(q.getDescricao()).append("\n");
                    descSuite = true;
                }
                if (!descChale && q instanceof ChaleFamilia) {
                    sb.append(" • Chalé Família: ").append(q.getDescricao()).append("\n");
                    descChale = true;
                }
            }
            sb.append("\n");

            // --- BLOCO 2: ESTATÍSTICAS ---
            sb.append("Total de Funcionários Cadastrados: ").append(hotel.getFuncionarios().size()).append("\n");
            sb.append("Total de Hóspedes Cadastrados: ").append(hotel.getHospedes().size()).append("\n");
            sb.append("Total de Reservas Ativas no Sistema: ").append(hotel.getReservasAtivas().size()).append("\n\n");

            sb.append("--- SITUAÇÃO DOS QUARTOS PARA O DIA ").append(dataConsulta.format(dtf)).append(" ---\n\n");

            int disponiveis = 0;
            int reservados = 0;
            int padraoLivre = 0, suiteLivre = 0, chaleLivre = 0;

            sb.append(">> QUARTOS RESERVADOS/OCUPADOS:\n");

            for (Quarto q : hotel.getQuartos()) {
                Reserva reservaEncontrada = null;

                for (Reserva r : hotel.getReservasAtivas()) {
                    if (r.getQuarto().equals(q)) {
                        if (!dataConsulta.isBefore(r.getCheckIn()) && !dataConsulta.isAfter(r.getCheckOut())) {
                            reservaEncontrada = r;
                            break;
                        }
                    }
                }

                if (reservaEncontrada != null) {
                    reservados++;
                    // Não escrevemos mais a descrição aqui!
                    sb.append(" • [").append(q.getClass().getSimpleName()).append("] ");

                    // SE FOR CHALÉ, AVISA A QUANTIDADE DE CAMAS!
                    if (q instanceof ChaleFamilia) {
                        ChaleFamilia chale = (ChaleFamilia) q;
                        sb.append("(Camas: ").append(chale.getCamasSolteiro()).append(" Solteiro, ")
                                .append(chale.getCamasCasal()).append(" Casal) ");
                    }

                    sb.append("\n   Hóspede: ").append(reservaEncontrada.getHospede().getNome())
                            .append(" | De ").append(reservaEncontrada.getCheckIn().format(dtf))
                            .append(" até ").append(reservaEncontrada.getCheckOut().format(dtf));

                    try {
                        double valorPrevisto = reservaEncontrada.calcularTotal();
                        sb.append(" | Valor da Hospedagem: R$ ").append(String.format("%.2f", valorPrevisto));
                    } catch (Exception ex) {
                        sb.append(" | Valor da Hospedagem: [Erro no Cálculo]");
                    }

                    sb.append("\n   Email: "). append(reservaEncontrada.getHospede().getEmail())
                            .append(" | CPF: ").append(reservaEncontrada.getHospede().getCpf())
                            .append(" | Celular: ").append(reservaEncontrada.getHospede().getCelular())
                            .append("\n\n");
                } else {
                    disponiveis++;
                    if (q instanceof QuartoPadrao) padraoLivre++;
                    else if (q instanceof SuiteLuxo) suiteLivre++;
                    else if (q instanceof ChaleFamilia) chaleLivre++;
                }
            }

            if (reservados == 0) {
                sb.append("   Nenhum quarto reservado para esta data.\n\n");
            }

            // --- BLOCO 3: VAGAS ---
            sb.append(">> DISPONIBILIDADE DE QUARTOS LIVRES:\n");
            sb.append(" • Quartos Padrão Livres: ").append(padraoLivre).append("\n");
            sb.append(" • Suítes Luxo Livres: ").append(suiteLivre).append("\n");
            sb.append(" • Chalés Família Livres: ").append(chaleLivre).append("\n");

            sb.append("\n------------------------------------\n");
            sb.append("Resumo do Dia:\n");
            sb.append("Total Ocupado: ").append(reservados).append("\n");
            sb.append("Total Livre: ").append(disponiveis).append("\n");

            txtRelatorio.setText(sb.toString());
        });

        return painel;
    }
}