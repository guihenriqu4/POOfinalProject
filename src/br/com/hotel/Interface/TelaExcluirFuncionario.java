package src.br.com.hotel.Interface;

import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.services.Hotel;

import javax.swing.*;
import java.awt.*;

// Tela de demissão acessível apenas por administradores
public class TelaExcluirFuncionario {

    public static JPanel criarPainel(Hotel hotel, Funcionario usuarioLogado) {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Demitir / Excluir Funcionário", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(titulo, BorderLayout.NORTH);

        JPanel painelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelCentro.add(new JLabel("Selecione o funcionário:"));

        JComboBox<String> comboFuncionarios = new JComboBox<>();
        painelCentro.add(comboFuncionarios);

        JButton btnAtualizar = new JButton("Atualizar Lista");
        painelCentro.add(btnAtualizar);

        painel.add(painelCentro, BorderLayout.CENTER);

        JButton btnExcluir = new JButton("Excluir Funcionário do Sistema");
        btnExcluir.setBackground(new Color(94, 0, 0)); // Cor vermelha para indicar perigo
        btnExcluir.setForeground(Color.RED);

        // Preenche a lista ocultando o próprio usuário logado para ele não se excluir
        Runnable atualizarCaixa = () -> {
            comboFuncionarios.removeAllItems();
            for (Funcionario f : hotel.getFuncionarios()) {
                if (!f.getCpf().equals(usuarioLogado.getCpf())) { 
                    comboFuncionarios.addItem(f.getNome() + " (CPF: " + f.getCpf() + ")");
                }
            }
        };

        atualizarCaixa.run();
        btnAtualizar.addActionListener(e -> atualizarCaixa.run());

        // Confirmação e exclusão real da lista do hotel
        btnExcluir.addActionListener(e -> {
            String selecionado = (String) comboFuncionarios.getSelectedItem();
            if (selecionado == null) {
                JOptionPane.showMessageDialog(painel, "Nenhum funcionário selecionado!", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirmacao = JOptionPane.showConfirmDialog(painel,
                    "ATENÇÃO: Deseja realmente excluir este funcionário permanentemente?",
                    "Confirmar Demissão", JOptionPane.YES_NO_OPTION);

            if (confirmacao == JOptionPane.YES_OPTION) {
                Funcionario funcionarioParaRemover = null;
                for (Funcionario f : hotel.getFuncionarios()) {
                    if (selecionado.contains(f.getCpf())) { // Mapeia pelo CPF na String
                        funcionarioParaRemover = f;
                        break;
                    }
                }

                if (funcionarioParaRemover != null) {
                    hotel.getFuncionarios().remove(funcionarioParaRemover);
                    JOptionPane.showMessageDialog(painel, "Funcionário excluído com sucesso!");
                    atualizarCaixa.run(); // Refaz a lista sem ele
                }
            }
        });

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelBotao.add(btnExcluir);
        painel.add(painelBotao, BorderLayout.SOUTH);

        return painel;
    }
}