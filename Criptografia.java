import java.io.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
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

            byte[] iv = new byte[16];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);

            try (FileOutputStream fileOut = new FileOutputStream(nomeArquivo);
                 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                objectOut.writeObject(chaveSecreta);
                objectOut.writeObject(iv);
            }

            System.out.println("Chave AES e IV gravados com sucesso.");
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }

    public static SecretKeySpec lerChave(String nomeArquivo) {
        SecretKeySpec chave = null;
        byte[] iv = null;
        try (FileInputStream fileIn = new FileInputStream(nomeArquivo);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            chave = (SecretKeySpec) objectIn.readObject();
            iv = (byte[]) objectIn.readObject();
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            System.out.println("Chave lida do arquivo: " + bytesParaString(chave.getEncoded()));
            System.out.println("IV lido do arquivo: " + bytesParaString(ivSpec.getIV()));
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
