package src.br.com.hotel.app;

import src.br.com.hotel.model.Pessoa.Pessoa;
import src.br.com.hotel.model.Pessoa.Hospede;
import src.br.com.hotel.model.Pessoa.Funcionario;
import src.br.com.hotel.Interface.TelaAdministrador;
import src.br.com.hotel.Interface.TelaCadastroFuncionario;
import src.br.com.hotel.Interface.TelaExcluirFuncionario;
import src.br.com.hotel.Interface.TelaExcluirReserva;
import src.br.com.hotel.Interface.TelaMinhasReservas;
import src.br.com.hotel.Interface.TelaRelatorioHotel;
import src.br.com.hotel.model.Pessoa.Administrador;
import src.br.com.hotel.model.Quarto.QuartoPadrao;
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
        Administrador admin = new Administrador("Admin Roberto", "12345678900", "adminRoberto@hotel.com", "21999999999", 7500.0, "Gerência", "senha123");
        hotelSimulado = new Hotel("Hotel Paraíso, Oásis & Oceano", admin);

        hotelSimulado.carregarDados();
        // Se a lista de funcionários vier vazia, significa que o arquivo não existia
        if (hotelSimulado.getFuncionarios().isEmpty()) {

            // --- CADASTRANDO FUNCIONÁRIOS E ADMINS ---
            hotelSimulado.addFuncionario(admin);

            // +1 Administrador
            Administrador admin2 = new Administrador("Julia Costa", "22233344455", "juliacosta@hotel.com", "21999888777", 6000.0, "Gerência", "senha123");
            hotelSimulado.addFuncionario(admin2);

            // 3 Funcionários Comuns
            Funcionario funcionarioTeste = new Funcionario("Funcionario Joao", "11122233344", "joao@hotel.com", "16888888888", 2000.0, "Recepção", "senha123");
            hotelSimulado.addFuncionario(funcionarioTeste);
            Funcionario comum2 = new Funcionario("Pedro Queixo", "33344455559", "pedroqueixo@hotel.com", "21999111222", 2900.0, "Recepção", "FuncPedroQ");
            hotelSimulado.addFuncionario(comum2);
            Funcionario comum3 = new Funcionario("Marta Silva", "44455566610", "marta10@hotel.com", "11999333444", 2200.0, "Recepção", "FuncMarta10");
            hotelSimulado.addFuncionario(comum3);

            // --- CADASTRANDO HÓSPEDES ---
            Hospede hospedeTeste = new Hospede("Hospede Maria", "00011122233", "maria@gmail.com", "777777777", "Hospede123");
            Hospede h2 = new Hospede("Carlos Miguel", "55566677788", "carlos@gmail.com", "999555666", "Hospede200");
            Hospede h3 = new Hospede("Lucia Pereira", "66677788899", "lucia@gmail.com", "999777888", "Hospede203");
            Hospede h4 = new Hospede("São Marcos", "77788899900", "marcos@gmail.com", "999999000", "Hospede99");
            Hospede h5 = new Hospede("Phillipe Coutinho", "88899900011", "philcoutinho@gmail.com", "999000111", "Hospede11");
            Hospede h6 = new Hospede("Bruno Henrique", "99900011122", "bh27@gmail.com", "999222333", "Hospede00");

            hotelSimulado.addHospede(hospedeTeste);
            hotelSimulado.addHospede(h2);
            hotelSimulado.addHospede(h3);
            hotelSimulado.addHospede(h4);
            hotelSimulado.addHospede(h5);
            hotelSimulado.addHospede(h6);

            // --- POPULANDO O HOTEL COM QUARTOS ---
            for (int i = 0; i < 7; i++) {
                hotelSimulado.addQuarto(new QuartoPadrao(150.0)); // Cria do índice 0 ao 6
            }
            for (int i = 0; i < 7; i++) {
                hotelSimulado.addQuarto(new SuiteLuxo(400.0));    // Cria do índice 7 ao 13
            }
            for(int i = 0; i < 4; i++){
                hotelSimulado.addQuarto(new ChaleFamilia(300.00, 4)); // Cria do índice 14 ao 17
            }

            // --- REALIZANDO AS 6 RESERVAS (1 original + 5 novas) ---

            // Reserva 1: Maria (Quarto Padrão) feito  pelo Joao
            hotelSimulado.realizarReserva(hospedeTeste, funcionarioTeste, hotelSimulado.getQuartos().get(0), LocalDate.now() , LocalDate.now().plusDays(3), "14h-10h");

            // Reserva 2: Carlos (Quarto Padrão) feito pelo Pedro
            hotelSimulado.realizarReserva(h2, comum2, hotelSimulado.getQuartos().get(1), LocalDate.now().plusDays(1), LocalDate.now().plusDays(5), "10h-8h");

            // Reserva 3: Lucia (Chale Familia) feito pela Marta
            ChaleFamilia chaleLucia = (ChaleFamilia) hotelSimulado.getQuartos().get(14);
            chaleLucia.setCamas(2, 1); // 2 camas de solteiro e 1 de casal
            hotelSimulado.realizarReserva(h3, comum3, chaleLucia, LocalDate.now().minusDays(2), LocalDate.now().plusDays(2), "16h-14h");

            // Reserva 4: Marcos (Suíte Luxo) feito pelo Pedro
            hotelSimulado.realizarReserva(h4, comum2, hotelSimulado.getQuartos().get(7), LocalDate.now(), LocalDate.now().plusDays(4), "14h-10h");

            // Reserva 5: Phillipe Coutinho (Suíte Luxo) feito pela Julia Costa
            hotelSimulado.realizarReserva(h5, admin2, hotelSimulado.getQuartos().get(8), LocalDate.now().plusDays(2), LocalDate.now().plusDays(7), "18h-16h");

            // Reserva 6: Bruno Henrique (Chale Familia) feito pelo Joao
            ChaleFamilia chaleBruno = (ChaleFamilia) hotelSimulado.getQuartos().get(15);
            chaleBruno.setCamas(0, 2); // 0 camas de solteiro e 2 de casal
            hotelSimulado.realizarReserva(h6, funcionarioTeste, chaleBruno, LocalDate.now().minusDays(5), LocalDate.now().plusDays(1), "10h-8h");
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

        // Criação do Menu Superior (Sobre Nós -> Tela com informações do Hotel)
        JMenuBar menuBar = new JMenuBar();

        JMenu menuSobreNos = new JMenu("Sobre Nós");

        menuSobreNos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                String descricaoHotel = "Bem-vindo ao Hotel Paraíso,Oásis & Oceano !\n\n" +
                        "Localizado no litoral do Rio de Janeiro, nosso hotel oferece uma experiência " +
                        "única de conforto e tecnologia. Contamos com quartos confortáveis, " +
                        "atendimento especializado e um sistema de gestão de ponta desenvolvido " +
                        "para garantir a melhor estadia possível aos nossos hóspedes.\n\n" +
                        "Versão do Sistema: 1.0\n" +
                        "Desenvolvido por: Guilherme Henrique e Felipe Piva";

                JOptionPane.showMessageDialog(frame, descricaoHotel, "Sobre o Hotel POO", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        menuBar.add(menuSobreNos);
        frame.setJMenuBar(menuBar);

        // Painel de Abas principal
        JTabbedPane abas = new JTabbedPane();

        // Controle de Acesso: O que o Hóspede pode ver
        if (usuarioLogado instanceof Hospede) {
            abas.addTab("Minhas Reservas", TelaMinhasReservas.criarPainel(hotelSimulado, (Hospede) usuarioLogado));
        }

        // Controle de Acesso: O que Funcionários E Administradores podem ver
        if (usuarioLogado instanceof Funcionario) { 
            abas.addTab("Cadastro de Hóspede", src.br.com.hotel.Interface.TelaCadastroReserva.criarPainel(hotelSimulado, (Funcionario) usuarioLogado));
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