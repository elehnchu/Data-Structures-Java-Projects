/**
 * @EL_CHU HC3294
 * Programming Assignment 2 - Recursion exercises
 * COMS W3134
 *
 * Note: All methods must be written recursively. No credit will be given for
 * methods written without recursion, even if they produce the correct output.
 */
public class Recursion {
        /**
         * Returns the value of x * y, computed via recursive addition.
         * x is added y times. Both x and y are non-negative.
         * @param x  non-negative integer multiplicand 1
         * @param y  non-negative integer multiplicand 2
         * @return   x * y
         */
        public static int recursiveMultiplication(int x, int y) {
            //base case for when x doesn't need to be added y amount of times anymore, so 0 is returned
            if (y==0){
                return 0;
            }
            //adds int x y amount of times until y = 0
            return x + recursiveMultiplication(x, y-1);
        }
/******************************************************************************/
        /**
         * Reverses a string via recursion.
         * @param s  the non-null string to reverse
         * @return  a new string with the characters in reverse order
         */
        public static String reverse(String s) {
            //base case for when what's left of the string is a single character or two.
            if (s.length() == 0 || s.length() == 1){
                //returns what's left of the string after recursion
                return s;
            }
            //gets character at end of list
            char x = s.charAt(s.length()-1);
            //uses recursive method to pass reverse method again, but with a substring
            //excluding the last character for each string previously passed
            return x + reverse(s.substring(0, s.length()-1));
        }
/******************************************************************************/
        private static int maxHelper(int[] array, int index, int max)  {
            //base case that returns the max once the index matches the length of the array passed
            if (index == array.length) {
                return max;
            }
            //if the value at a certain index is greater than the current max, then the value at that index becomes
            //the new max
            if (max < array[index]) {
                max = array[index];
            }
            //calls the method but increments the index to traverse through the entire array to find the max
            return maxHelper(array, index+1, max);

        }
        /**
         * Returns the maximum value in the array.
         * Uses a helper method to do the recursion.
         * @param array  the array of integers to traverse
         * @return       the maximum value in the array
         */
        public static int max(int[] array) {
            return maxHelper(array, 0, Integer.MIN_VALUE);
        }
/******************************************************************************/
        /**
         * Returns whether or not a string is a palindrome, a string that is
         * the same both forward and backward.
         * @param s  the string to process
         * @return   a boolean indicating if the string is a palindrome
         */
    public static boolean isPalindrome(String s) {
        // base case for when the string passed through recursion is only one or two characters long
        if (s.length() == 0 || s.length() == 1) {
            //if recursion successfully passes through the entire string, mean end character and first character match
            //and so on depending on the string passed, then the string is a palindrome
            return true;
        }
        //checks if the first and last character of the string passed are the same
        if (s.charAt(0) == s.charAt(s.length() - 1)) {
            //if is, then recursion is used to see if the second character and second to last character are the same
            //and so on.
            return isPalindrome(s.substring(1, s.length() - 1));
        }
        //if the characters aren't the same, then it is known that the string isn't a palindrome
        else {
            return false;
        }
    }
/******************************************************************************/
    private static boolean memberHelper(int key, int[] array, int index) {
        //if the entire array is traversed and a match isn't found between the key and a value within the array,
        //then false is returned.
        if (index == array.length) {
            return false;
        }
        //checks if the current value of array at index is equal to the key.
        if (key == array[index]) {
            //if so, true is returned.
            return true;
        }
        //method is called for recursion by incrementing the index to check the next value in the array
        return memberHelper(key, array, index + 1);

    }
    /**
     * Returns whether or not the integer key is in the array of integers.
     * Uses a helper method to do the recursion.
     * @param key    the value to seek
     * @param array  the array to traverse
     * @return       a boolean indicating if the key is found in the array
     */
    public static boolean isMember(int key, int[] array) {
        return memberHelper(key, array, 0);
    }
/******************************************************************************/
    /**
     * Returns a new string where identical chars that are adjacent
     * in the original string are separated from each other by a tilde '~'.
     * @param s  the string to process
     * @return   a new string where identical adjacent characters are separated
     *           by a tilde
     */
    public static String separateIdentical(String s) {
        //base case for when recursion is used and what's left of the string is only a character or two.
        if (s.length()==0 || s.length()==1){
            //what's left of the string is returned
            return s;
        }
        //we get the first character of the string
        char e = s.charAt(0);
        //if the first character matches the second character of the string, a tilde is added as a new string below
        if( e == s.charAt(1)){
            //new string is created, inserting the tilde & the first character (e), but also uses recursion to traverse
            // through the rest of the string by creating a substring (of a smaller version of the string)
            String b = e +"~" + separateIdentical(s.substring(1));
            return b;
        }
        //if there is no identical, adjacent characters, just the normal part of the string (without the tilde) is
        //returned
        return e + separateIdentical(s.substring(1));
    }
}

