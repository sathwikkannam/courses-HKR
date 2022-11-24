import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {

        byte[] data = getData("Ciphertext and Keys/ciphertext.enc");

        byte[] encryptedKey1 = new byte[128];
        byte[] encryptedIV = new byte[128];
        byte[] encryptedKey2 = new byte[128];
        byte[] cipherText = new byte[data.length - (encryptedIV.length + encryptedKey1.length + encryptedKey2.length)];

    }







    public static byte[] getData(String cipherFile) throws IOException {
        Path path = Paths.get(cipherFile);
        return Files.readAllBytes(path);

    }
}