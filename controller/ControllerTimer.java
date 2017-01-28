package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import model.Messagerie;
import view.Fenetre;

public class ControllerTimer extends Controller
  implements ActionListener
{
  public ControllerTimer(Messagerie messagerie, Fenetre fenetre)
  {
    super(messagerie, fenetre);
    Timer timer = new Timer(50, this);
    timer.start();
  }

  public void actionPerformed(ActionEvent actionEvent)
  {
    this.fenetre.repaint();
  }
}