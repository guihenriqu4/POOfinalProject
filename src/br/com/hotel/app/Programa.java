package src.br.com.hotel.app;

import src.br.com.hotel.model.Pessoa.Pessoa;
import src.br.com.hotel.model.Pessoa.Hospede;
import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.Interface.TelaAdministrador;
import src.br.com.hotel.Interface.TelaCadastroFuncionario;
import src.br.com.hotel.Interface.TelaCadastroHospede;
import src.br.com.hotel.Interface.TelaExcluirFuncionario;
import src.br.com.hotel.Interface.TelaExcluirReserva;
import src.br.com.hotel.Interface.TelaMinhasReservas;
import src.br.com.hotel.Interface.TelaRelatorioHotel;
import src.br.com.hotel.model.Pessoa.Administrador;
import src.br.com.hotel.model.Quarto.QuartoPadrao;
import src.br.com.hotel.model.Quarto.Quarto;
import src.br.com.hotel.model.Quarto.ChaleFamilia;
import src.br.com.hotel.model.Quarto.SuiteLuxo;
import src.br.com.hotel.services.Hotel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

// Classe principal que inicializa o sistema inteiro
public class Programa {
    // Instância global que funciona como o "banco de dados" em memória do hotel
    private static Hotel hotelSimulado;

    public static void main(String[] args) {
        // Tenta aplicar o visual padrão do sistema operacional nativo (Windows/Mac)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Carrega dados do arquivo ou cria dados fake se for a primeira execução
        inicializarDadosTeste();

        // Abre a tela de login e pausa a execução até o usuário logar
        Pessoa usuarioLogado = exibirTelaLogin();

        // Se logou com sucesso, abre a janela principal com as abas corretas
        if (usuarioLogado != null) {
            abrirTelaPrincipal(usuarioLogado);
        } else {
            System.exit(0); // Fecha o programa se fechar o login sem logar
        }
    }

    // Método que cria objetos falsos para o professor conseguir testar o sistema logo de cara
    private static void inicializarDadosTeste() {
        Administrador admin = new Administrador("Admin Silva", "12345678900", "admin@hotel.com", "999999999", 5000.0, "Gerência", "senha123");
        hotelSimulado = new Hotel("Hotel POO", admin);

        hotelSimulado.carregarDados();
        // Se a lista de funcionários vier vazia, significa que o arquivo não existia
        if (hotelSimulado.getFuncionarios().isEmpty()) {
            hotelSimulado.addFuncionario(admin);

            Funcionario comum = new Funcionario("Funcionario Joao", "11122233344", "joao@hotel.com", "888888888", 2000.0, "Recepção", "senha123");
            hotelSimulado.addFuncionario(comum);

            // Adiciona hóspede de teste para popular a tabela de relatórios
            Hospede hospedeTeste = new Hospede("Hospede Maria", "00011122233", "maria@gmail.com", "777777777", "Hospede123");
            hotelSimulado.addHospede(hospedeTeste);

            // Popula o hotel com vários quartos para teste
            for (int i = 0; i < 7; i++) {
                hotelSimulado.addQuarto(new QuartoPadrao(150.0));
                hotelSimulado.addQuarto(new SuiteLuxo(400.0));
            }
            for(int i = 0;i < 4; i++){
                hotelSimulado.addQuarto(new ChaleFamilia(300.00, 4));
            }
            // Pega o primeiro quarto da lista para fazer a reserva teste da Maria
            Quarto quartoMaria = hotelSimulado.getQuartos().get(0);
            hotelSimulado.realizarReserva(hospedeTeste, comum, quartoMaria, LocalDate.now(), LocalDate.now().plusDays(3), "14h-10h");
        }
        hotelSimulado.salvarDados(); // Salva esse estado inicial no arquivo
    }

    // Cria a janela de Login e valida as credenciais
    private static Pessoa exibirTelaLogin() {
        // Usa array de 1 posição para conseguir alterar o valor dentro do escopo do botão
        final Pessoa[] usuarioAutenticado = {null};

        JDialog dialogLogin = new JDialog((Frame) null, "Login de Acesso", true);
        dialogLogin.setSize(380, 200);
        dialogLogin.setLayout(new GridLayout(4, 1, 10, 10));
        dialogLogin.setLocationRelativeTo(null); // Centraliza na tela

        JPanel pnlCpf = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtCpf = new JTextField(15);
        pnlCpf.add(new JLabel("CPF (Hóspede ou Funcionário):"));
        pnlCpf.add(txtCpf);

        JPanel pnlSenha = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPasswordField txtSenha = new JPasswordField(15);
        pnlSenha.add(new JLabel("Senha:"));
        pnlSenha.add(txtSenha);

        JButton btnEntrar = new JButton("Entrar");

        dialogLogin.add(new JLabel("Bem-vindo! Insira suas credenciais:", SwingConstants.CENTER));
        dialogLogin.add(pnlCpf);
        dialogLogin.add(pnlSenha);
        dialogLogin.add(btnEntrar);

        // Ação de clique no botão "Entrar"
        btnEntrar.addActionListener(e -> {
            String cpfDigitado = txtCpf.getText();
            String senhaDigitada = new String(txtSenha.getPassword());

            boolean usuarioEncontrado = false;

            // 1. Tenta logar como Hóspede primeiro
            for (Hospede h : hotelSimulado.getHospedes()) {
                if (h.getCpf().equals(cpfDigitado)) {
                    usuarioEncontrado = true;
                    if (h.getSenhaHospede().equals(senhaDigitada)) {
                        JOptionPane.showMessageDialog(dialogLogin, "Login efetuado!\nBem-vindo Hóspede: " + h.getNome());
                        usuarioAutenticado[0] = h;
                        dialogLogin.dispose(); // Fecha o popup de login
                        return;
                    } else {
                        JOptionPane.showMessageDialog(dialogLogin, "Senha de Hóspede Incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // 2. Tenta logar como Funcionário ou Administrador
            if (!usuarioEncontrado) {
                for (Funcionario f : hotelSimulado.getFuncionarios()) {
                    if (f.getCpf().equals(cpfDigitado)) {
                        usuarioEncontrado = true;
                        // Chama o método autenticar() que está na classe Funcionario
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

            // Se rodou tudo e não achou ninguém
            if (!usuarioEncontrado) {
                JOptionPane.showMessageDialog(dialogLogin, "Usuário não encontrado!", "Erro", JOptionPane.WARNING_MESSAGE);
            }
        });

        dialogLogin.setVisible(true);
        return usuarioAutenticado[0];
    }

    // Monta a janela principal do sistema baseada em quem logou
    private static void abrirTelaPrincipal(Pessoa usuarioLogado) {
        JFrame frame = new JFrame("Sistema Hoteleiro - " + usuarioLogado.getNome());

        // Criação do Menu Superior (Arquivo -> Novo / Sair)
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

        // Painel de Abas principal
        JTabbedPane abas = new JTabbedPane();

        // Controle de Acesso: O que o Hóspede pode ver
        if (usuarioLogado instanceof Hospede) {
            abas.addTab("Minhas Reservas", TelaMinhasReservas.criarPainel(hotelSimulado, (Hospede) usuarioLogado));
        }

        // Controle de Acesso: O que Funcionários E Administradores podem ver
        if (usuarioLogado instanceof Funcionario) { 
            abas.addTab("Cadastro de Hóspede", TelaCadastroHospede.criarPainel(hotelSimulado, (Funcionario) usuarioLogado));
            abas.addTab("Excluir Reserva", TelaExcluirReserva.criarPainel(hotelSimulado));
            abas.addTab("Relatório do Hotel", TelaRelatorioHotel.criarPainel(hotelSimulado));
        }

        // Controle de Acesso: O que APENAS Administradores podem ver
        if (usuarioLogado instanceof Administrador) { 
            abas.addTab("Cadastro de Funcionário", TelaCadastroFuncionario.criarPainel(hotelSimulado));
            abas.addTab("Excluir Funcionário", TelaExcluirFuncionario.criarPainel(hotelSimulado, (Funcionario) usuarioLogado));
            abas.addTab("Painel de Administrador", TelaAdministrador.criarPainel(hotelSimulado));
        }

        frame.add(abas);
        frame.setSize(800, 650);
        
        // Impede que a janela feche sem executar a ação programada abaixo
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        // EVENTO IMPORTANTE: Salva os dados físicos (persistência) toda vez que clica no 'X' para fechar
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                hotelSimulado.salvarDados();
                System.exit(0);
            }
        });
        
        frame.setLocationRelativeTo(null); // Centraliza a tela principal
        frame.setVisible(true);
    }
}