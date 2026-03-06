package src.br.com.hotel.Interface;


import src.br.com.hotel.exceptions.CamposInvalidosException;
import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.model.Pessoa.Hospede;
import src.br.com.hotel.model.Quarto.ChaleFamilia;
import src.br.com.hotel.model.Quarto.Quarto;
import src.br.com.hotel.model.Quarto.QuartoPadrao;
import src.br.com.hotel.model.Quarto.SuiteLuxo;
import src.br.com.hotel.services.Hotel;
import src.br.com.hotel.services.Reserva;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

// Interface principal onde a recepção cadastra novos clientes e cria reservas simultaneamente
public class TelaCadastroReserva {

    public static JPanel criarPainel(Hotel hotel, Funcionario responsavel) {
        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelFormulario = new JPanel(new GridLayout(12, 2, 10, 10));

        // Reutiliza campos básicos
        JTextField[] camposPessoa = UtilidadesInterface.BasePessoa(painelFormulario);

        JTextField txtsenhaHospede = new JTextField();
        painelFormulario.add(new JLabel("Senha do Hóspede:"));
        painelFormulario.add(txtsenhaHospede);

        JComboBox<String> comboTiposQuarto = new JComboBox<>();

        // Função que varre a lista de quartos e contabiliza quantos estão desocupados de cada tipo
        Runnable atualizarQuartos = () -> {
            comboTiposQuarto.removeAllItems();
            int padrao = 0, suite = 0, chale = 0;

            for (Quarto q : hotel.getQuartos()) {
                if (!q.isOcupado()) {
                    if (q instanceof QuartoPadrao) padrao++;
                    else if (q instanceof SuiteLuxo) suite++;
                    else if (q instanceof ChaleFamilia) chale++;
                }
            }
            if (padrao > 0) comboTiposQuarto.addItem("Quarto Padrão (" + padrao + " livres)");
            if (suite > 0) comboTiposQuarto.addItem("Suíte Luxo (" + suite + " livres)");
            if (chale > 0) comboTiposQuarto.addItem("Chalé Família (" + chale + " livres)");
        };
        atualizarQuartos.run();

        JButton btnAtualizarQuartos = new JButton("Atualizar Lista");
        btnAtualizarQuartos.addActionListener(e -> atualizarQuartos.run());

        JPanel painelQuartoLivre = new JPanel(new BorderLayout(5, 0));
        painelQuartoLivre.add(comboTiposQuarto, BorderLayout.CENTER);
        painelQuartoLivre.add(btnAtualizarQuartos, BorderLayout.EAST);

        painelFormulario.add(new JLabel("Tipo de Quarto:"));
        painelFormulario.add(painelQuartoLivre);

        // Painel extra que só aparece se for Chalé Família
        JLabel lblCamas = new JLabel("Configuração das Camas:");
        JPanel painelCamas = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JComboBox<Integer> comboSolteiro = new JComboBox<>(new Integer[]{0, 1, 2, 3, 4, 5, 6});
        JComboBox<Integer> comboCasal = new JComboBox<>(new Integer[]{0, 1, 2, 3});

        painelCamas.add(new JLabel("Solteiro:")); painelCamas.add(comboSolteiro);
        painelCamas.add(new JLabel("  Casal:")); painelCamas.add(comboCasal);

        lblCamas.setVisible(false);
        painelCamas.setVisible(false);

        // Listener que esconde ou mostra a seleção de camas baseado na escolha da combobox
        comboTiposQuarto.addItemListener(e -> {
            if (comboTiposQuarto.getSelectedItem() != null) {
                boolean isChale = comboTiposQuarto.getSelectedItem().toString().contains("Chalé");
                lblCamas.setVisible(isChale);
                painelCamas.setVisible(isChale);
            }
        });

        painelFormulario.add(lblCamas);
        painelFormulario.add(painelCamas);

        // Seletor de Datas nativo do Java
        JSpinner spinnerDataInicio = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditorInicio = new JSpinner.DateEditor(spinnerDataInicio, "dd/MM/yyyy");
        spinnerDataInicio.setEditor(dateEditorInicio);
        painelFormulario.add(new JLabel("Data de Entrada:"));
        JPanel painelDataInicioLayout = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelDataInicioLayout.add(spinnerDataInicio);
        painelFormulario.add(painelDataInicioLayout);

        JRadioButton checkinEout = new JRadioButton("10h-8h", true);
        JRadioButton checkinEout2 = new JRadioButton("14h-10h");
        JRadioButton checkinEout3 = new JRadioButton("16h-14h");
        JRadioButton checkinEout4 = new JRadioButton("18h-16h");
        ButtonGroup Checkinecheckout = new ButtonGroup();
        Checkinecheckout.add(checkinEout); Checkinecheckout.add(checkinEout2);
        Checkinecheckout.add(checkinEout3); Checkinecheckout.add(checkinEout4);

        painelFormulario.add(new JLabel("Horário do Check-in/out:"));
        JPanel painelCheckinEout = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelCheckinEout.add(checkinEout); painelCheckinEout.add(checkinEout2);
        painelCheckinEout.add(checkinEout3); painelCheckinEout.add(checkinEout4);
        painelFormulario.add(painelCheckinEout);

        JLabel label2 = new JLabel("Mais de um dia de hospedagem?");
        JCheckBox MaisDeumdia = new JCheckBox("Sim");

        JPanel painelDatas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelDatas.setVisible(false);

        JSpinner spinnerDataFim = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditorFim = new JSpinner.DateEditor(spinnerDataFim, "dd/MM/yyyy");
        spinnerDataFim.setEditor(dateEditorFim);

        painelDatas.add(new JLabel("Data de saída:"));
        painelDatas.add(spinnerDataFim);

        // Mostra a data final apenas se o cliente for ficar mais de 1 dia
        MaisDeumdia.addItemListener(e -> {
            painelDatas.setVisible(MaisDeumdia.isSelected());
            painelDatas.revalidate(); painelDatas.repaint();
        });

        painelFormulario.add(label2);
        JPanel painelMaisdeumdia = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelMaisdeumdia.add(MaisDeumdia); painelMaisdeumdia.add(painelDatas);
        painelFormulario.add(painelMaisdeumdia);

        JLabel lblValorTotal = new JLabel("Valor Previsto: R$ 0,00");
        lblValorTotal.setFont(new Font("Arial", Font.BOLD, 14));
        lblValorTotal.setForeground(new Color(0, 100, 0));

        JButton btnCalcular = new JButton("Calcular Valor");

        // Função para prever o valor financeiro chamando o método polimórfico de calcularValor
        btnCalcular.addActionListener(e -> {
            String tipoSelecionado = (String) comboTiposQuarto.getSelectedItem();
            if (tipoSelecionado == null) return;

            Quarto quartoParaSimulacao = null;
            // Encontra um quarto compatível apenas para simular preço
            for (Quarto q : hotel.getQuartos()) {
                if (!q.isOcupado()) {
                    if (tipoSelecionado.contains("Padrão") && q instanceof QuartoPadrao) { quartoParaSimulacao = q; break; }
                    else if (tipoSelecionado.contains("Suíte") && q instanceof SuiteLuxo) { quartoParaSimulacao = q; break; }
                    else if (tipoSelecionado.contains("Chalé") && q instanceof ChaleFamilia) { quartoParaSimulacao = q; break; }
                }
            }

            if (quartoParaSimulacao != null) {
                if (quartoParaSimulacao instanceof ChaleFamilia) {
                    int solteiro = (Integer) comboSolteiro.getSelectedItem();
                    int casal = (Integer) comboCasal.getSelectedItem();
                    int totalCamasEquivalente = solteiro + (casal * 2);
                    if (totalCamasEquivalente > 6) {
                        JOptionPane.showMessageDialog(painelCadastro, "Capacidade excedida para o Chalé (Max: 6 equivalentes).", "Aviso", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                    ((ChaleFamilia) quartoParaSimulacao).setCamas(solteiro, casal);
                }

                LocalDate in = ((Date) spinnerDataInicio.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate out = MaisDeumdia.isSelected() ? ((Date) spinnerDataFim.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : in;
                long dias = ChronoUnit.DAYS.between(in, out);
                if (dias <= 0) dias = 1;

                // Executa polimorfismo aqui!
                double valor = quartoParaSimulacao.calcularValor((int) dias);
                lblValorTotal.setText(String.format("Valor Previsto: R$ %.2f", valor));
            }
        });

        painelFormulario.add(btnCalcular);
        painelFormulario.add(lblValorTotal);

        JButton salvar = new JButton("Salvar Hóspede e Reserva");
        JButton limpar = new JButton("Limpar");

        // Consolida o cadastro no Backend
        salvar.addActionListener(e -> {
            try {
                String nome = camposPessoa[0].getText().trim();
                String cpf = camposPessoa[1].getText().trim();
                String email = camposPessoa[2].getText().trim();
                String celular = camposPessoa[3].getText().trim();
                String senha = txtsenhaHospede.getText().trim();

                if(nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || celular.isEmpty() || senha.isEmpty()){
                    throw new CamposInvalidosException("Por favor, preencha todos os campos do Hóspede!");
                }
                String tipoSelecionado = (String) comboTiposQuarto.getSelectedItem();
                if (tipoSelecionado == null) {
                    JOptionPane.showMessageDialog(painelCadastro, "Não há quartos disponíveis!", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int solteiro = 0, casal = 0;
                if (tipoSelecionado.contains("Chalé")) {
                    solteiro = (Integer) comboSolteiro.getSelectedItem();
                    casal = (Integer) comboCasal.getSelectedItem();
                    int totalCamasEquivalente = solteiro + (casal * 2);

                    if (totalCamasEquivalente > 6) {
                        JOptionPane.showMessageDialog(painelCadastro, "Capacidade excedida! O Chalé suporta no máximo 6 pessoas (Casal=2, Solteiro=1).", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    if (totalCamasEquivalente == 0) {
                        JOptionPane.showMessageDialog(painelCadastro, "Você precisa selecionar pelo menos uma cama para o Chalé.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                // Elege um quarto físico vago para o hospede
                Quarto quartoParaReserva = null;
                for (Quarto q : hotel.getQuartos()) {
                    if (!q.isOcupado()) {
                        if (tipoSelecionado.contains("Padrão") && q instanceof QuartoPadrao) { quartoParaReserva = q; break; }
                        else if (tipoSelecionado.contains("Suíte") && q instanceof SuiteLuxo) { quartoParaReserva = q; break; }
                        else if (tipoSelecionado.contains("Chalé") && q instanceof ChaleFamilia) { quartoParaReserva = q; break; }
                    }
                }

                if (quartoParaReserva == null) return;

                if (quartoParaReserva instanceof ChaleFamilia) {
                    ((ChaleFamilia) quartoParaReserva).setCamas(solteiro, casal);
                }

                Hospede h = new Hospede(camposPessoa[0].getText(), camposPessoa[1].getText(), camposPessoa[2].getText(), camposPessoa[3].getText(), txtsenhaHospede.getText());
                hotel.addHospede(h);

                LocalDate dataCheckIn = ((Date) spinnerDataInicio.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate dataCheckOut = MaisDeumdia.isSelected() ? ((Date) spinnerDataFim.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate() : dataCheckIn;
                String horario = checkinEout.isSelected() ? "10h-8h" : (checkinEout2.isSelected() ? "14h-10h" : (checkinEout3.isSelected() ? "16h-14h" : "18h-16h"));

                // Integra e cria a reserva associando hóspede, recepcionista e quarto
                // 1. Guarda a reserva que o hotel acabou de criar na memória
                Reserva reservaCriada = hotel.realizarReserva(h, responsavel, quartoParaReserva, dataCheckIn, dataCheckOut, horario);

                // 2. Verifica quais checkboxes estão marcados e faz a Composição acontecer lá no back-end!
                atualizarQuartos.run();

                JOptionPane.showMessageDialog(painelCadastro, "Hóspede salvo e Reserva efetuada com sucesso!");
                lblValorTotal.setText("Valor Previsto: R$ 0,00");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painelCadastro, "Erro ao salvar: Verifique os dados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(limpar); painelBotoes.add(salvar);
        painelCadastro.add(painelFormulario, BorderLayout.NORTH);
        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);

        return painelCadastro;
    }
}