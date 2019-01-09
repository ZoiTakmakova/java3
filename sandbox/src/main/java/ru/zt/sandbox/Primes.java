package ru.zt.sandbox;

public class Primes {


public static boolean isPrime(int n) {
  for (int i = 2; i < n; i++) {
    if (n % i == 0) {
      return false; //число n НЕ простое
    }
  }
  return true;//число n  простое
}

  public static boolean isPrimeWhile(int n){
    int i=2;
    while (i<n && n % i != 0){
      i++;
    }
    return i==n;
  }

public static boolean isPrime(long n) {
  for (long i = 2; i < n; i++) {
    if (n % i == 0) {
      return false; //число n НЕ простое
    }
  }
  return true;//число n  простое
}

public static boolean isPrimeFast(int n) {
  int m = (int)Math.sqrt(n);// явное преобразование математического кореня в целое число
  for (int i = 2; i < m; i++) {
    if (n % i == 0) {
      return false; //число n НЕ простое
    }
  }
  return true;//число n  простое
}
}
