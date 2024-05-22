import javax.swing.*;
import java.awt.*;

class ContaServico extends Conta {
    private String site;

    public ContaServico(String usuario, String senha, String site) {
        super(usuario, senha);
        this.site = site;
    }

    public void mostrarConta() {
        System.out.println(usuario + senha + site);
    }
}

class ContaServicoInterface extends JFrame {
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JTextField siteField;
    private JButton cadastrarButton;

    public ContaServicoInterface() {
        setTitle("Cadastro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(15, 1, 10, 10));
        panel.setBackground(new Color(0x17, 0x1B, 0x27));

        Font font = new Font("Arial", Font.BOLD, 18);
        Color textColor = Color.WHITE;

        JLabel usuarioLabel = new JLabel("Usuário:");
        usuarioField = new JTextField();
        usuarioField.setOpaque(false);
        usuarioLabel.setFont(font);
        usuarioLabel.setForeground(textColor);
        usuarioField.setFont(font);
        usuarioField.setForeground(textColor);

        JLabel senhaLabel = new JLabel("Senha:");
        senhaField = new JPasswordField();
        senhaField.setOpaque(false);
        senhaLabel.setFont(font);
        senhaLabel.setForeground(textColor);
        senhaField.setFont(font);
        senhaField.setForeground(textColor);

        JLabel siteLabel = new JLabel("Site:");
        siteField = new JTextField();
        siteField.setOpaque(false);
        siteLabel.setFont(font);
        siteLabel.setForeground(textColor);
        siteField.setFont(font);
        siteField.setForeground(textColor);

        cadastrarButton = new JButton("Cadastrar");

        cadastrarButton.setFont(font);
        cadastrarButton.setForeground(textColor);
        cadastrarButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        cadastrarButton.setPreferredSize(new Dimension(150, 50));

        panel.add(usuarioLabel);
        panel.add(usuarioField);
        panel.add(senhaLabel);
        panel.add(senhaField);
        panel.add(siteLabel);
        panel.add(siteField);
        panel.add(new JLabel());
        panel.add(cadastrarButton);

        add(panel);
        setVisible(true);

    }

        public void cadastrarContaServico (ContaServico contaservico){

        }
}
