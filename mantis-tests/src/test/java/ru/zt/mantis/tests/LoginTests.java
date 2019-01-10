package ru.zt.mantis.tests;

import org.testng.annotations.Test;
import ru.zt.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.assertTrue;

public class LoginTests extends  TestBase{

  @Test
public void testLogin() throws IOException {

    //создание новой сессии  HttpSession
    HttpSession session = app.newSession();
    //проверка того, что пользователь залогинился
    assertTrue(session.login("administrator","root"));
     //после входа на струнице появился нужный текст
    assertTrue(session.isLoggedInAs("administrator"));
  }
}
