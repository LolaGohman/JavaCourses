package org.courses.lesson1;

public class SumOfDigitsInNumber {

    public static int sum(int number) {
        if (number <= 0) {
            return number;
        }
        String numberInStringFormat = String.valueOf(number);
        int sum = 0;
        char[] numberDigitsInCharFormat = numberInStringFormat.toCharArray();
        for (int i = 0; i < numberDigitsInCharFormat.length; i++) {
            sum = sum + Character.getNumericValue(numberDigitsInCharFormat[i]);
        }

        return sum;
    }


}
