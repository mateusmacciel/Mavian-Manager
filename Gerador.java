import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

class Gerador extends JFrame {
    private JTextField tamanhoField;
    private JPasswordField resultadoField;

    public Gerador() {
        // Configurações da janela
        setTitle("Gerador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 800);
        setLocationRelativeTo(null);

        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(15, 1, 10, 10));
        panel.setBackground(new Color(0x17, 0x1B, 0x27));

        // Componentes da interface
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
        tamanhoField.setBorder(BorderFactory.createLineBorder(Color.WHITE)); // Adiciona uma borda para visibilidade

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

        // Botão de alternância de visibilidade da senha
        JButton toggleVisibilityButton = new JButton("👁");
        toggleVisibilityButton.setFont(new Font("Arial", Font.PLAIN, 18));
        toggleVisibilityButton.setPreferredSize(new Dimension(50, 50));
        toggleVisibilityButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        toggleVisibilityButton.setForeground(Color.WHITE);
        toggleVisibilityButton.setBorder(BorderFactory.createEmptyBorder());
        toggleVisibilityButton.setFocusPainted(false);

        toggleVisibilityButton.addActionListener(new ActionListener() {
            private boolean isPasswordVisible = false;

            @Override
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

        // Adiciona componentes ao painel
        panel.add(label);
        panel.add(tamanhoField);
        panel.add(gerarButton);
        panel.add(new JLabel());  // Filler
        panel.add(resultadoLabel);
        panel.add(resultadoField);
        panel.add(toggleVisibilityButton);  // Adicione o botão de alternância de visibilidade

        // Adiciona painel à janela
        add(panel);
        setVisible(true);

        // Listener do botão "Gerar senha"
        gerarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int tamanho = Integer.parseInt(tamanhoField.getText());
                    String senha = gerarSenha(tamanho);
                    resultadoField.setText(senha);
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(Gerador.this, "Por favor, insira um número.");
                }
            }
        });
    }

    public static String gerarSenha(int tamanho) {
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
