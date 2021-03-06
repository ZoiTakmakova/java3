package ru.zt.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.zt.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

//@BeforeMethod
public void startMailServer() {
  app.mail().start();
}


@Test
public void testRegistration() throws IOException, MessagingException {
  long now = System.currentTimeMillis();
  String user = String.format("user%s", now);
  String password = "password";
  String email = String.format("user%s@local.localdomain", now);
  //создание пользователя неа почтовом сервере
  app.james().createUser(user, password);
  app.registration().start(user, email);

  //внутр.почтовый сервер
  //List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
  //внеш.почтовый сервер
  List<MailMessage> mailMessages = app.james().waitForMail(user, password, 60000);
  String cofirmationLink = findConfirmationLink(mailMessages, email);
  app.registration().finish(cofirmationLink, password);
  assertTrue(app.newSession().login(user, password));
}

private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
  MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
  VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
  return regex.getText(mailMessage.text);
}

//@AfterMethod(alwaysRun = true)
public void stopMailServer() {
  app.mail().stop();
}

}
