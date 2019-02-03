package ru.zt.idsys.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.zt.idsys.model.CentRegData;

public class EsiaCentRegHelper extends HelperBase {


public EsiaCentRegHelper(WebDriver wd) {
        super(wd);
    }
//кнп "Создать" новый центр регистрации
    public void newRegCentr(){
        click(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Список [Центры регистрации]'])[1]/following::span[3]"));
    }
//выбрать поле "Идентификатор центра регистрации"
/*    public void IdCentReg(){
        click(By.id("ESIA_REGISTRATION_CENTERS.ID*"));
    }*/
//By.id("ESIA_REGISTRATION_CENTERS.ID1011779865")
// заполнить поле "Идентификатор центра регистрации"

//кнп "Сохранить"
// driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='Сохранить'])[1]/following::span[2]")).click();

    CentRegData centReg = new CentRegData()
            .withIdCentReg(5).withNameCentReg("5555name").withDescription("555description");
}
