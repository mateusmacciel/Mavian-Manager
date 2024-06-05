import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Cofre extends JFrame {
    private JTextArea textArea;
    private JButton consultarButton;
    private JButton abrirCadastroButton;
    private JButton abrirGeradorButton;

    private class ConsultarListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            consultarContas();
        }

        private void consultarContas() {
            String url = "jdbc:postgresql://localhost:5432/meudb";
            String dbUsuario = "postgres";
            String dbSenha = "mateus";

            try {
                Connection connection = DriverManager.getConnection(url, dbUsuario, dbSenha);

                String sql = "SELECT email, senha, site FROM mavian.contas";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                StringBuilder stringBuilder = new StringBuilder();

                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String senhaHash = resultSet.getString("senha");
                    String site = resultSet.getString("site");
                    stringBuilder.append("Email: ").append(email).append("\n");
                    stringBuilder.append("Senha Hash: ").append(senhaHash).append("\n");
                    stringBuilder.append("Site: ").append(site).append("\n\n");
                }

                textArea.setText(stringBuilder.toString());

                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(Cofre.this, "Erro ao consultar as contas no cofre: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Cofre());
    }
}
