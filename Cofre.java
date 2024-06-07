import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.ArrayList;

class Cofre {
    private JFrame frame;
    private JTextArea textArea;
    private JButton consultarButton;
    private JButton abrirCadastroButton;
    private JButton abrirGeradorButton;
    private SecretKeySpec chaveAES;
    private ArrayList<String> contas;

    public Cofre() {
        this.chaveAES = Criptografia.lerChave("chave.txt");
        this.contas = new ArrayList<>();

        frame = new JFrame("Cofre de Senhas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 800);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(new Color(0x17, 0x1B, 0x27));

        Font font = new Font("Arial", Font.BOLD, 18);
        Color textColor = Color.WHITE;

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1, 10, 10));
        topPanel.setBackground(new Color(0x17, 0x1B, 0x27));

        abrirCadastroButton = new JButton("Cadastrar Conta de Serviço");
        abrirCadastroButton.setFont(font);
        abrirCadastroButton.setForeground(textColor);
        abrirCadastroButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        abrirCadastroButton.setPreferredSize(new Dimension(200, 50));
        abrirCadastroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ContaServicoCadastro();
            }
        });

        abrirGeradorButton = new JButton("Gerador");
        abrirGeradorButton.setFont(font);
        abrirGeradorButton.setForeground(textColor);
        abrirGeradorButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        abrirGeradorButton.setPreferredSize(new Dimension(200, 50));
        abrirGeradorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Gerador();
            }
        });

        topPanel.add(abrirCadastroButton);
        topPanel.add(abrirGeradorButton);

        panel.add(topPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setFont(font);
        textArea.setForeground(textColor);
        textArea.setBackground(new Color(0x17, 0x1B, 0x27));
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        consultarButton = new JButton("Consultar Contas");
        consultarButton.setFont(font);
        consultarButton.setForeground(textColor);
        consultarButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        consultarButton.setPreferredSize(new Dimension(200, 50));
        consultarButton.addActionListener(new ConsultarListener());

        panel.add(consultarButton, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    private class ConsultarListener extends Criptografia implements ActionListener {
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
                contas.clear();

                while (resultSet.next()) {
                    String email = resultSet.getString("email");
                    String senhaHash = resultSet.getString("senha");
                    String site = resultSet.getString("site");

                    String senhaDescriptografada = descriptografarSenha(senhaHash);

                    contas.add("Email: " + email + "\n" +
                            "Senha: " + senhaDescriptografada + "\n" +
                            "Site: " + site + "\n\n");
                }

                atualizarTextArea();

                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Ocorreu algum erro ao tentar consultar suas contas.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        protected String descriptografarSenha(String senhaCriptografada) throws Exception {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, chaveAES);
            byte[] senhaBytes = cipher.doFinal(Base64.getDecoder().decode(senhaCriptografada));
            return new String(senhaBytes);
        }
    }

    private void atualizarTextArea() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String conta : contas) {
            stringBuilder.append(conta);
        }
        textArea.setText(stringBuilder.toString());
    }
}

