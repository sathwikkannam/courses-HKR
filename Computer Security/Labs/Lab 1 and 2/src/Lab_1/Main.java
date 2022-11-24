package Lab_1;

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
import java.util.Arrays;

public class Main {
    private static Cipher decoder;
    private static final String publicKey = "lab1EncKeys";

    public static void main(String[] args){
        // Key1 - 128 bytes, IV - 128 bytes, Key2 - 128 bytes, ciphertext - remaining bytes.
        byte[] originalData = getFileData("Ciphertext and Keys/ciphertext.enc");
        byte[] encryptedKey1 = Arrays.copyOfRange(originalData, 0, 129);
        byte[] encryptedIV = Arrays.copyOfRange(originalData, 129, 258);
        byte[] encryptedKey2 = Arrays.copyOfRange(originalData, 258, 389);
        byte[] cipherText = Arrays.copyOfRange(originalData, 389, originalData.length);

        byte[] privateKey = getFileData("Ciphertext and Keys/lab1Store");


        byte[] decryptedKey1 = decryptRSA(publicKey.getBytes(), encryptedKey1);
        byte[] decryptedIV = decryptRSA(publicKey.getBytes(), encryptedIV);
        byte[] text = decryptCipher(cipherText, "AES/CBC/PKCS5Padding", "AES", decryptedKey1, decryptedIV);


        System.out.println("Cipher text: " + Arrays.toString(cipherText));
        System.out.println("------------------------");
        System.out.println("Plain text: " + Arrays.toString(text));

    }


    public static byte[] decryptRSA(byte[] publicKey, byte[] encryptedData){
        try {
            decoder = Cipher.getInstance("RSA");
            SecretKeySpec key = new SecretKeySpec(publicKey, "RSA");
            decoder.init(Cipher.DECRYPT_MODE, key);
            return decoder.doFinal(encryptedData);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }


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



}