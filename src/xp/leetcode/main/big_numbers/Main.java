package xp.leetcode.main.big_numbers;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The program sums up and multiplies 2 decimal numbers, but only positive numbers.
 * The feature update allows make calculation with negative numbers too.
 * <p>
 * Option 1: run the program with arguments. Arguments should contain 2 decimal numbers.
 * Option 2: write 2 decimal numbers in the file "numbers.txt" with any separation
 * <p>
 * The result you can see in the files "summ.txt" and mult.txt and also text output of the program.
 */
public class Main {

    private static final String SOURCE_PCKG = "src/xp/leetcode/main/big_numbers/source/";

    private static final String FILE_IN = SOURCE_PCKG + "numbers.txt";

    private static final String FILE_OUT_SUMM = SOURCE_PCKG + "summ.txt";

    private static final String FILE_OUT_MULT = SOURCE_PCKG + "mult.txt";

    private static final String RADIX_OUT_128 = "128-Radix:      ";

    private static final String RADIX_OUT_10 = "10-Radix:       ";

    public static void main(String[] args) {
        BigNumber num1;
        BigNumber num2;
        if (args.length != 0) {
            num1 = new BigNumber(args[0]);
            num2 = new BigNumber(args[1]);
        } else {
            try {
                List<BigNumber> numbers = BigNumber.getNumberFromFile128(new File(FILE_IN));
                if (numbers.size() < 2) {
                    return;
                }
                num1 = numbers.get(0);
                num2 = numbers.get(1);
            } catch (IOException e) {
                System.out.println("An error during reading file occurred. Check the file.");
                e.printStackTrace();
                return;
            }
        }


        BigNumber sum = BigNumber.sum(num1, num2);
        System.out.println(RADIX_OUT_128 + num1 + " + " + num2 + " = " + sum);
        System.out.println(RADIX_OUT_10 + num1.getNumber10From128() + " + "
                + num2.getNumber10From128() + " = " + num1.add(num2).getNumber10From128());

        BigNumber mult = BigNumber.multiply(num1, num2);
        System.out.println(RADIX_OUT_128 + num1 + " * " + num2 + " = " + mult);
        System.out.println(RADIX_OUT_10 + num1.getNumber10From128() + " * "
                + num2.getNumber10From128() + " = " + mult.getNumber10From128());

        try {
            BigNumber.writeNumberToFile128(sum, new File(FILE_OUT_SUMM));
            BigNumber.writeNumberToFile128(mult, new File(FILE_OUT_MULT));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
