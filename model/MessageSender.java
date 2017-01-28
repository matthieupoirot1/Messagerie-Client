package model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import security.AES;

public class MessageSender
  implements Runnable
{
  private ObjectOutputStream out;
  private Conversation conversation;

  public MessageSender(ObjectOutputStream out, Conversation conversation)
  {
    this.out = out;
    this.conversation = conversation;
  }

  public void run()
  {
    while (true) {
      for (int i = 0; i < this.conversation.getToSendMessages().size(); i++) {
        try {
          Message message = (Message)this.conversation.getToSendMessages().get(i);
          System.out.println(message.getTexte());
          message.setTexte(AES.encrypt(message.getTexte(), this.conversation.getAesKey()));
          this.out.writeObject(message);
          this.out.flush();
          message.setTexte(AES.decrypt(message.getTexte(), this.conversation.getAesKey()));
          this.conversation.addMessage((Message)this.conversation.getToSendMessages().get(i));
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
      this.conversation.getToSendMessages().clear();
    }
  }
}