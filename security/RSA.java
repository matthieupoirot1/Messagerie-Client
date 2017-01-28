package security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class RSA
{
  public static KeyPair generateKeyPair()
  {
    KeyPairGenerator keyPairGenerator = null;
    try {
      keyPairGenerator = KeyPairGenerator.getInstance("RSA");
      keyPairGenerator.initialize(2048);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return keyPairGenerator.generateKeyPair();
  }

  public static byte[] encrypt(String text, PublicKey key)
  {
    byte[] cipherText = null;
    try
    {
      Cipher cipher = Cipher.getInstance("RSA");

      cipher.init(1, key);
      cipherText = cipher.doFinal(text.getBytes());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return cipherText;
  }

  public static String decrypt(byte[] text, PrivateKey key)
  {
    byte[] dectyptedText = null;
    try
    {
      Cipher cipher = Cipher.getInstance("RSA");

      cipher.init(2, key);
      dectyptedText = cipher.doFinal(text);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }

    return new String(dectyptedText);
  }

  public void SaveKeyPair(String path, KeyPair keyPair) throws IOException {
    PrivateKey privateKey = keyPair.getPrivate();
    PublicKey publicKey = keyPair.getPublic();

    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey
      .getEncoded());
    FileOutputStream fos = new FileOutputStream(path + "/public.key");
    fos.write(x509EncodedKeySpec.getEncoded());
    fos.close();

    PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey
      .getEncoded());
    fos = new FileOutputStream(path + "/private.key");
    fos.write(pkcs8EncodedKeySpec.getEncoded());
    fos.close();
  }

  public KeyPair LoadKeyPair(String path, String algorithm)
    throws IOException, NoSuchAlgorithmException, InvalidKeySpecException
  {
    File filePublicKey = new File(path + "/public.key");
    FileInputStream fis = new FileInputStream(path + "/public.key");
    byte[] encodedPublicKey = new byte[(int)filePublicKey.length()];
    fis.read(encodedPublicKey);
    fis.close();

    File filePrivateKey = new File(path + "/private.key");
    fis = new FileInputStream(path + "/private.key");
    byte[] encodedPrivateKey = new byte[(int)filePrivateKey.length()];
    fis.read(encodedPrivateKey);
    fis.close();

    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);

    PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

    PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);

    PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

    return new KeyPair(publicKey, privateKey);
  }

  public static byte[] encryptSecretKey(SecretKey skey, PublicKey pkey)
  {
    Cipher cipher = null;
    byte[] key = null;
    try
    {
      cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      cipher.init(1, pkey);
      key = cipher.doFinal(skey.getEncoded());
    }
    catch (Exception e)
    {
      System.out.println("exception encoding key: " + e.getMessage());
      e.printStackTrace();
    }
    return key;
  }

  public static SecretKey decryptAESKey(byte[] data, PrivateKey privKey)
  {
    SecretKey key = null;
    Cipher cipher = null;
    try
    {
      cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
      cipher.init(2, privKey);

      key = new SecretKeySpec(cipher.doFinal(data), "AES");
    }
    catch (Exception e)
    {
      System.out.println("exception decrypting the aes key: " + e
        .getMessage());
      return null;
    }

    return key;
  }
}