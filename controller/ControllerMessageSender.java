package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.Conversation;
import model.Message;
import model.Messagerie;
import model.Utilisateur;
import view.Fenetre;

public class ControllerMessageSender extends Controller
  implements ActionListener
{
  public ControllerMessageSender(Messagerie messagerie, Fenetre fenetre)
  {
    super(messagerie, fenetre);
    fenetre.setControllerMessageSender(this);
  }

  public void actionPerformed(ActionEvent actionEvent)
  {
    switch (actionEvent.getActionCommand())
    {
    case "send":
      ((Conversation)this.messagerie.getConversations().get(this.fenetre.getSelectedTab())).send(new Message(this.messagerie.getUtilisateur().getLogin(), this.fenetre.getTextToSend()));
      this.fenetre.clearMessageField();
    }
  }
}