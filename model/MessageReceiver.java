package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import security.AES;

public class MessageReceiver
  implements Runnable
{
  private ObjectInputStream in;
  private Conversation conversation;

  public MessageReceiver(ObjectInputStream in, Conversation conversation)
  {
    this.in = in;
    this.conversation = conversation;
  }

  public void run()
  {
    while (true)
      try {
        Message message = (Message)this.in.readObject();
        message.setTexte(AES.decrypt(message.getTexte(), this.conversation.getAesKey()));
        this.conversation.addMessage(message);
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
  }
}