package ru.zt.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.zt.mantis.model.MailMessage;
import ru.zt.mantis.model.UserData;
import ru.zt.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;


public class ChangePasswordTests extends TestBase{

    /*Предусловие: Запуск почтового сервера Wiser*/
    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }


    @Test
    public void testChangePassword() throws IOException, MessagingException {
                                         /*Вход Администратора в систему*/
       String user = "administrator";
       String password = "root";
       app.registration().loging(user, password);

                               /*перейти на страницу управления пользователями*/
       app.changePassword().pageManageUsers();
                                         /*выбрать заданного пользователя*/
         /*Однако получить информацию об идентификаторе и/или логине пользователя тесты должны самостоятельно
     во время выполнения,например, загрузив информацию о пользователях из базы данных.*/
       // app.changePassword().selectUser();
        //получаем из БД все группы
        Users users = app.db().users();
        UserData changedUser = users.iterator().next();
        int changedUserId = changedUser.getId();
        String changedUserEmail = changedUser.getEmail();
        //выбрать пользователя
        app.changePassword().selectUser(changedUserId);
                                     /*Отправляется письмо на адрес пользователя*/
        app.changePassword().submitResetPassword();
        //Встроенный почтовый сервер
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);

        /*Помещение результата регулярного выражения в переменную cofirmationLink*/
        String cofirmationLink = findConfirmationLink(mailMessages, changedUserEmail);

        //создаем новый пароль
        long now = System.currentTimeMillis();
        String newPassword = String.format("password%s", now);
        app.changePassword().resetPassword(cofirmationLink, newPassword);//changePassword
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

