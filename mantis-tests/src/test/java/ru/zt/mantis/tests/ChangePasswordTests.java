package ru.zt.mantis.tests;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.zt.mantis.appmanager.HttpSession;

import java.io.IOException;


public class ChangePasswordTests extends TestBase{

    /*Предусловие: Запуск почтового сервера Wiser*/
    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }


    @Test
    public void testChangePassword() throws IOException {

                                         /*Администратор входит в систему*/

       String user = "administrator";
       String password = "root";
       app.loginOn().loging(user, password);

                               /*переходит на страницу управления пользователями*/
       app.change().changeAccount();





        /*выбирает заданного пользователя и нажимает кнопку Reset Password*/




        /*Отправляется письмо на адрес пользователя*/


        /*тесты должны получить это письмо, извлечь из него ссылку для смены пароля*/


        /*пройти по этой ссылке */



        /*изменить пароль*/



        /*Затем тесты должны проверить, что пользователь может войти в систему с новым паролем*/


    }



    /*Постдусловие: Остановка почтового сервера Wiser*/
    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}

