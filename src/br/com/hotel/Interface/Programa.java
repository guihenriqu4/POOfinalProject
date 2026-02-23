import javax.swing.*;
import java.awt.*;

public class Program {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        JFrame frame = new JFrame("Sistema Hoteleiro");

        JMenuBar menuBar = new JMenuBar();
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemNovo = new JMenuItem("Novo");
        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> {
            System.exit(0);
        });
        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem itemAjuda = new JMenuItem("Sobre nós");

        menuArquivo.add(itemNovo);
        menuArquivo.add(itemSair);
        menuBar.add(menuArquivo);
        menuBar.add(menuAjuda);
        menuAjuda.add(itemAjuda);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Cadastro de Hóspede", painelCadastro());
        abas.addTab("Lista de Hóspedes", painelLista());
        abas.addTab("Estatísticas", painelEstatisticas());
        frame.add(abas);

        frame.setJMenuBar(menuBar);
        frame.setSize(675, 350);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel painelCadastro() {
        JPanel painelCadastro = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Nome do hóspede:");
        JTextField fieldNome = new JTextField();

        String[] quartoescolhido = {"Quarto Individual", "Suíte Luxo", "Quarto Padrão (para casal)", "Chalé Família"};
        JComboBox<String> comboQuartos = new JComboBox<>(quartoescolhido);

        JSpinner spinnerDataInicio = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditorInicio = new JSpinner.DateEditor(spinnerDataInicio, "dd/MM/yyyy");
        spinnerDataInicio.setEditor(dateEditorInicio);

        JRadioButton checkinEout = new JRadioButton("10h-8h");
        JRadioButton checkinEout2 = new JRadioButton("14h-10h");
        JRadioButton checkinEout3 = new JRadioButton("16h-14h");
        JRadioButton checkinEout4 = new JRadioButton("18h-16h");
        ButtonGroup Checkinecheckout = new ButtonGroup();
        Checkinecheckout.add(checkinEout);
        Checkinecheckout.add(checkinEout2);
        Checkinecheckout.add(checkinEout3);
        Checkinecheckout.add(checkinEout4);

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

        JButton salvar = new JButton("Salvar");
        JButton limpar = new JButton("Limpar");

        JPanel painelQuartos = new JPanel(new GridLayout(5, 2, 10, 20));

        painelQuartos.add(label);
        painelQuartos.add(fieldNome);

        painelQuartos.add(new JLabel("Quarto:"));
        painelQuartos.add(comboQuartos);

        painelQuartos.add(new JLabel("Data de Entrada:"));
        JPanel painelDataInicioLayout = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelDataInicioLayout.add(spinnerDataInicio);
        painelQuartos.add(painelDataInicioLayout);

        painelQuartos.add(new JLabel("Horário do Check-in e Check-out:"));
        JPanel painelCheckinEout = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelCheckinEout.add(checkinEout);
        painelCheckinEout.add(checkinEout2);
        painelCheckinEout.add(checkinEout3);
        painelCheckinEout.add(checkinEout4);
        painelQuartos.add(painelCheckinEout);

        painelQuartos.add(label2);

        JPanel painelBolsistas = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        painelBolsistas.add(bolsistaS);
        painelBolsistas.add(painelDatas);
        painelQuartos.add(painelBolsistas);

        painelCadastro.add(painelQuartos, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(limpar);
        painelBotoes.add(salvar);

        painelCadastro.add(painelBotoes, BorderLayout.SOUTH);

        return painelCadastro;
    }
}