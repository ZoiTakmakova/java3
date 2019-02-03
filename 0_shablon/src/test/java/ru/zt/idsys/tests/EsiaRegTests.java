package ru.zt.idsys.tests;


import org.testng.annotations.Test;

public class EsiaRegTests extends TestBase{

    @Test
    public void testEsia() {
        app.getNavigationHelper().gotoEsiaPage();//здесь нужно подождать время необходимое на подключение к стенду
        app.getNavigationHelper().goToEsiaHdbkPage();
        app.getNavigationHelper().goToEsiaHdbkRegCentr();
        app.getEsiaCentRegHelper().newRegCentr();
        //app.getEsiaCentRegHelper().IdCentReg();

    }

    }


