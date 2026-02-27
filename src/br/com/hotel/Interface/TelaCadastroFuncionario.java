package src.br.com.hotel.Interface;

import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.model.Pessoa.Administrador;
import src.br.com.hotel.services.Hotel;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroFuncionario {

    // Recebe o Hotel por parâmetro para salvar o funcionário nele
    public static JPanel criarPainel(Hotel hotel) {
        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelFormulario = new JPanel(new GridLayout(8, 2, 10, 10));

        // Chama o método BasePessoa da nossa nova classe de Utilidades
        JTextField[] camposPessoa = UtilidadesInterface.BasePessoa(painelFormulario);

        JTextField txtSalario = new JTextField();
        JTextField txtSetor = new JTextField();
        JPasswordField txtSenha = new JPasswordField();
        JRadioButton rbComum = new JRadioButton("Comum", true);
        JRadioButton rbAdmin = new JRadioButton("Administrador");
        ButtonGroup bg = new ButtonGroup(); bg.add(rbComum); bg.add(rbAdmin);
        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelTipo.add(rbComum);
        painelTipo.add(rbAdmin);

        painelFormulario.add(new JLabel("Salário (R$):")); painelFormulario.add(txtSalario);
        painelFormulario.add(new JLabel("Setor:")); painelFormulario.add(txtSetor);
        painelFormulario.add(new JLabel("Senha de Acesso:")); painelFormulario.add(txtSenha);
        painelFormulario.add(new JLabel("Nível de Acesso:")); painelFormulario.add(painelTipo);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");

        btnSalvar.addActionListener(e -> {
            try {
                Funcionario novo;
                if (rbAdmin.isSelected()) {
                    novo = new Administrador(camposPessoa[0].getText(), camposPessoa[1].getText(), camposPessoa[2].getText(), camposPessoa[3].getText(), Double.parseDouble(txtSalario.getText()), txtSetor.getText(), new String(txtSenha.getPassword()));
                } else {
                    novo = new Funcionario(camposPessoa[0].getText(), camposPessoa[1].getText(), camposPessoa[2].getText(), camposPessoa[3].getText(), Double.parseDouble(txtSalario.getText()), txtSetor.getText(), new String(txtSenha.getPassword()));
                }
                hotel.addFuncionario(novo);
                JOptionPane.showMessageDialog(painelCadastro, "Funcionário salvo!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painelCadastro, "Erro: Verifique os dados.");
            }
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(btnLimpar);
        painelBotoes.add(btnSalvar);

        painelCadastro.add(painelFormulario, BorderLayout.NORTH);
        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);

        return painelCadastro;
    }
}