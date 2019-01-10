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
  /*Предусловие: Запуск почтового сервера Wiser*/
@BeforeMethod

public void startMailServer() {
  app.mail().start();
}

@Test
public void testRegistration() throws IOException, MessagingException {
  /*функция возвращающая текущее время*/
  long now = System.currentTimeMillis();
  /*добавление логину значения текущего времени*/
  String user = String.format("user%s", now);
  String password = "password";
  /*добавление почте значения текущего времени*/
  String email = String.format("user%s@local.localdomain", now);
  app.registration().start(user, email);
  /*Встроенный почтовый сервер*/
  List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
  /*Помещение результата регулярного выражения в переменную cofirmationLink*/
  String cofirmationLink = findConfirmationLink(mailMessages, email);
  app.registration().finish(cofirmationLink, password);
  /*Проерка того, что пользователь залогинился*/
  assertTrue(app.newSession().login(user, password));
}

/*в функцию передается список писем mailMessages и адрес электронной почты email,
* берем из них первое findFirst().get()*/
private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
  /*в результате из потока mailMessages.stream() остануться те сообщения которые пришли на нужнвы адресс
  * filter((m) -> m.to.equals(email)), далее возьмем первый из них findFirst().get()*/
  MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
  /*Построение регулярного ваыражения*/
  VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
  /*возвращает получившееся значение*/
  return regex.getText(mailMessage.text);
}
  /*Постдусловие: Остановка почтового сервера Wiser*/
@AfterMethod(alwaysRun = true)
public void stopMailServer() {
  app.mail().stop();
}

}
