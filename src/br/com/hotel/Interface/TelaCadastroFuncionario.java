package src.br.com.hotel.Interface;

import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.model.Pessoa.Administrador;
import src.br.com.hotel.services.Hotel;
import src.br.com.hotel.exceptions.CamposInvalidosException;

import javax.swing.*;
import java.awt.*;

// Classe responsável pelo formulário de admissão de equipe
public class TelaCadastroFuncionario {

    public static JPanel criarPainel(Hotel hotel) {
        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelFormulario = new JPanel(new GridLayout(8, 2, 10, 10));

        // Reutiliza o método BasePessoa para gerar campos comuns (Nome, CPF, Email, Celular)
        JTextField[] camposPessoa = UtilidadesInterface.BasePessoa(painelFormulario);

        JTextField txtSalario = new JTextField();
        JTextField txtSetor = new JTextField();
        JPasswordField txtSenha = new JPasswordField();
        
        // Cria botões de rádio para decidir o nível de acesso
        JRadioButton rbComum = new JRadioButton("Comum", true);
        JRadioButton rbAdmin = new JRadioButton("Administrador");
        ButtonGroup bg = new ButtonGroup(); // Agrupa para que só um possa ser selecionado
        bg.add(rbComum); bg.add(rbAdmin);
        
        JPanel painelTipo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelTipo.add(rbComum);
        painelTipo.add(rbAdmin);

        painelFormulario.add(new JLabel("Salário (R$):")); painelFormulario.add(txtSalario);
        painelFormulario.add(new JLabel("Setor:")); painelFormulario.add(txtSetor);
        painelFormulario.add(new JLabel("Senha de Acesso:")); painelFormulario.add(txtSenha);
        painelFormulario.add(new JLabel("Nível de Acesso:")); painelFormulario.add(painelTipo);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnLimpar = new JButton("Limpar");

        // Ação de salvamento
        btnSalvar.addActionListener(e -> {
            try {
                String nome = camposPessoa[0].getText().trim();
                String cpf = camposPessoa[1].getText().trim();
                String email = camposPessoa[2].getText().trim();
                String celular = camposPessoa[3].getText().trim();
                String salario = txtSalario.getText().trim();
                String setor = txtSetor.getText().trim();
                String senha = new String(txtSenha.getPassword()).trim();

                //Lança a excessão se tiver alguma caixa vazia ao salvar
                if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || celular.isEmpty() ||
                        salario.isEmpty() || setor.isEmpty() || senha.isEmpty()) {
                    throw new CamposInvalidosException("Por favor, preencha todos os campos antes de salvar!");
                }

                Funcionario novo;
                // Instancia Administrador ou Funcionario comum com base no Radio Button (Polimorfismo/Herança)
                if (rbAdmin.isSelected()) {
                    novo = new Administrador(camposPessoa[0].getText(), camposPessoa[1].getText(), camposPessoa[2].getText(), camposPessoa[3].getText(), Double.parseDouble(txtSalario.getText()), txtSetor.getText(), new String(txtSenha.getPassword()));
                } else {
                    novo = new Funcionario(camposPessoa[0].getText(), camposPessoa[1].getText(), camposPessoa[2].getText(), camposPessoa[3].getText(), Double.parseDouble(txtSalario.getText()), txtSetor.getText(), new String(txtSenha.getPassword()));
                }
                // Adiciona na lista principal do sistema
                hotel.addFuncionario(novo);
                JOptionPane.showMessageDialog(painelCadastro, "Funcionário salvo!");
            } catch (CamposInvalidosException ex){
                JOptionPane.showMessageDialog(painelCadastro,ex.getMessage(), "Campos em Branco", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painelCadastro, "Erro: Verifique se o salário é um número válido!","Erro", JOptionPane.ERROR_MESSAGE);
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