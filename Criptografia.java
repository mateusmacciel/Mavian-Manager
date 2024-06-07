import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

class Criptografia {

    public static void gerarChave(String nomeArquivo) {
        try {
            String algoritmo = "AES";
            int tamanhoChave = 256;

            KeyGenerator keyGen = KeyGenerator.getInstance(algoritmo);
            keyGen.init(tamanhoChave);
            SecretKey chave = keyGen.generateKey();

            byte[] chaveCodificada = chave.getEncoded();
            SecretKeySpec chaveSecreta = new SecretKeySpec(chaveCodificada, algoritmo);

            FileOutputStream fileOut = new FileOutputStream(nomeArquivo);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(chaveSecreta);
            objectOut.close();
            fileOut.close();

            System.out.println("Chave AES gravada com sucesso.");
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    public static SecretKeySpec lerChave(String nomeArquivo) {
        SecretKeySpec chave = null;
        try {
            FileInputStream fileIn = new FileInputStream(nomeArquivo);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            chave = (SecretKeySpec) objectIn.readObject();
            objectIn.close();
            fileIn.close();

            System.out.println("Chave lida do arquivo: " + bytesParaString(chave.getEncoded()));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return chave;
    }

    private static String bytesParaString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
}
