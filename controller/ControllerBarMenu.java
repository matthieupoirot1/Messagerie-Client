package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Messagerie;
import model.Utilisateur;
import view.Fenetre;

public class ControllerBarMenu extends Controller
  implements ActionListener
{
  public ControllerBarMenu(Messagerie messagerie, Fenetre fenetre)
  {
    super(messagerie, fenetre);
    fenetre.setControllerBarMenu(this);
  }

  public void actionPerformed(ActionEvent actionEvent)
  {
    switch (actionEvent.getActionCommand()) {
    case "leave":
      this.messagerie.leave();
      break;
    case "connect":
      String login = JOptionPane.showInputDialog(null, "Login :", "Connexion", 3);
      this.messagerie.setUtilisateur(new Utilisateur(login));
      String ip = JOptionPane.showInputDialog(null, "IP du Serveur :", "Connexion", 3);
      this.messagerie.connectToServer(ip);
    }
  }
}