package xp.leetcode.main.big_numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents a number in the 128 numeral system
 */
public class BigNumber {

    /**
     * List of digits of number in 128-numeral system, where the first index represent the last digit.
     */
    private List<Byte> nums;

    /**
     * Constructor for creation BigNumber from the String with the digits in 10-numeral system
     *
     * @param line – decimal number
     */
    public BigNumber(String line) {
        this.nums = BigNumberUtil.getNumber128FromString(line);
    }

    /**
     * Constructor for creation BigNumber from the decimal number
     *
     * @param aNumber – decimal number
     */
    public BigNumber(long aNumber) {
        this.nums = BigNumberUtil.getNumber128FromDecimal(aNumber);
    }

    /**
     * Inner constructor for creation BigNumber
     *
     * @param nums – list of digits in 128-numeral system
     */
    private BigNumber(List<Byte> nums) {
        this.nums = new ArrayList<>(nums);
    }

    /**
     * Add to this number another number
     *
     * @param num the second number of summarize
     * @return new BigNumber as a result of sum up
     */
    public BigNumber add(BigNumber num) {
        if (num == null) {
            return this;
        }
        return new BigNumber(BigNumberUtil.sum2List128(this.nums, num.nums));
    }

    /**
     * Sum up 2 BigNumbers.
     *
     * @param num1 BigNumber as the first summand
     * @param num2 BigNumber as the second summand
     * @return new BigNumber as a sum
     */
    public static BigNumber sum(BigNumber num1, BigNumber num2) {
        if (num1 == null) {
            return num2;
        }
        if (num2 == null) {
            return num1;
        }
        return new BigNumber(BigNumberUtil.sum2List128(num1.nums, num2.nums));
    }

    /**
     * Multiply of  2 BigNumbers.
     *
     * @param num1 BigNumber as the first multiplier
     * @param num2 BigNumber as the second multiplier
     * @return new BigNumber as a product
     */
    public static BigNumber multiply(BigNumber num1, BigNumber num2) {
        if (num1 == null) {
            return num2;
        }
        if (num2 == null) {
            return num1;
        }
        return new BigNumber(BigNumberUtil.multiply128(num1.nums, num2.nums));
    }

    /**
     * Extract numbers from file.
     *
     * @param file with decimal numbers separated by space, line separator or paragraph separator
     * @return list of BigNumbers
     * @throws IOException exception can throw during file reading
     */
    public static List<BigNumber> getNumberFromFile128(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        List<List<Byte>> numberList128 = BigNumberUtil.getNumberList128(in);
        List<BigNumber> numbers = new ArrayList<>();
        numberList128.forEach(numberList -> numbers.add(new BigNumber(numberList)));
        return numbers;
    }

    /**
     * Write BigNumber in the file as a decimal number.
     *
     * @param num  BigNumber
     * @param file file
     * @throws IOException exception can throw during file writing
     */
    public static void writeNumberToFile128(BigNumber num, File file) throws IOException {
        BigNumberUtil.writeNumberToFile(BigNumberUtil.getNumberList10From128(num.nums), file);
    }

    /**
     * Return the line represented the decimal number from BigNumber
     *
     * @return the decimal number as a line
     */
    public String getNumber10From128() {
        List<Byte> numberList10From128 = BigNumberUtil.getNumberList10From128(nums);
        StringBuilder sb = new StringBuilder();
        for (int i = numberList10From128.size() - 1; i >= 0; i--) {
            sb.append(numberList10From128.get(i));
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = nums.size() - 1; i >= 0; i--) {
            sb.append(nums.get(i)).append(" ");
        }
        if (nums.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
