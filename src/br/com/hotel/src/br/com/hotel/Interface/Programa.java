package src.br.com.hotel.Interface;

import src.br.com.hotel.Interface.TelaExcluirFuncionario;
import src.br.com.hotel.Interface.TelaRelatorioHotel;
import src.br.com.hotel.model.Pessoa.Pessoa;
import src.br.com.hotel.model.Pessoa.Hospede;
import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.model.Pessoa.Administrador;
import src.br.com.hotel.model.Quarto.QuartoPadrao;
import src.br.com.hotel.model.Quarto.SuiteLuxo;
import src.br.com.hotel.services.Hotel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class Programa {
    private static Hotel hotelSimulado;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        inicializarDadosTeste();

        Pessoa usuarioLogado = exibirTelaLogin();

        if (usuarioLogado != null) {
            abrirTelaPrincipal(usuarioLogado);
        } else {
            System.exit(0);
        }
    }

    private static void inicializarDadosTeste() {
        Administrador admin = new Administrador("Admin Silva", "12345678900", "admin@hotel.com", "999999999", 5000.0, "Gerência", "senha123");
        hotelSimulado = new Hotel("Hotel POO", admin);
        hotelSimulado.addFuncionario(admin);

        Funcionario comum = new Funcionario("Funcionario Joao", "11122233344", "joao@hotel.com", "888888888", 2000.0, "Recepção", "senha123");
        hotelSimulado.addFuncionario(comum);

        // --- ADICIONADO DADOS DE TESTE PARA O RELATÓRIO E PARA O HÓSPEDE ---
        Hospede hospedeTeste = new Hospede("Hospede Maria", "00011122233", "maria@gmail.com", "777777777", "FIDELIDADE123");
        hotelSimulado.addHospede(hospedeTeste);

        QuartoPadrao q1 = new QuartoPadrao(150.0);
        SuiteLuxo q2 = new SuiteLuxo(400.0);
        hotelSimulado.addQuarto(q1);
        hotelSimulado.addQuarto(q2);

        // Cria uma reserva para Maria para podermos ver na tela dela e no relatório
        hotelSimulado.realizarReserva(hospedeTeste, comum, q1, LocalDate.now(), LocalDate.now().plusDays(3),"14h-10h");
    }

    // O Retorno mudou de Funcionario para Pessoa!
    private static Pessoa exibirTelaLogin() {
        final Pessoa[] usuarioAutenticado = {null};

        JDialog dialogLogin = new JDialog((Frame) null, "Login de Acesso", true);
        dialogLogin.setSize(380, 200);
        dialogLogin.setLayout(new GridLayout(4, 1, 10, 10));
        dialogLogin.setLocationRelativeTo(null);

        JPanel pnlCpf = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtCpf = new JTextField(15);
        pnlCpf.add(new JLabel("CPF (Hóspede ou Funcionário):")); pnlCpf.add(txtCpf);

        JPanel pnlSenha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPasswordField txtSenha = new JPasswordField(15);
        pnlSenha.add(new JLabel("Senha / N. Fidelidade:")); pnlSenha.add(txtSenha);

        JButton btnEntrar = new JButton("Entrar");

        dialogLogin.add(new JLabel("Bem-vindo! Insira suas credenciais:", SwingConstants.CENTER));
        dialogLogin.add(pnlCpf);
        dialogLogin.add(pnlSenha);
        dialogLogin.add(btnEntrar);

        btnEntrar.addActionListener(e -> {
            String cpfDigitado = txtCpf.getText();
            String senhaDigitada = new String(txtSenha.getPassword());

            boolean usuarioEncontrado = false;

            // 1. Tenta logar como Hóspede primeiro
            for (Hospede h : hotelSimulado.getHospedes()) {
                if (h.getCpf().equals(cpfDigitado)) {
                    usuarioEncontrado = true;
                    if (h.getNumFidelidade().equals(senhaDigitada)) {
                        JOptionPane.showMessageDialog(dialogLogin, "Login efetuado!\nBem-vindo Hóspede: " + h.getNome());
                        usuarioAutenticado[0] = h;
                        dialogLogin.dispose();
                        return;
                    } else {
                        JOptionPane.showMessageDialog(dialogLogin, "Número de Fidelidade Incorreto!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // 2. Tenta logar como Funcionário/Admin
            if (!usuarioEncontrado) {
                for (Funcionario f : hotelSimulado.getFuncionarios()) {
                    if (f.getCpf().equals(cpfDigitado)) {
                        usuarioEncontrado = true;
                        if (f.autenticar(senhaDigitada)) {
                            JOptionPane.showMessageDialog(dialogLogin, "Login efetuado!\nBem-vindo(a), " + f.getNome());
                            usuarioAutenticado[0] = f;
                            dialogLogin.dispose();
                            return;
                        } else {
                            JOptionPane.showMessageDialog(dialogLogin, "Senha incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }
                }
            }

            if (!usuarioEncontrado) {
                JOptionPane.showMessageDialog(dialogLogin, "Usuário não encontrado!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        });

        dialogLogin.setVisible(true);
        return usuarioAutenticado[0];
    }

    private static void abrirTelaPrincipal(Pessoa usuarioLogado) {
        JFrame frame = new JFrame("Sistema Hoteleiro - " + usuarioLogado.getNome());

        JMenuBar menuBar = new JMenuBar();
        JMenu menuArquivo = new JMenu("Arquivo");
        JMenuItem itemNovo = new JMenuItem("Novo");
        JMenuItem itemSair = new JMenuItem("Sair");
        itemSair.addActionListener(e -> System.exit(0));

        JMenu menuAjuda = new JMenu("Ajuda");
        JMenuItem itemAjuda = new JMenuItem("Sobre nós");

        menuArquivo.add(itemNovo);
        menuArquivo.add(itemSair);
        menuBar.add(menuArquivo);
        menuBar.add(menuAjuda);
        menuAjuda.add(itemAjuda);
        frame.setJMenuBar(menuBar);

        JTabbedPane abas = new JTabbedPane();

        // Regras de Visualização das Abas
        if(usuarioLogado instanceof Hospede) {
            abas.addTab("Minhas Reservas", TelaMinhasReservas.criarPainel(hotelSimulado, (Hospede) usuarioLogado));
        }

        if(usuarioLogado instanceof Funcionario) { // Entra Funcionario e Administrador aqui
            abas.addTab("Cadastro de Hóspede", TelaCadastroHospede.criarPainel(hotelSimulado));
            abas.addTab("Excluir Reserva", TelaExcluirReserva.criarPainel(hotelSimulado));
            abas.addTab("Relatório do Hotel", TelaRelatorioHotel.criarPainel(hotelSimulado));
        }

        if(usuarioLogado instanceof Administrador) { // Entra só Administrador aqui
            abas.addTab("Cadastro de Funcionário", TelaCadastroFuncionario.criarPainel(hotelSimulado));
            abas.addTab("Excluir Funcionário", TelaExcluirFuncionario.criarPainel(hotelSimulado,(Funcionario) usuarioLogado));
            abas.addTab("Painel de Administrador", TelaAdministrador.criarPainel(hotelSimulado));
        }

        frame.add(abas);
        frame.setSize(800, 650);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}