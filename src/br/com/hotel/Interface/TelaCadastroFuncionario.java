package src.br.com.hotel.Interface;

import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.model.Pessoa.Administrador;
import src.br.com.hotel.services.Hotel;
import src.br.com.hotel.exceptions.CamposInvalidosException;
import src.br.com.hotel.exceptions.SalarioNegativoException;
import src.br.com.hotel.exceptions.FormatoInvalidoException;


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

        //Açcão do botão limpar
        btnLimpar.addActionListener(e -> {
            //Limpa os campos de texto básicos (Nome, CPF, Email, Celular)
            for (JTextField campo : camposPessoa) {
                campo.setText("");
            }

            //Limpa campos específicos de funcionário
            txtSalario.setText("");
            txtSetor.setText("");
            txtSenha.setText("");

            //Reseta o nível de acesso para "Comum"
            rbComum.setSelected(true);
        });

        // Ação de salvamento
        btnSalvar.addActionListener(e -> {
            try {
                String nome = camposPessoa[0].getText().trim();
                String cpf = camposPessoa[1].getText().trim();
                String email = camposPessoa[2].getText().trim();
                String celularTexto = camposPessoa[3].getText().trim();
                String salarioTexto = txtSalario.getText().trim();
                String setor = txtSetor.getText().trim();
                String senha = new String(txtSenha.getPassword()).trim();

                // 1. Validação de campos vazios
                if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || celularTexto.isEmpty() ||
                        salarioTexto.isEmpty() || setor.isEmpty() || senha.isEmpty()) {
                    throw new CamposInvalidosException("Por favor, preencha todos os campos antes de salvar!");
                }

                // 2. Validação de formato (Transformando NumberFormatException na sua classe)
                long celularLong;
                double valorSalario;
                try {
                    celularLong = Long.parseLong(celularTexto);
                    valorSalario = Double.parseDouble(salarioTexto);
                } catch (NumberFormatException ex) {
                    throw new FormatoInvalidoException("Salário e Celular devem conter apenas números!");
                }

                // 3. Validação de regra de negócio (Salário)
                if (valorSalario <= 0) {
                    throw new SalarioNegativoException("O salário não pode ser um valor negativo ou nulo!");
                }

                Funcionario novo;
                // Instancia usando o celular convertido para long e o salário validado
                if (rbAdmin.isSelected()) {
                    novo = new Administrador(nome, cpf, email, celularLong, valorSalario, setor, senha);
                } else {
                    novo = new Funcionario(nome, cpf, email, celularLong, valorSalario, setor, senha);
                }

                hotel.addFuncionario(novo);
                JOptionPane.showMessageDialog(painelCadastro, "Funcionário salvo!");

            } catch (CamposInvalidosException ex) {
                JOptionPane.showMessageDialog(painelCadastro, ex.getMessage(), "Campos em Branco", JOptionPane.WARNING_MESSAGE);
            } catch (FormatoInvalidoException ex) {
                JOptionPane.showMessageDialog(painelCadastro, ex.getMessage(), "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (SalarioNegativoException ex) {
                JOptionPane.showMessageDialog(painelCadastro, ex.getMessage(), "Erro de Valor", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painelCadastro, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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