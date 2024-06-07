import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class ContaServicoCadastro {
    private JFrame frame;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField siteField;
    private JButton cadastrarButton;

    public ContaServicoCadastro() {
        frame = new JFrame("Cadastro de Conta de Serviço");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 800);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(15, 1, 10, 10));
        panel.setBackground(new Color(0x17, 0x1B, 0x27));

        Font font = new Font("Arial", Font.BOLD, 18);
        Color textColor = Color.WHITE;

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        emailField.setOpaque(false);
        emailLabel.setFont(font);
        emailLabel.setForeground(textColor);
        emailField.setFont(font);
        emailField.setForeground(textColor);

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

        JButton abrirGeradorButton = new JButton("Gerador");
        abrirGeradorButton.setFont(font);
        abrirGeradorButton.setForeground(textColor);
        abrirGeradorButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        panel.add(abrirGeradorButton);

        abrirGeradorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Gerador();
            }
        });

        JButton abrirCofreButton = new JButton("Cofre");
        abrirCofreButton.setFont(font);
        abrirCofreButton.setForeground(textColor);
        abrirCofreButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        panel.add(abrirCofreButton);

        abrirCofreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Cofre();
            }
        });

        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(senhaLabel);
        panel.add(senhaField);
        panel.add(siteLabel);
        panel.add(siteField);
        panel.add(new JLabel());
        panel.add(cadastrarButton);

        frame.add(panel);

        cadastrarButton.addActionListener(new CadastroListener());

        frame.setVisible(true);
    }

    private class CadastroListener extends Conta implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String email = emailField.getText();
            String senha = new String(senhaField.getPassword());
            String site = siteField.getText();

            try {
                cadastrarConta(email, senha, site);
                JOptionPane.showMessageDialog(frame, "Cadastro realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao cadastrar conta: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        public void cadastrarConta(String email, String senha, String site) throws SQLException {
            String url = "jdbc:postgresql://localhost:5432/meudb";
            String dbUsuario = "postgres";
            String dbSenha = "mateus";

            Connection connection = DriverManager.getConnection(url, dbUsuario, dbSenha);

            String sql = "INSERT INTO mavian.contas (email, senha, site) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, senha);
            statement.setString(3, site);
            statement.executeUpdate();

            statement.close();
            connection.close();
        }

        public void cadastrarConta(String email, String hashSenha) throws SQLException { }
    }
}
