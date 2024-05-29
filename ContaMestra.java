import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class ContaMestra extends Conta {
    public ContaMestra(String usuario, String senha) {
        super(usuario, senha);
    }

    public void mostrarConta() {
        System.out.println(usuario + senha);
    }
}


class ContaMestraInterface extends JFrame {
    private JTextField usuarioField;
    private JPasswordField senhaField;
    private JButton cadastrarButton;

    public ContaMestraInterface() {
        setTitle("Cadastro");
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

        cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setFont(font);
        cadastrarButton.setForeground(textColor);
        cadastrarButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        cadastrarButton.setPreferredSize(new Dimension(150, 50));

        panel.add(usuarioLabel);
        panel.add(usuarioField);
        panel.add(senhaLabel);
        panel.add(senhaField);
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

            try {
                cadastrarConta(usuario, senha);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(ContaMestraInterface.this, "Erro ao cadastrar usuário: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }

        public void cadastrarConta(String usuario, String senha) throws SQLException {
            String url = "jdbc:postgresql://localhost:5432/meudb";
            String dbUsuario = "postgres";
            String dbSenha = "mateus";

            Connection connection = DriverManager.getConnection(url, dbUsuario, dbSenha);

            String sql = "INSERT INTO mavian.usuario (usuario, senha) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, usuario);
            statement.setString(2, senha);
            statement.executeUpdate();

            statement.close();
            connection.close();
        }
    }
}
