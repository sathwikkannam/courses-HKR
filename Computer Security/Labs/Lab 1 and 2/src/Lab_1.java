import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

public class Lab_1 {
    private static Cipher decoder;

    public static void main(String[] args){
        // Key1 - 128 bytes, IV - 128 bytes, Key2 - 128 bytes, ciphertext - remaining bytes.
        byte[] originalData = getFileData("Ciphertext and Keys/ciphertext.enc");
        byte[] encryptedKey1 = Arrays.copyOfRange(originalData, 0, 128);
        byte[] encryptedIV = Arrays.copyOfRange(originalData, 128, 256);
        byte[] encryptedKey2 = Arrays.copyOfRange(originalData, 256, 384);
        byte[] cipherText = Arrays.copyOfRange(originalData, 384, originalData.length);
        PrivateKey privateKey = getPrivateKey("Ciphertext and Keys/lab1Store", "lab1EncKeys", "lab1StorePass".toCharArray(), "lab1KeyPass".toCharArray());

        PublicKey publicKey = getPublicKey("Ciphertext and Keys/lab1Sign.cert");

        byte[] decryptedKey1 = decryptRSA(privateKey, encryptedKey1);
        byte[] decryptedIV = decryptRSA(privateKey, encryptedIV);
        byte[] decryptedKey2 = decryptRSA(privateKey, encryptedKey2);
        byte[] decryptCipherText = decryptCipher(cipherText, "AES/CBC/PKCS5Padding", "AES", decryptedKey1, decryptedIV);


        System.out.println("Cipher text: " + Arrays.toString(cipherText));
        System.out.println("------------------------");
        System.out.println("Plain text: " + Arrays.toString(decryptCipherText));

    }

    // Lab 1 - Task 1
    public static byte[] decryptRSA(PrivateKey publicKey, byte[] encryptedData){
        try {
            decoder = Cipher.getInstance("RSA");
            decoder.init(Cipher.DECRYPT_MODE, publicKey);
            decoder.update(encryptedData);
            return decoder.doFinal();

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }

    // Lab 1 - Task 2
    public static byte[] decryptCipher(byte[] cipherText, String transformation, String cipher, byte[] key, byte[] IV){
        try {
            decoder = Cipher.getInstance(transformation);
            IvParameterSpec ivParam = new IvParameterSpec(IV);
            SecretKeySpec secretKey = new SecretKeySpec(key, cipher);
            decoder.init(Cipher.DECRYPT_MODE, secretKey, ivParam);
            return decoder.doFinal(cipherText);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }


    public static byte[] getFileData(String file){
        try{
            Path path = Paths.get(file);
            return Files.readAllBytes(path);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public static PublicKey getPublicKey(String file){
        try{
            FileInputStream publicKeyFile = new FileInputStream(file);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate certificate = certificateFactory.generateCertificate(publicKeyFile);
            return certificate.getPublicKey();

        }catch(FileNotFoundException | CertificateException e){
            throw new RuntimeException(e);
        }

    }

    //https://docs.oracle.com/javase/7/docs/api/java/security/KeyStore.html
    public static PrivateKey getPrivateKey(String file, String alias, char[] filePassword, char[] keyPassword){
        try{
            KeyStore keyStore = KeyStore.getInstance("JCEKS");
            keyStore.load(new FileInputStream(file), filePassword);
            KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(keyPassword);
            KeyStore.PrivateKeyEntry pkEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias,protParam);

            return pkEntry.getPrivateKey();
        }catch(KeyStoreException | UnrecoverableEntryException | NoSuchAlgorithmException | CertificateException | IOException e){
            throw new RuntimeException(e);
        }
    }


}