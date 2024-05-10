import java.util.Random;

class Gerador {
    public static String gerarSenha(int tamanho) {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "abcdefghijklmnopqrstuvwxyz" +
                "0123456789" +
                "!@#$%&*";

        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        for(int i = 0; i < tamanho; i++) {
            int indice = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(indice));
        }

        return sb.toString();
    }
}
