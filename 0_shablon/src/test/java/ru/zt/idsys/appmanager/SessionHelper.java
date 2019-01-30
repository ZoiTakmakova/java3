package ru.zt.idsys.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.zt.idsys.appmanager.HelperBase;

public class SessionHelper extends HelperBase {
private WebDriver wd;

public SessionHelper(WebDriver wd) {
  super(wd);
 }

public void login(String username, String password) {
  type(By.id("LOGIN-USERNAME"),username);
  type(By.id("LOGIN-PASSWORD"),password);

  click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Запомнить пароль'])[1]/following::span[1]"));

}
}
