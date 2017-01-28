package controller;

import model.Messagerie;
import view.Fenetre;

public abstract class Controller
{
  protected Messagerie messagerie;
  protected Fenetre fenetre;

  public Controller(Messagerie messagerie, Fenetre fenetre)
  {
    this.messagerie = messagerie;
    this.fenetre = fenetre;
  }

  public static void generateController(Messagerie messagerie, Fenetre fenetre) {
    new ControllerBarMenu(messagerie, fenetre);
    new ControllerMessageSender(messagerie, fenetre);
    new ControllerTimer(messagerie, fenetre);
    new ControllerListUser(messagerie, fenetre);
  }
}