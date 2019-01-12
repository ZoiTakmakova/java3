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

public void finish(String cofirmationLink, String password) {
  /*проходим по ссылке*/
  wd.get(cofirmationLink);
  type(By.name("password"), password);
  type(By.name("password_confirm"), password);
  click(By.cssSelector("input[value='Update User']"));
}
}
