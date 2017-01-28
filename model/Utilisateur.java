package model;

import java.io.Serializable;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import security.RSA;

public class Utilisateur
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String login;
  private String ipAdrr;
  private PublicKey publicKey;
  private PrivateKey privateKey;

  public Utilisateur(String login)
  {
    this.login = login;
    KeyPair kp = RSA.generateKeyPair();

    this.publicKey = kp.getPublic();
    this.privateKey = kp.getPrivate();
  }

  public Utilisateur(Utilisateur u) {
    this.login = u.getLogin();
    this.ipAdrr = u.getIpAdrr();
    this.publicKey = u.getPublicKey();
    this.privateKey = null;
  }

  public String getLogin() {
    return this.login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getIpAdrr() {
    return this.ipAdrr;
  }

  public void setIpAdrr(String ipAdrr) {
    this.ipAdrr = ipAdrr;
  }

  public PublicKey getPublicKey() {
    return this.publicKey;
  }

  public String toString() {
    return this.login;
  }

  public PrivateKey getPrivateKey() {
    return this.privateKey;
  }
}