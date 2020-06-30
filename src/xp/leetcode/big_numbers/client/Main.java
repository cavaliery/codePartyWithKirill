package xp.leetcode.big_numbers.client;

import xp.leetcode.big_numbers.BigNumber;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The program sums up and multiplies 2 decimal numbers, but only positive numbers.
 * The feature update allows make calculation with negative numbers too.
 * <p>
 * Option 1: run the program with arguments. Then the program ask you to write two numbers.
 * Option 2: write 2 decimal numbers in the file "numbers.txt" with line separation.
 * <p>
 * The result you can see in the files "summ.txt" and mult.txt and also text output of the program.
 */
public class Main {

    private static final String SOURCE_PCKG = "src/xp/leetcode/big_numbers/source/";

    private static final String FILE_IN = SOURCE_PCKG + "numbers.txt";

    private static final String FILE_OUT_SUMM = SOURCE_PCKG + "summ.txt";

    private static final String FILE_OUT_MULT = SOURCE_PCKG + "mult.txt";

    public static void main(String[] args) {
        BigNumber num1 = null;
        BigNumber num2 = null;
        if (args.length != 0) {
            System.out.println("Write the first number:");
            while (num1 == null) {
                num1 = readFromSystemIn();
            }
            System.out.println("Write the second number:");
            while (num2 == null) {
                num2 = readFromSystemIn();
            }
            System.out.println();
        } else {
            List<BigNumber> numbers = getNumberFromFile();
            if (numbers.size() < 2) {
                return;
            }
            num1 = numbers.get(0);
            num2 = numbers.get(1);
        }

        BigNumber sum = BigNumber.sum(num1, num2);
        System.out.println("128-Radix:      " + num1 + " + " + num2 + " = " + sum);
        System.out.println("10-Radix:       " + num1.toDecimalString() + " + "
                + num2.toDecimalString() + " = " + num1.add(num2).toDecimalString());

        BigNumber mult = BigNumber.multiply(num1, num2);
        System.out.println("128-Radix:      " + num1 + " * " + num2 + " = " + mult);
        System.out.println("10-Radix:       " + num1.toDecimalString() + " * "
                + num2.toDecimalString() + " = " + mult.toDecimalString());

        writeNumberToFile(sum, new File(FILE_OUT_SUMM));
        writeNumberToFile(mult, new File(FILE_OUT_MULT));

    }

    private static BigNumber readFromSystemIn() {
        try {
            return BigNumber.createBigNumber(BigNumber.MAX_BYTE_RADIX, System.in);
        } catch (IOException e) {
            System.err.println("Incorrect format of the number. Please try one more time!");
            e.printStackTrace();
        }
        return null;
    }

    private static List<BigNumber> getNumberFromFile() {
        List<BigNumber> numbers = new ArrayList<>();
        try {
            List<String> numLines = Files.readAllLines(Paths.get(FILE_IN));
            numLines.forEach(line -> numbers.add(new BigNumber(BigNumber.MAX_BYTE_RADIX, line.trim())));
        } catch (IOException e) {
            System.err.println("An error during reading file occurred. Check the file.");
            e.printStackTrace();
        }
        return numbers;
    }

    private static void writeNumberToFile(BigNumber num, File file) {
        try {
            String numLine = num.toDecimalString();
            FileWriter writer = new FileWriter(file);
            writer.write(numLine);
            writer.close();
        } catch (IOException e) {
            System.err.println("An error during writing file occurred.");
            e.printStackTrace();
        }
    }

}
