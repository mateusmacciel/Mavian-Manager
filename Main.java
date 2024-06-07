import javax.swing.*;

class Main {
    public static void main (String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ContaMestraCadastro();
            }
        });
        Criptografia.gerarChave("chave.txt");
        Criptografia.lerChave("chave.txt");
    }
}
