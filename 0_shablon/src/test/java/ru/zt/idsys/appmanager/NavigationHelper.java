package ru.zt.idsys.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.zt.idsys.appmanager.HelperBase;

public class NavigationHelper extends HelperBase {


public NavigationHelper(WebDriver wd) {
  super(wd);
}

  public void gotoEsiaRegPage() {
  click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Коммерческие начисления'])[1]/following::div[5]"));
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
