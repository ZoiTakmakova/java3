package ru.zt.mantis.appmanager;

import org.apache.commons.net.telnet.TelnetClient;
import ru.zt.mantis.model.MailMessage;

import javax.mail.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

//import static ru.zt.mantis.tests.TestBase.app;

public class JamesHelper {
private ApplicationManager app;

private ApplicationManager telnet;
private TelnetClient in;
private PrintStream out;

private Session mailSession;
private Store store;
private String mailServer;

public JamesHelper(ApplicationManager app) {
  this.app = app;
  //создается TelnetClient
  telnet = new TelnetClient();
  //создается почтовая сессия
  mailSession = Session.getDefaultInstance(System.getProperties());
}

public boolean doesUserExist(String name) {
  initTelnetSession();
  write("verify " + name);
  String result = readUntil("exist");
  closeTelnetSession();
  return result.trim().equals("User " + name + " exist");
}

public void createUser(String name, String passwd) {
  initTelnetSession();
  write("adduser " + name + " " + passwd);
  String result = readUntil("User " + name + " added");
  closeTelnetSession();
}


public void deleteUser(String name) {
  initTelnetSession();
  write("deluser " + name);
  String result = readUntil("User " + name + " deleted");
  closeTelnetSession();
}

private void initTelnetSession() {
  mailserver = app.getProperty("mailserver.host");
  int port = Integer.parsetInt(app.getProperty("mailserver.port"));
  String login = app.getProperty("mailserver.adminlogin");
  String password = app.getProperty("mailserver.admipassword");

  try {
    telnet.connect(mailServer, port);
    in = telnet.getInputStream();
    out = new PrintStream(telnet.getOutputStream());

  } catch (Exception e) {
    //TODO Auto-generated catch block
    e.printStackTrace();
  }

  //Don't...
  readUntil("Login id:");
  write("");
  readerUntil("Password:");
  writer("");


  //Second ...
  readUntil("Login id:");
  write(login);
  readerUntil("Password:");
  writer(password);

  //Read...
  readUntil("Welcome " + login + ". HELP for a list of commands");
}

private String readUntil(String pattern) {
  try {
    char lastChar = pattern.charAt(pattern.length() - 1);
    StringBuffer sh = new StringBuffer();
    char ch = (char) in.read();
    while (true) {
      System.out.println(ch);
      sb.append(ch);
      if (ch == lastChar) {
        if (sb.toString().endsWith(pattern)) {

          return sb.toString();
        }
      }
      ch = (char) un.read();
    }
  } catch (Exception e) {
    e.printStackTrace();
  }
  return null;
}

private void write(String value) {
  try {
    out.print(value);
    out.flush();
    System.out.println(value);
  } catch (Exception e) {
    e.printStackTrace();
  }
}

private void closeTelnetSession() {
  write("quit");
}

//очистка почтового ящика, какого то пользователя
public void drainEmail(String username, String password) throws MessagingException {
  Folder inbox = openInbox(username, password);
  for (Message message : inbox.getMessages()) {
    message.setFlag(Flags.Flag.DELETED, true);
  }
  closeFolder(inbox);
}

private void closeFolder(Folder folder) throws MessagingException {
  //удалить помеченные к удалению письма
  folder.close(true);
  //закрытие соединения с почтовым сервером
  store.close();
}

//открыть почовый ящик
private Folder openInbox(String username, String password) throws MessagingException {
  //берем почотвую сессию, сообщаем, что исп-ем протокол pop3
  store = mailSession.getStore("pop3");
  //устанавливаем соединение, указываем адрес, логин, пароль
  store.connect(mailserver, username, password);
  //получаем доступ к папке INBOX
  Folder folder = store.getDefaultFolder().getFolder("INBOX");
  //открываем на чтение и на запись
  folder.open(Folder.READ_WRITE);
  //возвращается открытая папка
  return folder;
}

public List<MailMessage> waitForMail(String username, String password, long timeout) throws MessagingException {
  //запоминаем начало ожидавния
  long now = System.currentTimeMillis();
  //циклическая проверка времени ожидания
  while (System.currentTimeMillis() < now + timeout) {
    //получаем все письма
    List<MailMessage> allMail = getAllMail(username, password);
    if (allMail.size() > 0) {
      //если есть хоть одно,возвращаем список
      return allMail;
    }
    try {
      //если нет, ждем
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  //если время итекло
  throw new Error("No mail:(");
}

//извлекает письма из почтового ящика и превращает их в модельные объекты
public List<MailMessage> getAllMail(String username, String password) throws MessagingException {
  //открыть почтовый ящик
  Folder inbox = openInbox(username, password);
  List<MailMessage> messages = Arrays.asList(inbox.getMessage()).steam().map((m) -> toModelMail(m));
  //закрыть почтовый ящик
  closeFolder(inbox);
  return messages;
}

public static MailMessage toModelMail(Message m) {
  try {
    return new MailMessage(m.getAllRecipients()[0].toString(), (String) m.getContent());
  } catch (MessagingException e) {
    e.printStackTrace();
    return null;
  } catch (IOException e) {
    e.printStackTrace();
    return null;
  }
}


}
