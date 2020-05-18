package xp.leetcode.main.big_numbers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BigNumber {

    public static final short CONST_128 = Byte.MAX_VALUE + 1;
    public static final byte CONST_10 = 10;

    public static List<Byte> ARRAY_128;

    static {
        ARRAY_128 = new ArrayList<>();
        ARRAY_128.add((byte) 8);
        ARRAY_128.add((byte) 2);
        ARRAY_128.add((byte) 1);
    }

    // 0-index – the last digit
    List<Byte> nums;
    boolean sign;

    public BigNumber(List<Byte> nums) {
        this.nums = new ArrayList<>(nums);
    }

    public BigNumber add(BigNumber num) {
        if (num == null) {
            return this;
        }
        return new BigNumber(sum2List128(this.nums, num.nums));
    }


    public static BigNumber sum(BigNumber num1, BigNumber num2) {
        if (num1 == null) {
            return num2;
        }
        if (num2 == null) {
            return num1;
        }
        return new BigNumber(sum2List128(num1.nums, num2.nums));
    }

    public static BigNumber multiply(BigNumber num1, BigNumber num2) {
        if (num1 == null) {
            return num2;
        }
        if (num2 == null) {
            return num1;
        }
        return new BigNumber(multiplyRadix(CONST_128, num1.nums, num2.nums));
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = nums.size() - 1; i >= 0; i--) {
            sb.append(nums.get(i)).append(" ");
        }
        return sb.toString();
    }

    private static List<Byte> multiply128(List<Byte> numLong, byte singleN) {
        return multiplyRadix(CONST_128, numLong, singleN);
    }

    private static List<Byte> multiply10(List<Byte> numLong, byte singleN) {
        return multiplyRadix(CONST_10, numLong, singleN);
    }

    //return List with index 0 – the last digit, index n – the first digit
    private static List<Byte> multiplyRadix(short radix, List<Byte> numLong, byte singleN) {
        List<Byte> result = new ArrayList<>();
        int trans = 0;
        for (int i = 0; i < numLong.size(); i++) {
            int temp = numLong.get(i) * singleN + trans;
            trans = temp / radix;
            result.add((byte) (temp % radix));

        }
        if (trans != 0) {
            result.add((byte) trans);
        }
        return result;
    }

    private static List<Byte> multiply(List<Byte> numLong, List<Byte> num) {
        List<Byte> res = new ArrayList<>();
        List<Byte> numCopy = new ArrayList<>(numLong);
        for (int i = 0; i < num.size(); i++) {
            List<Byte> temp = multiply10(numCopy, num.get(i));
            res = sum2List10(res, temp);
            numCopy.add(0, (byte) 0);
        }
        return res;
    }

    private static List<Byte> multiplyRadix(short radix, List<Byte> numLong, List<Byte> num) {
        List<Byte> res = new ArrayList<>();
        List<Byte> numCopy = new ArrayList<>(numLong);
        for (int i = 0; i < num.size(); i++) {
            List<Byte> temp = multiplyRadix(radix, numCopy, num.get(i));
            res = sum2ListRadix(radix, res, temp);
            numCopy.add(0, (byte) 0);
        }
        return res;
    }

    private static List<Byte> sum128(List<Byte> numLong, Byte singleN) {
        return sumRadix(CONST_128, numLong, singleN);
    }

    private static List<Byte> sum10(List<Byte> numLong, Byte singleN) {
        return sumRadix(CONST_10, numLong, singleN);
    }

    private static List<Byte> sumRadix(short radix, List<Byte> numLong, byte singleN) {
        List<Byte> result = new ArrayList<>();
        int rest = singleN;
        for (int i = 0; i < numLong.size(); i++) {
            int temp = numLong.get(i) + rest;
            result.add((byte) (temp % radix));
            rest = temp / radix;
        }
        if (rest != 0) {
            result.add((byte) rest);
        }
        return result;
    }

    private static List<Byte> sum2List10(List<Byte> num1, List<Byte> num2) {
        return sum2ListRadix(CONST_10, num1, num2);
    }

    private static List<Byte> sum2List128(List<Byte> num1, List<Byte> num2) {
        return sum2ListRadix(CONST_128, num1, num2);
    }

    private static List<Byte> sum2ListRadix(short radix, List<Byte> num1, List<Byte> num2) {
        if (num1.size() >= num2.size()) {
            return sumBiggerListWithSmallerRadix(radix, num1, num2);
        }
        return sumBiggerListWithSmallerRadix(radix, num2, num1);
    }

    private static List<Byte> sumBiggerListWithSmallerRadix(short radix, List<Byte> num1, List<Byte> num2) {
        List<Byte> res = new ArrayList<>();
        int i1 = 0;
        int i2 = 0;
        int rest = 0;
        while (i1 < num1.size() && i2 < num2.size()) {
            int temp = num1.get(i1++) + num2.get(i2++) + rest;
            res.add((byte) (temp % radix));
            rest = temp / radix;
        }
        while (i1 < num1.size()) {
            int temp = num1.get(i1++) + rest;
            res.add((byte) (temp % radix));
            rest = temp / radix;
        }
        if (rest != 0) {
            res.add((byte) rest);
        }
        return res;
    }

    public static List<BigNumber> getNumberFromFile128(File file) throws IOException {
        InputStream in = new FileInputStream(file);
        List<BigNumber> numbers = new ArrayList<>();
        List<Byte> numList128 = new ArrayList<>();
        int c;
        while ((c = in.read()) != -1) {
            if (Character.isDigit(c)) {
                byte digit = (byte) (c - '0');
                if (numList128.isEmpty()) {
                    numList128.add(digit);
                } else {
                    numList128 = multiply128(numList128, CONST_10);
                    numList128 = sum128(numList128, digit);
                }
            }
            if (Character.isSpaceChar(c)) {
                numbers.add(new BigNumber(numList128));
                numList128.clear();
            }
        }
        numbers.add(new BigNumber(numList128));
        return numbers;
    }

    public static void writeNumberToFile128(BigNumber num, File file) throws IOException {
        writeNumberToFile(num.getNumberList10From128(), file);
    }

    private List<Byte> getNumberList10From128() {
        List<Byte> numList10 = new ArrayList<>();
        List<Byte> num128 = this.nums;
        int first = num128.get(num128.size() - 1);
        while (first != 0) {
            numList10.add((byte) (first % CONST_10));
            first = first / CONST_10;
        }
        for (int i = num128.size() - 2; i >= 0; i--) {
            numList10 = multiply(numList10, ARRAY_128);
            numList10 = sum10(numList10, num128.get(i));
        }
        return numList10;
    }

    private static void writeNumberToFile(List<Byte> array, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        int i = array.size() - 1;
        while (i >= 0) {
            writer.write(array.get(i--) + '0');
        }
        writer.close();
    }

    public BigNumber getNumber10From128() {
        return new BigNumber(this.getNumberList10From128());
    }


}
