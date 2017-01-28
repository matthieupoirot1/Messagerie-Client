package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class UserListReceiver
  implements Runnable
{
  private ObjectInputStream in;
  private Messagerie messagerie;

  public UserListReceiver(ObjectInputStream in, Messagerie messagerie)
  {
    this.in = in;
    this.messagerie = messagerie;
  }

  public void run()
  {
    while (true)
      try {
        this.messagerie.setDestinataires((ArrayList)this.in.readObject());
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      }
  }
}