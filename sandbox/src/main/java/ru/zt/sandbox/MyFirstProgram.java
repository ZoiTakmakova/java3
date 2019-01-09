package ru.zt.sandbox;

public class MyFirstProgram {

public static void main(String[] args) {
  hello("world");//вызов функции hello с агрументом world
  hello("user");//вызов функции hello с агрументом user
  hello("Zoi");//вызов функции hello с агрументом Zoi

//Объект класса Square
  Square s = new Square(5);
  //Вывод результата расчета Площади квадрата
  System.out.println("Площадь квадрата со стороной " + s.l + "=" + s.area());

//Объект класса Rectangle
  Rectangle r = new Rectangle(4, 6);
  //Вывод результата расчета Площади прямоугольника
  System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + r.area());

  //Объект класса Point точка p1
  Point p1 = new Point(1.0, 4.0);
  //Объект класса Point точка p2
  Point p2 = new Point(5.0, 7.0);
  //Вывод результата расчета Расстояния между точками p1 и p2
  System.out.println("Расстояние между точками p1 и p2 = " + p2.distance(p1));
}

//Объявление Функции hello с аргументом somebody
public static void hello(String somebody) {
  System.out.println("Hello, " + somebody + "!");
}

/*
//Объявление Функции distance с аргументами p1 и p2
public static double distance(Point p1, Point p2) {
  return Math.sqrt((Math.pow((p2.x - p1.x), 2)) + (Math.pow((p2.y - p1.y), 2)));
}
*/

}
