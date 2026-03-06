package src.br.com.hotel.Interface;

import src.br.com.hotel.model.Pessoa.Administrador;
import src.br.com.hotel.model.Pessoa.Funcionario;
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
        txtEstatisticas.setFont(new Font("Monospaced", Font.PLAIN, 13)); // Fonte monoespaçada para alinhar tabelas

        // Coleta dados das listas do hotel e exibe no formato texto
        btnVerEstatisticas.addActionListener(e -> {
            int totalHospedes = hotel.getHospedes().size();
            int totalReservas = hotel.getReservasAtivas().size();
            int totalEquipe = hotel.getFuncionarios().size();

            int qtdAdmins = 0;
            int qtdComuns = 0;

            // Usamos StringBuilder para montar os textos longos de forma mais otimizada
            StringBuilder sbAdmins = new StringBuilder();
            StringBuilder sbComuns = new StringBuilder();

            // Varre a lista de funcionários e separa quem é Admin de quem é Comum
            for (Funcionario f : hotel.getFuncionarios()) {
                if (f instanceof Administrador) {
                    qtdAdmins++;
                    sbAdmins.append(" • Nome: ").append(f.getNome())
                            .append(" | CPF: ").append(f.getCpf())
                            .append(" | Setor: ").append(f.getSetor())
                            .append(" | Salário: R$ ").append(String.format("%.2f", f.getSalario()))
                            .append(" | Celular: ").append(f.getCelular())
                            .append("\n");
                } else {
                    qtdComuns++;
                    sbComuns.append(" • Nome: ").append(f.getNome())
                            .append(" | CPF: ").append(f.getCpf())
                            .append(" | Setor: ").append(f.getSetor())
                            .append(" | Salário: R$ ").append(String.format("%.2f", f.getSalario()))
                            .append(" | Celular: ").append(f.getCelular())
                            .append("\n");
                }
            }

            // Monta o relatório final
            StringBuilder relatorio = new StringBuilder();
            relatorio.append("========== RELATÓRIO ADMINISTRATIVO ==========\n\n");

            relatorio.append(">> ESTATÍSTICAS GERAIS:\n");
            relatorio.append("Total de Hóspedes Cadastrados: ").append(totalHospedes).append("\n");
            relatorio.append("Total de Reservas Ativas: ").append(totalReservas).append("\n");
            relatorio.append("Tamanho Total da Equipe: ").append(totalEquipe).append(" colaboradores\n\n");

            relatorio.append("----------------------------------------------\n");
            relatorio.append(">> EQUIPE DE GESTÃO (ADMINISTRADORES: ").append(qtdAdmins).append(")\n\n");
            if (qtdAdmins > 0) {
                relatorio.append(sbAdmins.toString());
            } else {
                relatorio.append(" Nenhum administrador cadastrado no sistema.\n");
            }
            relatorio.append("\n");

            relatorio.append("----------------------------------------------\n");
            relatorio.append(">> EQUIPE OPERACIONAL (COMUNS: ").append(qtdComuns).append(")\n\n");
            if (qtdComuns > 0) {
                relatorio.append(sbComuns.toString());
            } else {
                relatorio.append(" Nenhum funcionário comum cadastrado no sistema.\n");
            }

            txtEstatisticas.setText(relatorio.toString());
            // Faz a barra de rolagem voltar para o topo toda vez que atualiza
            txtEstatisticas.setCaretPosition(0);
        });

        painelEstatisticas.add(painelBotao, BorderLayout.NORTH);
        painelEstatisticas.add(new JScrollPane(txtEstatisticas), BorderLayout.CENTER);

        painelAdmin.add(painelEstatisticas, BorderLayout.CENTER);

        return painelAdmin;
    }
}