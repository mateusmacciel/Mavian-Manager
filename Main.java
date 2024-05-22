import javax.swing.*;


class Main {
    public static void main (String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Gerador();
                //new ContaMestraInterface();
                //new ContaServicoInterface();
            }
        });
    }
}
