package model;

import java.util.ArrayList;
import javax.crypto.SecretKey;
import security.AES;
import security.RSA;

public class Conversation
{
  private ArrayList<Message> messages;
  private ArrayList<Message> toSendMessages;
  private Utilisateur receiver;
  private SecretKey aesKey;

  public Conversation()
  {
    this.messages = new ArrayList();
    this.toSendMessages = new ArrayList();
  }

  public Conversation(Utilisateur utilisateur) {
    this();
    this.receiver = utilisateur;
    this.aesKey = AES.generateKey();
  }

  public Utilisateur getReceiver() {
    return this.receiver;
  }

  public void setReceiver(Utilisateur receiver) {
    this.receiver = receiver;
  }

  public synchronized ArrayList<Message> getToSendMessages() {
    while (this.toSendMessages.isEmpty()) {
      try {
        wait();
      }
      catch (InterruptedException ie) {
        ie.printStackTrace();
      }
    }
    return this.toSendMessages;
  }

  public void addMessage(Message message) {
    this.messages.add(message);
  }

  public synchronized void send(Message message) {
    this.toSendMessages.add(message);
    notify();
  }

  public String readMessages() {
    String result = "";
    for (int i = 0; i < this.messages.size(); i++) {
      result = result + ((Message)this.messages.get(i)).toString() + "\n";
    }
    return result;
  }

  public byte[] getCryptedAesKey() {
    return RSA.encryptSecretKey(this.aesKey, this.receiver.getPublicKey());
  }

  public void setAesKey(SecretKey sKey) {
    this.aesKey = sKey;
  }

  public SecretKey getAesKey() {
    return this.aesKey;
  }
}