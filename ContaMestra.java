import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Base64;
import org.mindrot.jbcrypt.BCrypt;

class ContaMestraCadastro {
    private JFrame frame;
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JPasswordField confirmarSenhaField;
    private JButton cadastrarButton;
    private SecretKeySpec chaveAES;

    public ContaMestraCadastro() {
        this.chaveAES = Criptografia.lerChave("chave.txt");
        frame = new JFrame("Cadastro de Conta Mestra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 800);
        frame.setLocationRelativeTo(null);

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

        JLabel confirmarSenhaLabel = new JLabel("Confirmar Senha:");
        confirmarSenhaField = new JPasswordField();
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

        frame.add(panel);

        cadastrarButton.addActionListener(new ContaMestraListener());

        frame.setVisible(true);
    }

    private class ContaMestraListener extends Conta implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String usuario = usuarioField.getText();
            String senha = new String(senhaField.getPassword());
            String confirmarsenha = new String(confirmarSenhaField.getPassword());

            if(!senha.equals(confirmarsenha)){
                JOptionPane.showMessageDialog(frame, "As senhas não coincidem!", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if(usuario.isEmpty() || senha.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String senhaCriptografada = criptografarSenha(senha);
                cadastrarConta(usuario, senhaCriptografada);
                frame.dispose();
                new ContaMestraLogin();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao cadastrar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        private String criptografarSenha(String senha) throws Exception {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, chaveAES);
            byte[] senhaCriptografadaBytes = cipher.doFinal(senha.getBytes());
            return Base64.getEncoder().encodeToString(senhaCriptografadaBytes);
        }

        public void cadastrarConta(String usuario, String senhaCriptografada) throws SQLException {
            String url = "jdbc:postgresql://localhost:5432/meudb";
            String dbUsuario = "postgres";
            String dbSenha = "mateus";

            Connection connection = DriverManager.getConnection(url, dbUsuario, dbSenha);

            String sql = "INSERT INTO mavian.usuario (usuario, senha) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuario);
            statement.setString(2, senhaCriptografada);
            statement.executeUpdate();

            statement.close();
            connection.close();
        }

        public void cadastrarConta(String email, String senha, String site) throws SQLException { }
    }
}

class ContaMestraLogin {
    private JFrame frame;
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JButton loginButton;

    public ContaMestraLogin() {
        frame = new JFrame("Login Conta Mestra");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 800);
        frame.setLocationRelativeTo(null);

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

        frame.add(panel);

        loginButton.addActionListener(new LoginListener());

        frame.setVisible(true);
    }

    private class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String usuario = usuarioField.getText();
            String senha = new String(senhaField.getPassword());

            try {
                SecretKeySpec chaveAES = Criptografia.lerChave("chave.txt");

                if (validarCredenciais(usuario, senha, chaveAES)) {
                    JOptionPane.showMessageDialog(frame, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    new Gerador();
                } else {
                    JOptionPane.showMessageDialog(frame, "Usuário ou senha incorretos", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(frame, "Erro ao validar usuário: " + exc.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        public boolean validarCredenciais(String usuario, String senha, SecretKeySpec chaveAES) throws Exception {
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
                String senhaCriptografada = resultSet.getString("senha");

                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, chaveAES);
                byte[] senhaBytes = cipher.doFinal(Base64.getDecoder().decode(senhaCriptografada));
                String senhaDescriptografada = new String(senhaBytes);

                if (senha.equals(senhaDescriptografada)) {
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
