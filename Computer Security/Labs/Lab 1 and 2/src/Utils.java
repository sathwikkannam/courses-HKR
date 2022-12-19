import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class Utils {

    public static byte[] getFileData(String file){
        try{
            return Files.readAllBytes(Paths.get(file));
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public static byte[] decryptRSA(PrivateKey key, byte[] encryptedData){
        try {
            Cipher decoder = Cipher.getInstance("RSA");
            decoder.init(Cipher.DECRYPT_MODE, key);
            return decoder.doFinal(encryptedData);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException |
                 InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }
}
