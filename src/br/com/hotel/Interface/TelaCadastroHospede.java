package src.br.com.hotel.Interface;

import src.br.com.hotel.model.Pessoa.Hospede;
import src.br.com.hotel.services.Hotel;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroHospede {

    public static JPanel criarPainel(Hotel hotel) {
        JPanel painelCadastro = new JPanel(new BorderLayout(10, 10));
        painelCadastro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel painelFormulario = new JPanel(new GridLayout(10, 2, 10, 15));

        JTextField[] camposPessoa = UtilidadesInterface.BasePessoa(painelFormulario);

        JTextField txtFidelidade = new JTextField();
        painelFormulario.add(new JLabel("Nº Fidelidade:"));
        painelFormulario.add(txtFidelidade);

        String[] quartoescolhido = {"Quarto Individual", "Suíte Luxo", "Quarto Padrão (para casal)", "Chalé Família"};
        JComboBox<String> comboQuartos = new JComboBox<>(quartoescolhido);
        painelFormulario.add(new JLabel("Quarto:"));
        painelFormulario.add(comboQuartos);

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
        Checkinecheckout.add(checkinEout);
        Checkinecheckout.add(checkinEout2);
        Checkinecheckout.add(checkinEout3);
        Checkinecheckout.add(checkinEout4);

        painelFormulario.add(new JLabel("Horário do Check-in e Check-out:"));
        JPanel painelCheckinEout = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelCheckinEout.add(checkinEout);
        painelCheckinEout.add(checkinEout2);
        painelCheckinEout.add(checkinEout3);
        painelCheckinEout.add(checkinEout4);
        painelFormulario.add(painelCheckinEout);

        JLabel label2 = new JLabel("Mais de um dia de hospedagem?");
        JCheckBox bolsistaS = new JCheckBox("Sim");

        JPanel painelDatas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        painelDatas.setVisible(false);

        JSpinner spinnerDataFim = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditorFim = new JSpinner.DateEditor(spinnerDataFim, "dd/MM/yyyy");
        spinnerDataFim.setEditor(dateEditorFim);

        painelDatas.add(new JLabel("Data de saída:"));
        painelDatas.add(spinnerDataFim);

        bolsistaS.addItemListener(e -> {
            painelDatas.setVisible(bolsistaS.isSelected());
            painelDatas.revalidate();
            painelDatas.repaint();
        });

        painelFormulario.add(label2);
        JPanel painelBolsistas = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelBolsistas.add(bolsistaS);
        painelBolsistas.add(painelDatas);
        painelFormulario.add(painelBolsistas);

        JButton salvar = new JButton("Salvar");
        JButton limpar = new JButton("Limpar");

        salvar.addActionListener(e -> {
            try {
                Hospede h = new Hospede(camposPessoa[0].getText(), camposPessoa[1].getText(), camposPessoa[2].getText(), camposPessoa[3].getText(), txtFidelidade.getText());
                hotel.addHospede(h);
                JOptionPane.showMessageDialog(painelCadastro, "Hóspede salvo!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(painelCadastro, "Erro ao salvar.");
            }
        });

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(limpar);
        painelBotoes.add(salvar);

        painelCadastro.add(painelFormulario, BorderLayout.NORTH);
        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);

        return painelCadastro;
    }
}