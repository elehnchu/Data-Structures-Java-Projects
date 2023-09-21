/**
 * Class containing methods that find the greatest common denominator of two numbers.
 * @author EL CHU
 * @version 1.0
 */

public class GCD {
    /**
     * Takes the two inputted values on the command line, passes it through the
     * iterativeGCD method and recursiveGCD method, and prints results out.
     */
    public static void main (String[] args) {
        //initialize the variables that are going to be passed through the iterative
        // & recursive methods
        int a = 0;
        int b = 0;
        if (args.length != 2){
            System.out.println("Usage: java GCD <integer m> <integer n>");
            System.exit(1);
        }
        try{
            //gets the first number in the command line & converts it from a string to
            // an integer and takes the absolute value of it to deal with negatives.
            a = Math.abs(Integer.parseInt(args[0]));
        }
        // try catch is used to deal out exception errors if a non-number is inputted.
        catch (NumberFormatException nfe){
            System.out.println("Error: The first argument is not a valid integer.");
            //used to exit code, as wrong number was inputted.
            System.exit(1);
        }

        try{
            b = Math.abs(Integer.parseInt(args[1]));
        }
        catch(NumberFormatException nfe) {
            System.out.println("Error: The second argument is not a valid integer.");
            System.exit(1);
        }
        if (a==0 & b==0){
            System.out.println("gcd(0, 0) = undefined");
            System.exit(0);
        }
        System.out.printf("Iterative: gcd(%s, %s) = %s", args[0], args[1], iterativeGcd(a, b));
        System.out.println("");
        System.out.printf("Recursive: gcd(%s, %s) = %s", args[0], args[1], recursiveGcd(a, b));
        //used to exit code successfully
        System.exit(0);

    }
    /**
     * Returns the iterative greatest common denominator (GCD) of two inputted numbers using Euclid's method
     * @param m the larger of the two numbers inputted
     * @param n the smaller of the two numbers inputted
     * @return the greatest common denominator
     */
    public static int iterativeGcd (int m, int n) {
        //switches m & n if m is smaller than n, that way we can predetermine which integer to replace for
        //Euclid's method.
        if (m<n){
            int z = m;
            m = n;
            n = z;
        }
        //returns the other integer if one is 0
        if (n==0){
            return m;
        }
        else if (m==0){
            return n;
        }

        else {
            // a while loop to determine when to stop, as when n = 0, the GCD has been found and is
            //in m, hence why m is returned.
            while (n != 0 ) {
                int x = n;
                // the remainder of when m is divided by n, replaces n
                int r = m%n;
                //m becomes the previous version of n.
                m = x;
                n = r;

            }
            return m;
        }
    }
    /**
     * Returns the recursive greatest common denominator (GCD) of two inputted numbers using Euclid's method
     * @param m the larger of the two numbers inputted
     * @param n the smaller of the two numbers inputted
     * @return the greatest common denominator
     */
    public static int recursiveGcd (int m, int n) {
        if (n==0){
            return m;
        }
        else{
            //n is replaced by the remainder of the division, and m is
            //replaced by n. This cycle continues until n =0, and m is returned as the GCD.
            return recursiveGcd(n, m%n);
        }

    }

}




