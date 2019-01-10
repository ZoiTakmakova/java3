package ru.zt.mantis.appmanager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

public RegistrationHelper(ApplicationManager app) {
  super(app);
}

public void start(String username, String email) {
  wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
  type(By.name("username"), username);
  type(By.name("email"), email);
  click(By.cssSelector("input[value='Signup']"));
}

/*Вход пользователя*/
  public void loging(String username, String password) {
    //переход на страницу входа
    wd.get(app.getProperty("web.baseUrl"));
    //ввод имени
    type(By.name("username"), username);
    //ввод пароля
    type(By.name("password"), password);
    //нажать кнопку Login
    click(By.cssSelector("input[value='Login']"));
  }

  /*переходит на страницу управления пользователями*/
  public void changeAccount() {
    pageManageUsers();

  }


  //переходит в раздел Manage Users
  public void pageManageUsers() {   click(By.cssSelector("span[class ='bracket-link"));
  }
  //ввести пароль

  // <input type="password" size="32" maxlength="1024" name="password">

  //подтвердить пароль
  //<input type="password" size="32" maxlength="1024" name="password_confirm">

  //нажать кнопку Update User
  // <input type="submit" class="button" value="Update User">


public void finish(String cofirmationLink, String password) {
  /*проходим по ссылке*/
  wd.get(cofirmationLink);
  type(By.name("password"), password);
  type(By.name("password_confirm"), password);
  click(By.cssSelector("input[value='Update User']"));
}
}
