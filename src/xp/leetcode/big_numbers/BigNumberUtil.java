package xp.leetcode.big_numbers;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class BigNumberUtil {

    private static final byte DECIMAL_RADIX = 10;

    /**
     * Summarizes two lists of digits (represents BigNumber) on particular base.
     *
     * @param radix base
     * @param num1  list of digits on particular base
     * @param num2  list of digits on particular base
     * @return list of digits on particular base
     */
    static List<Byte> sum(short radix, List<Byte> num1, List<Byte> num2) {
        if (num1.size() >= num2.size()) {
            return sumBiggerListWithSmallerRadix(radix, num1, num2);
        }
        return sumBiggerListWithSmallerRadix(radix, num2, num1);
    }

    /**
     * Multiplies two lists of digits (represents BigNumber) on particular base.
     *
     * @param radix base
     * @param num1  list of digits on particular base
     * @param num2  list of digits on particular base
     * @return list of digits on particular base
     */
    static List<Byte> multiply(short radix, List<Byte> num1, List<Byte> num2) {
        List<Byte> res = new ArrayList<>();
        List<Byte> numCopy = new ArrayList<>(num1);
        for (int i = 0; i < num2.size(); i++) {
            List<Byte> temp = multiplySingleDigit(radix, numCopy, num2.get(i));
            res = sum(radix, res, temp);
            numCopy.add(0, (byte) 0);
        }
        return res;
    }

    private static List<Byte> sumSingleDigit(short radix, List<Byte> numLong, byte singleN) {
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

    private static List<Byte> multiplySingleDigitDecimal(List<Byte> numLong, byte singleN) {
        return multiplySingleDigit(DECIMAL_RADIX, numLong, singleN);
    }

    //return List with index 0 – the last digit, index n – the first digit
    private static List<Byte> multiplySingleDigit(short radix, List<Byte> numLong, byte singleN) {
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
            List<Byte> temp = multiplySingleDigitDecimal(numCopy, num.get(i));
            res = sum(DECIMAL_RADIX, res, temp);
            numCopy.add(0, (byte) 0);
        }
        return res;
    }

    /**
     * Calculates decimal number from list of digits in particular base.
     *
     * @param radix particular base
     * @param num   list of digits in particular base
     * @return list of digits represented decimal number with index 0 – the last digit, index n – the first digit
     */
    static List<Byte> getDecimalNumber(List<Byte> radix, List<Byte> num) {
        List<Byte> numList10 = new ArrayList<>();
        int first = num.get(num.size() - 1);
        while (first != 0) {
            numList10.add((byte) (first % DECIMAL_RADIX));
            first = first / DECIMAL_RADIX;
        }
        for (int i = num.size() - 2; i >= 0; i--) {
            numList10 = multiply(numList10, radix);
            numList10 = sumSingleDigit(DECIMAL_RADIX, numList10, num.get(i));
        }
        return numList10;
    }

    /**
     * Extracts digits from InputStream and calculates to the list of digits on particular base.
     *
     * @param radix particular base
     * @param input input with decimal number
     * @return list of digits on particular base
     * @throws IOException if something goes wrong
     */
    static List<Byte> getNumberFromInput(short radix, InputStream input) throws IOException {
        List<Byte> numList = new ArrayList<>();
        int c;
        while ((c = input.read()) != -1) {
            numList = addCharToList(radix, c, numList);
            if (Character.isWhitespace(c)) {
                return numList;
            }
        }
        return numList;
    }

    /**
     * Extracts digits from the Line and calculates to the list of digits on particular base.
     *
     * @param radix particular base
     * @param line  line with decimal number
     * @return list of digits on particular base
     */
    static List<Byte> getNumberFromString(short radix, String line) {
        List<Byte> numList = new ArrayList<>();
        for (int c : line.toCharArray()) {
            numList = addCharToList(radix, c, numList);
        }
        return numList;
    }

    /**
     * Calculates to the list of digits on particular base.
     *
     * @param radix particular base
     * @param aLong decimal number
     * @return list of digits on particular base
     */
    static List<Byte> getNumberFromDecimal(short radix, long aLong) {
        String line = "" + aLong;
        return getNumberFromString(radix, line);
    }

    private static List<Byte> addCharToList(short radix, int c, List<Byte> numList) {
        if (Character.isDigit(c)) {
            byte digit = (byte) (c - '0');
            numList = addDigitToList(radix, digit, numList);
        }
        return numList;
    }

    /**
     * Add  digit to the end of the number.
     *
     * @param radix   base of calculation system
     * @param digit   0–9
     * @param numList number in the base calculation system
     * @return number in base calculation system
     */
    private static List<Byte> addDigitToList(short radix, byte digit, List<Byte> numList) {
        if (numList.isEmpty()) {
            numList.add(digit);
        } else {
            numList = multiplySingleDigit(radix, numList, DECIMAL_RADIX);
            numList = sumSingleDigit(radix, numList, digit);
        }
        return numList;
    }

}
