package xp.leetcode.big_numbers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class represents number that can be long and allows to make calculation.
 */
public class BigNumber {

    public static final short RADIX_128 = Byte.MAX_VALUE + 1;

    /**
     * List of digits of number in specific-numeral system,
     * where the first index represent the last digit.
     */
    private List<Byte> nums;

    /**
     * Base of numerical system. Can be from 2 to 128.
     */
    private short base;

    /**
     * Represent base in array for inner calculation.
     */
    private final List<Byte> array_radix;

    /**
     * Inner constructor for creation BigNumber
     *
     * @param base base of numerical system. Can be from 2 to 128.
     * @param nums – list of digits in radix-numeral system
     */
    private BigNumber(short base, List<Byte> nums) throws RadixOutOfBoundException {
        if (base < 2 || base > RADIX_128) {
            throw new RadixOutOfBoundException(base);
        }
        this.base = base;
        this.nums = new ArrayList<>(nums);
        List<Byte> array_radix = new ArrayList<>();
        array_radix.add((byte) 8);
        array_radix.add((byte) 2);
        array_radix.add((byte) 1);
        this.array_radix = array_radix;
    }


    /**
     * Constructor for creation BigNumber from the String with the digits in 10-numeral system
     *
     * @param base base of numerical system. Can be from 2 to 128.
     * @param line – decimal number
     */
    public BigNumber(short base, String line) {
        this(base, BigNumberUtil.getNumberFromString(base, line));
    }

    /**
     * Constructor for creation BigNumber from the decimal number
     *
     * @param base    base of numerical system. Can be from 2 to 128.
     * @param aNumber – decimal number
     */
    public BigNumber(short base, long aNumber) {
        this(base, BigNumberUtil.getNumberFromDecimal(base, aNumber));
    }

    /**
     * Constructor for creation BigNumber from the input stream
     *
     * @param base  base of numerical system. Can be from 2 to 128.
     * @param input – input
     */
    public BigNumber(short base, InputStream input) throws IOException {
        this(base, BigNumberUtil.getNumberList(base, input));
    }


    /**
     * Add to this number another number
     *
     * @param num the second number of summarize
     * @return new BigNumber as a result of sum up
     */
    public BigNumber add(BigNumber num) {
        return new BigNumber(base, BigNumberUtil.sum2ListRadix(base, this.nums, num.nums));
    }

    /**
     * Add to this number another number
     *
     * @param num the second number of summarize
     * @return new BigNumber as a result of sum up
     */
    public BigNumber multiply(BigNumber num) {
        return new BigNumber(base, BigNumberUtil.multiplyRadix(base, this.nums, num.nums));
    }

    /**
     * Return the line represented the decimal number from BigNumber
     *
     * @return the decimal number as a line
     */
    public String getNumber() {
        List<Byte> numberList10 = BigNumberUtil.getNumberList10FromListOnBase(nums, array_radix);
        StringBuilder sb = new StringBuilder();
        for (int i = numberList10.size() - 1; i >= 0; i--) {
            sb.append(numberList10.get(i));
        }
        return sb.toString();
    }

    /**
     * Sum up 2 BigNumbers.
     *
     * @param num1 BigNumber as the first summand
     * @param num2 BigNumber as the second summand
     * @return new BigNumber as a sum
     */
    public static BigNumber sum(BigNumber num1, BigNumber num2) {
        return num1.add(num2);
    }

    /**
     * Multiply of  2 BigNumbers.
     *
     * @param num1 BigNumber as the first multiplier
     * @param num2 BigNumber as the second multiplier
     * @return new BigNumber as a product
     */
    public static BigNumber multiply(BigNumber num1, BigNumber num2) {
        return num1.multiply(num2);
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
