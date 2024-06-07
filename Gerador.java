import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class Gerador {
    private JFrame frame;
    private JTextField tamanhoField;
    private JPasswordField resultadoField;

    public Gerador() {
        frame = new JFrame("Gerador");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 800);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(15, 1, 10, 10));
        panel.setBackground(new Color(0x17, 0x1B, 0x27));

        JLabel label = new JLabel("Tamanho da senha:");
        tamanhoField = new JTextField();

        JButton gerarButton = new JButton("Gerar senha");
        JLabel resultadoLabel = new JLabel("Senha gerada:");
        resultadoField = new JPasswordField();
        resultadoField.setEditable(false);

        Font font = new Font("Arial", Font.BOLD, 18);
        Color textColor = Color.WHITE;

        label.setFont(font);
        label.setForeground(textColor);

        tamanhoField.setFont(font);
        tamanhoField.setForeground(textColor);
        tamanhoField.setOpaque(false);
        tamanhoField.setBorder(BorderFactory.createLineBorder(Color.WHITE));

        gerarButton.setFont(font);
        gerarButton.setForeground(textColor);
        gerarButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        gerarButton.setPreferredSize(new Dimension(150, 50));

        resultadoLabel.setFont(font);
        resultadoLabel.setForeground(textColor);

        resultadoField.setFont(font);
        resultadoField.setOpaque(false);
        resultadoField.setForeground(textColor);
        resultadoField.setBackground(Color.DARK_GRAY);

        JButton toggleVisibilityButton = new JButton("👁");
        toggleVisibilityButton.setFont(new Font("Arial", Font.PLAIN, 18));
        toggleVisibilityButton.setPreferredSize(new Dimension(50, 50));
        toggleVisibilityButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        toggleVisibilityButton.setForeground(Color.WHITE);
        toggleVisibilityButton.setBorder(BorderFactory.createEmptyBorder());
        toggleVisibilityButton.setFocusPainted(false);

        JButton abrirCadastroButton = new JButton("Cadastrar Conta de Serviço");
        abrirCadastroButton.setFont(font);
        abrirCadastroButton.setForeground(textColor);
        abrirCadastroButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        abrirCadastroButton.setPreferredSize(new Dimension(200, 50));

        JButton abrirCofreButton = new JButton("Cofre");
        abrirCofreButton.setFont(font);
        abrirCofreButton.setForeground(textColor);
        abrirCofreButton.setBackground(new Color(0x00, 0x4d, 0xcc));

        panel.add(abrirCadastroButton);
        panel.add(abrirCofreButton);

        abrirCofreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new Cofre();
            }
        });

        toggleVisibilityButton.addActionListener(new ActionListener() {
            private boolean isPasswordVisible = false;

            public void actionPerformed(ActionEvent e) {
                if (isPasswordVisible) {
                    resultadoField.setEchoChar('*');
                    toggleVisibilityButton.setText("👁");
                } else {
                    resultadoField.setEchoChar((char) 0);
                    toggleVisibilityButton.setText("👁‍🗨");
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });

        abrirCadastroButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ContaServicoCadastro();
            }
        });

        panel.add(label);
        panel.add(tamanhoField);
        panel.add(gerarButton);
        panel.add(new JLabel());
        panel.add(resultadoLabel);
        panel.add(resultadoField);
        panel.add(toggleVisibilityButton);

        frame.add(panel);
        frame.setVisible(true);

        gerarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int tamanho = Integer.parseInt(tamanhoField.getText());
                    String senha = gerarSenha(tamanho);
                    resultadoField.setText(senha);
                } catch (GeradorException ge) {
                    JOptionPane.showMessageDialog(frame, ge.getMessage());
                }
            }
        });
    }

    public static String gerarSenha(int tamanho) throws GeradorException {

        if (tamanho <= 0) {
            throw new GeradorException("O tamanho deve ser maior que zero.");
        }

        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789" +
                "!@#$%&*";

        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        for (int i = 0; i < tamanho; i++) {
            int indice = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(indice));
        }

        return sb.toString();
    }
}

class GeradorException extends Exception {
    public GeradorException(String message) {
        super(message);
    }

    public GeradorException(String message, Throwable cause) {
        super(message, cause);
    }
}

