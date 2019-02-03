package ru.zt.idsys.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {


public NavigationHelper(WebDriver wd) {
  super(wd);
}

  public void gotoEsiaPage() {
  click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Коммерческие начисления'])[1]/following::div[5]"));
  }
//переход в раздел ЕСИА-Справочники
  public void goToEsiaHdbkPage(){
  click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Удаление учетной записи'])[1]/following::span[1]"));
  }
  //переход в раздел ЕСИА-Справочники-Центры регистрации
  public void goToEsiaHdbkRegCentr(){
  click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Справочники'])[1]/following::span[1]"));
  }


/*
public void gotoGroupPage() {
  if (isElementPresent(By.tagName("h1"))
    && wd.findElement(By.tagName("h1")).getText().equals("Groups")
    && isElementPresent(By.name("new"))){
    return;
  }
  click(By.linkText("groups"));
}

public void gotoAddNewPage() {

  if (isElementPresent(By.tagName("h1"))
          && wd.findElement(By.tagName("h1")).getText().equals("Edit / add address book entry")){
    return;
  }
  click(By.linkText("add new"));
}

public void gotoHomePage() {
  if (isElementPresent(By.id("maintable"))){
    return;
  }
  click(By.linkText("home"));
}
*/
}
