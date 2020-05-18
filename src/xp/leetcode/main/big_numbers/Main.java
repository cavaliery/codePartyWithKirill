package xp.leetcode.main.big_numbers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final String SOURCE_PCKG = "src/xp/leetcode/main/big_numbers/source/";

    private static final String FILE_IN = SOURCE_PCKG + "numbers.txt";

    private static final String FILE_OUT = SOURCE_PCKG + "summ.txt";

    public static void main(String[] args) {
        File fileIn = new File(FILE_IN);
        File fileOut = new File(FILE_OUT);
        try {
            List<BigNumber> numbers = BigNumber.getNumberFromFile128(fileIn);
            BigNumber num1 = numbers.get(0);
            BigNumber num2 = numbers.get(1);
            BigNumber sum1 = BigNumber.sum(num1, num2);
            BigNumber sum2 = num1.add(num2);
            BigNumber mult = BigNumber.multiply(num1,num2);
            BigNumber.writeNumberToFile128(sum1, fileOut);
            System.out.println(num1 + " + " + num2 + " = " + sum1);
            System.out.println(num1.getNumber10From128() + " + "
                    + num2.getNumber10From128() + " = " + sum2.getNumber10From128());

            System.out.println(num1 + " * " + num2 + " = " + mult);
            System.out.println(num1.getNumber10From128() + " * "
                    + num2.getNumber10From128() + " = " + mult.getNumber10From128());


        } catch (FileNotFoundException e) {
            System.out.println("An error  occurred.");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
