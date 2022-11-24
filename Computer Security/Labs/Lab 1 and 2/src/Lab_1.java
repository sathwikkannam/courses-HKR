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
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
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
        byte[] encryptedIV = Arrays.copyOfRange(originalData, 129, 257);
        byte[] encryptedKey2 = Arrays.copyOfRange(originalData, 258, 386);
        byte[] cipherText = Arrays.copyOfRange(originalData, 387, originalData.length);
        byte[] privateKey = getFileData("Ciphertext and Keys/lab1Store");

        PublicKey publicKey = getPublicKey("Ciphertext and Keys/lab1Sign.cert");

        byte[] decryptedKey1 = decryptRSA(publicKey, encryptedKey1);
        byte[] decryptedIV = decryptRSA(publicKey, encryptedIV);
        byte[] decryptedKey2 = decryptRSA(publicKey, encryptedKey2);
        byte[] decryptCipherText = decryptCipher(cipherText, "AES/CBC/PKCS5Padding", "AES", decryptedKey1, decryptedIV);


        System.out.println("Cipher text: " + Arrays.toString(cipherText));
        System.out.println("------------------------");
        System.out.println("Plain text: " + Arrays.toString(decryptCipherText));

    }

    // Lab 1 - Task 1
    public static byte[] decryptRSA(PublicKey publicKey, byte[] encryptedData){
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
            decoder.update(cipherText);
            return decoder.doFinal(); // Cipher.update() 16 bytes of blocks at a time for AES.

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



}