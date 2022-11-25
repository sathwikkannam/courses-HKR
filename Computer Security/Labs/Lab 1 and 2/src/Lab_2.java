import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

public class Lab_2 {
    public static void main(String[] args){
        Lab_1.main(args);

        boolean isSignature1;
        boolean isSignature2;
        byte[] text = Lab_1.decryptedCipherText;
        byte[] macKey = Lab_1.decryptRSA(Lab_1.privateKey, Arrays.copyOfRange(Lab_1.getFileData("Ciphertext and Keys/ciphertext.enc"), 256, 384));
        byte[] signature1 = Lab_1.getFileData("Ciphertext and Keys/ciphertext.enc.sig1");
        byte[] signature2 = Lab_1.getFileData("Ciphertext and Keys/ciphertext.enc.sig2");

        PublicKey publicKey = getPublicKey("Ciphertext and Keys/lab1Sign.cert");

        String mac1 = getMac("Ciphertext and Keys/ciphertext.mac1.txt");
        String mac2 = getMac("Ciphertext and Keys/ciphertext.mac2.txt");
        String calculatedMac = toHex(getCalculatedMac(text, macKey, "HmacMD5"));

        System.out.printf("\nMAC:\n------------------------\nmac1: %s\nmac2: %s\n\n", mac1, mac2);
        if(calculatedMac.equalsIgnoreCase(mac1)){
            System.out.printf("Calculated mac: %s matches -> mac1: %s", calculatedMac, mac1);
        }else if(calculatedMac.equalsIgnoreCase(mac2)){
            System.out.printf("Calculated mac: %s matches -> mac2: %s", calculatedMac, mac2);
        }
        System.out.println("\n------------------------\n");

        isSignature1 = verifySignature("SHA1withRSA", publicKey, text, signature1);
        isSignature2 = verifySignature("SHA1withRSA", publicKey, text, signature2);

        System.out.printf("Signatures:\n------------------------\nsignature1: %b\nsignature2: %b\n------------------------", isSignature1, isSignature2);


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

    // Lab 2 - Task 4
    public static boolean verifySignature(String cipher, PublicKey publicKey, byte[] text, byte[] fileSignature){
        try{
            Signature signature = Signature.getInstance(cipher);
            signature.initVerify(publicKey);
            signature.update(text);
            return signature.verify(fileSignature);

        }catch(InvalidKeyException | NoSuchAlgorithmException | SignatureException e){
            throw new RuntimeException(e);
        }

    }

}
