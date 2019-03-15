package ru.zt.idsys.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ZhipHelper extends HelperBase {

    public ZhipHelper(WebDriver wd) {
        super(wd);
    }

    //переход в раздел Исполнительное производство
    public void goToSectionIP() {
        click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Постановления'])[1]/following::div[5]"));
    }
    //переход в подраздел Исполнительное производство
    public void goToSubsectionIP() {
        click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Исполнительные производства'])[1]/following::span[1]"));
    //создание нового ИП
        click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Исполнительные производства'])[3]/following::img[2]"));
     //выбрать поле 'Номер исполнительного документа'
        click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Номер исполнительного документа'])[1]/preceding::input[2]"));
     //заполнить поле 'Номер исполнительного документа'
         wd.findElement(By.xpath("//div[2]/div/div[2]/div/div[3]/div/div/input")).sendKeys("4415/19/69025-ИП");

    }
}