package ru.zt.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;
//import org.testng.annotations.Test;

public class SquareTests {

//Аннотация для фреймворка
@Test

  //Тестовый метод testArea
public  void testArea(){
  //Создание нового квадрата
  Square s = new Square(5);
  //Вычисление и проверка площади квадрата
  Assert.assertEquals(s.area(),25.0);
}

@Test
//Тестовый метод testPoint1
public  void  testPoint1(){
  //Объект класса Point точка p1
  Point p1 = new Point(1.0, 4.0);
  //Объект класса Point точка p2
  Point p2 = new Point(5.0, 7.0);
  //Вычисление и проверка растояние между точками p1 и p2
  Assert.assertEquals(p2.distance(p1),5.0);
}

@Test(enabled = false)
//Тестовый метод testPoint2
public  void  testPoint(){
  //Объект класса Point точка p1
  Point p1 = new Point(2.0, 4.0);
  //Объект класса Point точка p2
  Point p2 = new Point(5.0, 7.0);
  //Вычисление и проверка растояние между точками p1 и p2
  Assert.assertEquals(p2.distance(p1),4.2);
}
}
