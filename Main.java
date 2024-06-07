import javax.swing.*;

class Main {
    public static void main (String[] args) {
        Criptografia.gerarChave("chave.txt");
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ContaMestraCadastro();
            }
        });
    }
}
