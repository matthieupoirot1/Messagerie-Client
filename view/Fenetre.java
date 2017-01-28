package view;

import controller.ControllerBarMenu;
import controller.ControllerListUser;
import controller.ControllerMessageSender;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import model.Conversation;
import model.Messagerie;
import model.Utilisateur;

public class Fenetre extends JFrame
{
  private Messagerie messagerie;
  private JMenuBar menuBar;
  private JMenu fichier;
  private JMenuItem connexion;
  private JMenuItem quitter;
  private JPanel global;
  private JPanel listeUser;
  private JPanel chat;
  private JPanel listeMessage;
  private JPanel sender;
  private JTextField jtf_message;
  private JTextArea jta_listeMessage;
  private JButton envoyer;
  private JScrollPane scrollMessage;
  private JScrollPane scrollUser;
  private JTabbedPane onglets = new JTabbedPane(1);
  private JList liste;
  private DefaultListModel modelList;

  public Fenetre(Messagerie messagerie)
  {
    this.messagerie = messagerie;
    init();
  }

  private void init() {
    this.global = new JPanel();
    this.global.setLayout(new BoxLayout(this.global, 0));
    initMenuBar();
    initChat();
    initListeUser();
    setJMenuBar(this.menuBar);
    setContentPane(this.global);
    pack();
    setTitle("Messagerie");
    setResizable(false);
    setDefaultCloseOperation(3);
    setLocationRelativeTo(null);
    setVisible(true);
  }

  private void initMenuBar()
  {
    this.fichier = new JMenu("Fichier");
    this.connexion = new JMenuItem("Connexion");
    this.connexion.setActionCommand("connect");
    this.quitter = new JMenuItem("Quitter");
    this.quitter.setActionCommand("leave");
    this.fichier.add(this.connexion);
    this.fichier.add(this.quitter);
    this.menuBar = new JMenuBar();
    this.menuBar.add(this.fichier);
  }

  private void initChat()
  {
    this.listeMessage = new JPanel();
    this.sender = new JPanel();
    this.sender.setPreferredSize(new Dimension(400, 50));
    this.jta_listeMessage = new JTextArea("En attente de message...");
    this.jta_listeMessage.setEditable(false);
    DefaultCaret caret = (DefaultCaret)this.jta_listeMessage.getCaret();
    caret.setUpdatePolicy(0);
    this.scrollMessage = new JScrollPane(this.jta_listeMessage);
    this.scrollMessage.setPreferredSize(new Dimension(400, 200));
    this.scrollMessage.setVerticalScrollBarPolicy(22);
    this.listeMessage.add(this.scrollMessage);
    this.onglets.addTab("Messages", this.listeMessage);
    this.jtf_message = new JTextField();
    this.jtf_message.setPreferredSize(new Dimension(200, 20));
    this.envoyer = new JButton("Envoyer");
    this.envoyer.setActionCommand("send");
    this.sender.add(this.jtf_message);
    this.sender.add(this.envoyer);
    this.chat = new JPanel();
    this.chat.setLayout(new BoxLayout(this.chat, 1));
    this.chat.add(this.onglets);
    this.chat.add(this.sender);
    this.global.add(this.chat);
  }

  private void initListeUser() {
    this.listeUser = new JPanel();
    this.modelList = new DefaultListModel();
    this.liste = new JList(this.modelList);
    this.modelList.addElement("aucune connexion...");
    this.scrollUser = new JScrollPane(this.liste);
    this.scrollUser.setPreferredSize(new Dimension(150, 220));
    this.scrollUser.setVerticalScrollBarPolicy(22);
    this.listeUser.add(this.scrollUser);
    this.listeUser.setBorder(new EmptyBorder(15, 0, 0, 0));
    this.global.add(this.listeUser);
  }

  public void setControllerBarMenu(ControllerBarMenu controllerBarMenu) {
    this.connexion.addActionListener(controllerBarMenu);
    this.quitter.addActionListener(controllerBarMenu);
  }

  public void setControllerMessageSender(ControllerMessageSender controllerMessageSender) {
    this.envoyer.addActionListener(controllerMessageSender);
  }

  public void setControllerListUser(ControllerListUser controllerListUser) {
    this.liste.addMouseListener(controllerListUser);
  }

  public String getTextToSend() {
    return this.jtf_message.getText();
  }

  public void clearMessageField() {
    this.jtf_message.setText("");
  }

  public int getSelectedTab() {
    return this.onglets.getSelectedIndex();
  }

  public void repaint()
  {
    updateTab();
    updateUserList();
  }

  private void updateTab() {
    if (!this.messagerie.getConversations().isEmpty())
    {
      int selectedTab = this.onglets.getSelectedIndex();
      this.onglets.removeAll();

      for (int i = 0; i < this.messagerie.getConversations().size(); i++) {
        if (selectedTab == i) {
          this.onglets.addTab(((Conversation)this.messagerie.getConversations().get(i)).getReceiver().getLogin(), this.listeMessage);
          this.onglets.setSelectedIndex(i);
        } else {
          this.onglets.addTab(((Conversation)this.messagerie.getConversations().get(i)).getReceiver().getLogin(), null);
        }
      }
      this.jta_listeMessage.setText(((Conversation)this.messagerie.getConversations().get(this.onglets.getSelectedIndex())).readMessages());
    }
  }

  public void updateUserList() {
    ArrayList destinataires = this.messagerie.getDestinataires();
    if (!destinataires.isEmpty())
      if (destinataires.size() >= this.modelList.size()) {
        for (int i = 0; i < destinataires.size(); i++) {
          if (i >= this.modelList.size())
            this.modelList.addElement(destinataires.get(i));
          else if (!this.modelList.getElementAt(i).equals(destinataires.get(i))) {
            this.modelList.setElementAt(destinataires.get(i), i);
          }
        }
      }
      else
        for (int i = destinataires.size(); i < this.modelList.size(); i++)
          this.modelList.removeElementAt(i);
  }
}