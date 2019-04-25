package org.courses.lesson1;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SumOfDigitsInNumberTest {

    @Test(dataProvider = "inputAndExpectedSum")
    public void shouldCalculateSumOfDigitsInNumber(int inputNumber, int resultSum) {
        int actualFactorialValue = SumOfDigitsInNumber.sum(inputNumber);

        Assert.assertEquals(actualFactorialValue, resultSum);
    }

    @DataProvider
    private Object[][] inputAndExpectedSum() {
        return new Object[][]{
                {-1, -1},
                {-23, -23},
                {0, 0},
                {12, 3},
                {569, 20},
                {1234567890, 45}
        };
    }

}
