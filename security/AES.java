package security;

import java.io.PrintStream;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class AES
{
  public static SecretKey generateKey()
  {
    KeyGenerator keyGenerator = null;
    try {
      keyGenerator = KeyGenerator.getInstance("AES");
      keyGenerator.init(128);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return keyGenerator.generateKey();
  }

  public static String encrypt(String strToEncrypt, SecretKey key)
  {
    try
    {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      cipher.init(1, key);
      return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
    }
    catch (Exception e)
    {
      System.out.println("Error while encrypting: " + e.toString());
    }
    return null;
  }

  public static String decrypt(String strToDecrypt, SecretKey key)
  {
    try
    {
      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
      cipher.init(2, key);
      return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
    }
    catch (Exception e)
    {
      System.out.println("Error while decrypting: " + e.toString());
    }
    return null;
  }
}