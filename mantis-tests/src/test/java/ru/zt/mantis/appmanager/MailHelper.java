package ru.zt.mantis.appmanager;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;
import ru.zt.mantis.model.MailMessage;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class MailHelper {
private ApplicationManager app;
private final Wiser wiser;

public MailHelper(ApplicationManager app) {
  this.app = app;
  /*Wiser-Почтовый сервер*/
  wiser = new Wiser();
}

//count- кол-во входящих писем, timeout - время ожидания
public List<MailMessage> waitForMail(int count, long timeout) throws MessagingException, IOException {
  //фиксация текущего времени
  long start = System.currentTimeMillis();
  //проверка времени: пока прошедшее время меньше текущего плюс
  // время ожидания выполнять:
  while (System.currentTimeMillis() < start + timeout) {
    /*Если кол-во почты больше или равно ожидаемому,то..*/
    if (wiser.getMessages().size() >= count) {
      /*Выход из метода. Превращаем список писем getMessages в поток stream(),
       к каждому пусьму приеняем функцию map((m) -> toModelMail(m),
        и собираем снова список collect(Collectors.toList()*/
      return wiser.getMessages().stream().map((m) -> toModelMail(m)).collect(Collectors.toList());
    }
    try {
      /*Ожидание в течение 1000 мс и возвращение в начало цикла*/
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
  /*Сообщение если почта не пришла, или ее не достаточно: No mail*/
  throw new Error("No mail :(");
}

/*Преобразование реальных писем в модельние MailMessage*/
public static MailMessage toModelMail(WiserMessage m) {
  try {
    /*Функция преобразования*/
    MimeMessage mm = m.getMimeMessage();
    return new MailMessage(mm.getAllRecipients()[0].toString(), (String) mm.getContent());
  } catch (MessagingException e) {
    /*перехват ошибки и вывод сообщения на консоль: null */
    e.printStackTrace();
    return null;
  } catch (IOException e) {
    e.printStackTrace();
    return null;
  }
}
/*Запускает Почтовый сервер Wiser*/
public void start() {
  wiser.start();
}
  /*Останавливает Почтовый сервер Wiser*/
public void stop() {
  wiser.stop();
}

}
