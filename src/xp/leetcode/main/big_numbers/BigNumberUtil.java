package xp.leetcode.main.big_numbers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

class BigNumberUtil {

    private static final short CONST_128 = Byte.MAX_VALUE + 1;
    private static final byte CONST_10 = 10;

    private static List<Byte> ARRAY_128;

    static {
        ARRAY_128 = new ArrayList<>();
        ARRAY_128.add((byte) 8);
        ARRAY_128.add((byte) 2);
        ARRAY_128.add((byte) 1);
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

    static List<Byte> sum2List128(List<Byte> num1, List<Byte> num2) {
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

    private static List<Byte> multiply128(List<Byte> numLong, byte singleN) {
        return multiplyRadix(CONST_128, numLong, singleN);
    }

    private static List<Byte> multiply10(List<Byte> numLong, byte singleN) {
        return multiplyRadix(CONST_10, numLong, singleN);
    }

    static List<Byte> multiply128(List<Byte> num1, List<Byte> num2) {
        return multiplyRadix(CONST_128, num1, num2);
    }

    private static List<Byte> multiply10(List<Byte> num1, List<Byte> num2) {
        return multiplyRadix(CONST_10, num1, num2);
    }

    private static List<Byte> multiplyRadix(short radix, List<Byte> num1, List<Byte> num2) {
        List<Byte> res = new ArrayList<>();
        List<Byte> numCopy = new ArrayList<>(num1);
        for (int i = 0; i < num2.size(); i++) {
            List<Byte> temp = multiplyRadix(radix, numCopy, num2.get(i));
            res = sum2ListRadix(radix, res, temp);
            numCopy.add(0, (byte) 0);
        }
        return res;
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

    static List<Byte> getNumberList10From128(List<Byte> num128) {
        List<Byte> numList10 = new ArrayList<>();
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

    static List<List<Byte>> getNumberList128(InputStream input) throws IOException {
        List<List<Byte>> numbers = new ArrayList<>();
        List<Byte> numList128 = new ArrayList<>();
        int c;
        while ((c = input.read()) != -1) {
            numList128 = addCharToList128(c, numList128);
            if (Character.isSpaceChar(c)) {
                numbers.add(numList128);
                numList128 = new ArrayList<>();
            }
        }
        numbers.add(numList128);
        return numbers;
    }


    static List<Byte> getNumber128FromString(String line) {
        List<Byte> numList128 = new ArrayList<>();
        for (int c : line.toCharArray()) {
            numList128 = addCharToList128(c, numList128);
        }
        return numList128;
    }

    static List<Byte> getNumber128FromDecimal(long aLong) {
        String line = "" + aLong;
        return getNumber128FromString(line);
    }

    private static List<Byte> addCharToList128(int c, List<Byte> numList128) {
        if (Character.isDigit(c)) {
            byte digit = (byte) (c - '0');
            numList128 = addDigitToList128(digit, numList128);
        }
        return numList128;
    }

    /**
     * Add  digit to the end of the number
     *
     * @param digit      0–9
     * @param numList128 number in 128-radix
     * @return number in 128-radix
     */
    private static List<Byte> addDigitToList128(byte digit, List<Byte> numList128) {
        if (numList128.isEmpty()) {
            numList128.add(digit);
        } else {
            numList128 = multiply128(numList128, CONST_10);
            numList128 = sum128(numList128, digit);
        }
        return numList128;
    }

    static void writeNumberToFile(List<Byte> array, File file) throws IOException {
        FileWriter writer = new FileWriter(file);
        int i = array.size() - 1;
        while (i >= 0) {
            writer.write(array.get(i--) + '0');
        }
        writer.close();
    }

}
