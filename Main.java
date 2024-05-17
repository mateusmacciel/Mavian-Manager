import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;

public class Main {
    public static void main (String[] args) {
        JFrame frame = new JFrame("Gerador");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 800);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8,10,50,50));
        panel.setBackground(new Color(0x00, 0x1d, 0x66));

        JLabel label = new JLabel("Tamanho da senha:");
        JTextField tamanhoField = new JTextField();

        JButton gerarButton = new JButton("Gerar senha");
        JLabel resultadolabel = new JLabel("Senha gerada:");
        JTextField resultadoField = new JTextField();
        resultadoField.setEditable(false);

        Font font = new Font("Arial", Font.BOLD, 18);
        Color textColor = Color.WHITE;

        label.setFont(font);
        label.setForeground(textColor);

        tamanhoField.setFont(font);
        tamanhoField.setForeground(textColor);
        tamanhoField.setBackground(Color.DARK_GRAY);

        gerarButton.setFont(font);
        gerarButton.setForeground(textColor);
        gerarButton.setBackground(new Color(0x00, 0x4d, 0xcc));
        gerarButton.setPreferredSize(new Dimension(150,50));

        resultadolabel.setFont(font);
        resultadolabel.setForeground(textColor);

        resultadoField.setFont(font);
        resultadoField.setForeground(textColor);
        resultadoField.setBackground(Color.DARK_GRAY);

        panel.add(label);
        panel.add(tamanhoField);
        panel.add(gerarButton);
        panel.add(new JLabel());
        panel.add(resultadolabel);
        panel.add(resultadoField);

        frame.add(panel);

        frame.setVisible(true);
    }
}

