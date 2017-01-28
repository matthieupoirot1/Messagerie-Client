package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JList;
import model.Messagerie;
import model.Utilisateur;
import view.Fenetre;

public class ControllerListUser extends Controller
  implements MouseListener
{
  public ControllerListUser(Messagerie messagerie, Fenetre fenetre)
  {
    super(messagerie, fenetre);
    fenetre.setControllerListUser(this);
  }

  public void mouseClicked(MouseEvent mouseEvent)
  {
    JList list = (JList)mouseEvent.getSource();
    if (mouseEvent.getClickCount() == 2) {
      int i = list.locationToIndex(mouseEvent.getPoint());
      this.messagerie.connectTo((Utilisateur)this.messagerie.getDestinataires().get(i));
    }
  }

  public void mousePressed(MouseEvent mouseEvent)
  {
  }

  public void mouseReleased(MouseEvent mouseEvent)
  {
  }

  public void mouseEntered(MouseEvent mouseEvent)
  {
  }

  public void mouseExited(MouseEvent mouseEvent)
  {
  }
}