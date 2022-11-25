import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;

public class Lab_2 {
    public static void main(String[] args){
        String mac1 = getMac("Ciphertext and Keys/ciphertext.mac1.txt");
        String mac2 = getMac("Ciphertext and Keys/ciphertext.mac2.txt");
        PublicKey publicKey = getPublicKey("Ciphertext and Keys/lab1Sign.cert");
        byte[] macKey = Lab_1.decryptedKey2;
        String text = new String(Lab_1.decryptedCipherText);
        String calculatedMac = toHex(getCalculatedMac(text.getBytes(), macKey, "HmacMD5"));



    }
    // Lab 2 - Task 3
    public static String getMac(String file){
        try {
            return new BufferedReader(new FileReader(file)).readLine(); // The mac files contains only one line.
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Lab 2 - Task 4
    public static PublicKey getPublicKey(String file){
        try{
            FileInputStream publicKeyFile = new FileInputStream(file);
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            Certificate certificate = certificateFactory.generateCertificate(publicKeyFile);
            return certificate.getPublicKey(); // Sender's public key.

        }catch(FileNotFoundException | CertificateException e){
            throw new RuntimeException(e);
        }

    }

    // Lab 2 - Task 3
    public static byte[] getCalculatedMac(byte[] text, byte[] key, String cipher){
        try{
            Mac mac = Mac.getInstance(cipher);
            SecretKeySpec secretKey = new SecretKeySpec(key, cipher);
            mac.init(secretKey);
            return mac.doFinal(text);

        }catch(NoSuchAlgorithmException | InvalidKeyException e){
            throw new RuntimeException(e);
        }

    }


    public static String toHex(byte[] data){
        StringBuilder hex = new StringBuilder();

        for (byte aByte : data) {
            hex.append(String.format("%02X", aByte));
        }

        return hex.toString();
    }

}
