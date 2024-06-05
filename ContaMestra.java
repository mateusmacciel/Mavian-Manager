import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import org.mindrot.jbcrypt.BCrypt;

class ContaMestra extends Conta {
    public ContaMestra(String usuario, String senha) {
        super(usuario, senha);
    }

    public void mostrarConta() {
        System.out.println(usuario + senha);
    }
}

class ContaMestraCadastro extends JFrame {
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JPasswordField confirmarSenhaField;
    private JButton cadastrarButton;

    public ContaMestraCadastro() {
        setTitle("Cadastro de Conta Mestra");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setLocationRelativeTo(null);

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

        JLabel confirmarSenhaLabel = new JLabel("Confirmar Senha:");  // Novo label
        confirmarSenhaField = new JPasswordField();  // Novo campo
        confirmarSenhaField.setOpaque(false);
        confirmarSenhaLabel.setFont(font);
        confirmarSenhaLabel.setForeground(textColor);
        confirmarSenhaField.setFont(font);
        confirmarSenhaField.setForeground(textColor);

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setFont(font);
        cadastrarButton.setForeground(textColor);
        cadastrarButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        cadastrarButton.setPreferredSize(new Dimension(150, 50));

        panel.add(usuarioLabel);
        panel.add(usuarioField);
        panel.add(senhaLabel);
        panel.add(senhaField);
        panel.add(confirmarSenhaLabel);
        panel.add(confirmarSenhaField);
        panel.add(new JLabel());
        panel.add(cadastrarButton);

        add(panel);

        cadastrarButton.addActionListener(new ContaMestraListener());

        setVisible(true);
    }

    private class ContaMestraListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String usuario = usuarioField.getText();
            String senha = new String(senhaField.getPassword());
            String confirmarsenha = new String(confirmarSenhaField.getPassword());

            if(!senha.equals(confirmarsenha)){
                JOptionPane.showMessageDialog(ContaMestraCadastro.this, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String senhaHash = BCrypt.hashpw(senha, BCrypt.gensalt());
                cadastrarConta(usuario, senhaHash);
                dispose();
                new ContaMestraLogin();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(ContaMestraCadastro.this, "Erro ao cadastrar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        public void cadastrarConta(String usuario, String senhaHash) throws SQLException {
            String url = "jdbc:postgresql://localhost:5432/meudb";
            String dbUsuario = "postgres";
            String dbSenha = "mateus";

            Connection connection = DriverManager.getConnection(url, dbUsuario, dbSenha);

            String sql = "INSERT INTO mavian.usuario (usuario, senha) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuario);
            statement.setString(2, senhaHash);
            statement.executeUpdate();

            statement.close();
            connection.close();
        }
    }
}

class ContaMestraLogin extends JFrame {
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JButton loginButton;

    public ContaMestraLogin(){
            setTitle("Login Conta Mestra");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(500, 800);
            setLocationRelativeTo(null);

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

            loginButton = new JButton("Login");
            loginButton.setFont(font);
            loginButton.setForeground(textColor);
            loginButton.setBackground(new Color(0x00, 0x4d, 0xcc));
            loginButton.setPreferredSize(new Dimension(150, 50));

            panel.add(usuarioLabel);
            panel.add(usuarioField);
            panel.add(senhaLabel);
            panel.add(senhaField);
            panel.add(new JLabel());
            panel.add(loginButton);

            add(panel);

            loginButton.addActionListener(new LoginListener());

            setVisible(true);
        }

    private class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String usuario = usuarioField.getText();
            String senha = new String(senhaField.getPassword());

            try {
                if (validarCredenciais(usuario, senha)) {
                    JOptionPane.showMessageDialog(ContaMestraLogin.this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    new Gerador();
                } else {
                    JOptionPane.showMessageDialog(ContaMestraLogin.this, "Usuário ou senha incorretos", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(ContaMestraLogin.this, "Erro ao validar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        public boolean validarCredenciais(String usuario, String senha) throws SQLException {
            String url = "jdbc:postgresql://localhost:5432/meudb";
            String dbUsuario = "postgres";
            String dbSenha = "mateus";

            Connection connection = DriverManager.getConnection(url, dbUsuario, dbSenha);

            String sql = "SELECT senha FROM mavian.usuario WHERE usuario = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuario);
            ResultSet resultSet = statement.executeQuery();

            boolean existe = false;

            if (resultSet.next()) {
                String hashedSenha = resultSet.getString("senha");
                if (BCrypt.checkpw(senha, hashedSenha)) {
                    existe = true;
                }
            }

            resultSet.close();
            statement.close();
            connection.close();

            return existe;
        }
    }
}
