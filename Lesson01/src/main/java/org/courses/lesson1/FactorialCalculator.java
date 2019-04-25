package org.courses.lesson1;

import java.util.Scanner;

public class FactorialCalculator {

    public static int calculateFactorialOf(int factorialCount) {
        FactorialCalculator calculator = new FactorialCalculator();
        int intFactorial = 0;

        if (factorialCount < 10 && factorialCount >= 0) {
            intFactorial = calculator.calculatingFactorial(factorialCount);
            System.out.println("Factorial of number " + factorialCount + "=" + intFactorial);
        }
        else if (factorialCount < 0) {
            System.out.println("Error!");
            System.exit(-1);
        }
        else {
            System.out.println("If you want to continue, type 1 ");
            Scanner a = new Scanner(System.in);
            int inputMessage = a.nextInt();

            if (inputMessage == 1) {
                intFactorial = calculateFactorialOf(factorialCount);
                System.out.println("Factorial of number " + factorialCount + "=" + intFactorial);
            } else {
                System.out.println("Error!");
                System.exit(-1);
            }
        }
        return intFactorial;
    }

    private int calculatingFactorial(int factorialCount){
        int factorial= 1;
        for(int i = 1; i<=factorialCount; i++){
            factorial = factorial*i;
        }
        return factorial;
    }
}



