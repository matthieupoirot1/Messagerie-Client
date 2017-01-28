package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import javax.crypto.SecretKey;
import security.RSA;

public class PortListener
  implements Runnable
{
  private ServerSocket serverSocket;
  private Socket socket;
  private Messagerie messagerie;

  public PortListener(int port, Messagerie messagerie)
  {
    try
    {
      this.serverSocket = new ServerSocket(port);
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.messagerie = messagerie;
  }

  public void run()
  {
    while (true)
      try {
        this.socket = this.serverSocket.accept();
        Conversation conversation = new Conversation();
        conversation.addMessage(new Message("System", "Un utilisateur vient de se connecter"));

        ObjectOutputStream out = new ObjectOutputStream(this.socket.getOutputStream());
        out.flush();
        ObjectInputStream in = new ObjectInputStream(this.socket.getInputStream());
        try
        {
          conversation.setReceiver((Utilisateur)in.readObject());

          out.writeObject(this.messagerie.getUtilisateur().getPublicKey());
          out.flush();

          SecretKey secretKey = RSA.decryptAESKey((byte[])in.readObject(), this.messagerie.getUtilisateur().getPrivateKey());
          conversation.setAesKey(secretKey);
        }
        catch (ClassNotFoundException e) {
          e.printStackTrace();
        }
        this.messagerie.addConversation(conversation);

        new Thread(new MessageReceiver(in, conversation)).start();
        new Thread(new MessageSender(out, conversation)).start();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
  }
}