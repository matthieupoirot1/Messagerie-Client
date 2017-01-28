package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.security.PublicKey;
import java.util.ArrayList;
import security.RSA;

public class Messagerie
{
  private ArrayList<Conversation> conversations;
  private ArrayList<Utilisateur> destinataires;
  private Utilisateur utilisateur;

  public Messagerie()
  {
    this.conversations = new ArrayList();
    this.destinataires = new ArrayList();
    waitForConnectionOnPort(2042);
  }

  private void waitForConnectionOnPort(int port) {
    new Thread(new PortListener(port, this)).start();
  }

  public void connectTo(Utilisateur destinataire) {
    try {
      Socket socket = new Socket(destinataire.getIpAdrr(), 2042);
      System.out.println(destinataire);
      Conversation conversation = new Conversation(destinataire);
      conversation.addMessage(new Message("System", "Connexion réussie !"));
      this.conversations.add(conversation);

      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
      out.flush();
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

      out.writeObject(new Utilisateur(this.utilisateur));
      out.flush();

      PublicKey publicKeyDestinataire = null;
      try {
        publicKeyDestinataire = (PublicKey)in.readObject();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
      if (publicKeyDestinataire == null) {
        System.exit(-1);
      }

      out.writeObject(RSA.encryptSecretKey(conversation.getAesKey(), publicKeyDestinataire));

      new Thread(new MessageReceiver(in, conversation)).start();
      new Thread(new MessageSender(out, conversation)).start();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void connectToServer(String ip) {
    try {
      Socket socket = new Socket(ip, 2043);

      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
      out.flush();
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
      out.writeObject(new Utilisateur(this.utilisateur));

      new Thread(new UserListReceiver(in, this)).start();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void addConversation(Conversation conversation)
  {
    this.conversations.add(conversation);
  }

  public ArrayList<Conversation> getConversations() {
    return this.conversations;
  }

  public ArrayList<Utilisateur> getDestinataires() {
    return this.destinataires;
  }

  public Utilisateur getUtilisateur() {
    return this.utilisateur;
  }

  public void setDestinataires(ArrayList<Utilisateur> utilisateurs) {
    this.destinataires = utilisateurs;
  }

  public void leave() {
    System.exit(0);
  }

  public void setUtilisateur(Utilisateur utilisateur) {
    this.utilisateur = utilisateur;
  }
}