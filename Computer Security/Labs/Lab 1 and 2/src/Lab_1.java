import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Arrays;

public class Lab_1 {
    public static byte[] decryptedCipherText;
    public static PrivateKey privateKey;

    public static void main(String[] args){
        // CipherKey - 128 bytes, IV - 128 bytes, macKey - 128 bytes, ciphertext - remaining bytes.
        byte[] originalData = Utils.getFileData("Ciphertext and Keys/ciphertext.enc");
        byte[] encryptedCipherKey = Arrays.copyOfRange(originalData, 0, 128);
        byte[] encryptedIV = Arrays.copyOfRange(originalData, 128, 256);
        byte[] cipherText = Arrays.copyOfRange(originalData, 384, originalData.length);

        privateKey = getPrivateKey("Ciphertext and Keys/lab1Store", "lab1EncKeys", "lab1StorePass".toCharArray(), "lab1KeyPass".toCharArray());

        byte[] decryptedCipherKey = Utils.decryptRSA(privateKey, encryptedCipherKey);
        byte[] decryptedIV = Utils.decryptRSA(privateKey, encryptedIV);
        decryptedCipherText = decryptCipher(cipherText, "AES/CBC/PKCS5Padding", "AES", decryptedCipherKey, decryptedIV);


        System.out.println("Plain text: \n------------------------\n" + new String(decryptedCipherText) + "\n------------------------");

    }

    // Lab 1 - Task 2
    public static byte[] decryptCipher(byte[] cipherText, String transformation, String cipher, byte[] key, byte[] IV){
        try {
            Cipher decoder = Cipher.getInstance(transformation);
            IvParameterSpec ivParam = new IvParameterSpec(IV);
            SecretKeySpec secretKey = new SecretKeySpec(key, cipher);
            decoder.init(Cipher.DECRYPT_MODE, secretKey, ivParam);
            return decoder.doFinal(cipherText);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    //https://docs.oracle.com/javase/7/docs/api/java/security/KeyStore.html
    public static PrivateKey getPrivateKey(String file, String alias, char[] filePassword, char[] keyPassword){
        try{
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(new FileInputStream(file), filePassword);
            KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(keyPassword);
            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,protParam);

            return pkEntry.getPrivateKey();
        }catch(KeyStoreException | UnrecoverableEntryException | NoSuchAlgorithmException | CertificateException | IOException e){
            throw new RuntimeException(e);
        }
    }


}