import controller.Controller;
import model.Messagerie;
import view.Fenetre;

public class Appli
{
  public static void main(String[] args)
  {
    Messagerie messagerie = new Messagerie();
    Fenetre fenetre = new Fenetre(messagerie);
    Controller.generateController(messagerie, fenetre);
  }
}