package xp.leetcode.big_numbers;

/**
 * Thrown to indicate not correct radix for calculation of BigNumbers
 */
public class RadixOutOfBoundException extends RuntimeException {

    /**
     * Constructs with message
     *
     * @param radix the illegal radix.
     */
    public RadixOutOfBoundException(short radix) {
        super("Radix can be only positive and less or equal than 128, your radix is " + radix);
    }

}
