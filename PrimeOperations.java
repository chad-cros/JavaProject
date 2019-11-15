import java.util.ArrayList; 
import java.math.BigInteger;
import java.util.stream.IntStream;

/*
 *  Desc: This class generates primes, twin primes, and hexagon crosses using BigInteger data types.
 */
public class PrimeOperations {

    // Pair class implementation.
    private class Pair<T> {
        /**
         * A pair class that links two objects of type T together.
         *
         * @param member_a
         *         object a of the pair class that is linked to object b
         * @param member_b
         *         object b of the pair class that is linked to object a
         */

        public Pair(T member_a, T member_b) {
            this.member_a = member_a;
            this.member_b = member_b;
        }

        @Override
        public String toString() {
            return member_a + ", " + member_b;
        }

        private T member_a;
        private T member_b;

        public T getMember_a() {
            return member_a;
        }

        public T getMember_b() {
            return member_b;
        }

        public void setMember_a(T member_a) {
            this.member_a = member_a;
        }

        public void setMember_b(T member_b) {
            this.member_b = member_b;
        }

        public void setMembers(T member_a, T member_b) {
            this.member_a = member_a;
            this.member_b = member_b;
        }

    }

    // Member variables for containing out lists of integers, twin primes, hexagon crosses, and the pairs of twin primes that make up the hex crosses.
    private Integer primeListSize = 10;
    private ArrayList<BigInteger> primeList = new ArrayList<BigInteger>(primeListSize);

    private Integer twinListSize = 10;
    private ArrayList<Pair<BigInteger>> twinList = new ArrayList<Pair<BigInteger>>(twinListSize);

    private Integer hexCrossSize = 10;
    private ArrayList<Pair<Pair<BigInteger>>> hexCrosses = new ArrayList<Pair<Pair<BigInteger>>>(hexCrossSize);


    // Add a prime to the prime list if and only iff it is not already in the list. (ignore duplicates)
    public void addPrime(BigInteger x) {
        // First, check if there's space in the array to add an item, if not, create a new ArrayList with a size of
        //  2x the previous, and copy all of the items in the array over.
//        System.out.println("Checking if listSize == var: " + primeList.size() + " == " + primeListSize + "\n");
        if (primeList.size() == primeListSize) {
//            System.out.println("Generating new array.\n");
            primeListSize += primeListSize;
            ArrayList<BigInteger> tmp = new ArrayList<BigInteger>(primeListSize);
            tmp.addAll(primeList);
            // Set the pointer of primeList to that of tmp, and point tmp to null.
            primeList = tmp;
            tmp = null;
        }
        // Add the given BigInteger to the primeList, and increase the count.
        primeList.add(x);
    }

    private void addTwins(BigInteger a, BigInteger b){
        // First, check if there's space in the array to add an item, if not, create a new ArrayList with a size of
        //  2x the previous, and copy all of the items in the array over.
        if (twinListSize == twinList.size()) {
            twinListSize += twinListSize;
            ArrayList<Pair<BigInteger>> tmp = new ArrayList<Pair<BigInteger>>(twinListSize);
            tmp.addAll(twinList);
            // Set the pointer of primeList to that of tmp, and point tmp to null.
            twinList = tmp;
            tmp = null;
        }
        // Add the given BigInteger to the primeList, and increase the count.
        twinList.add(new Pair<BigInteger>(a, b));
    }

    private void addHexes(Pair<BigInteger> a, Pair<BigInteger> b){
        if (hexCrossSize == hexCrosses.size()) {
            hexCrossSize += hexCrossSize;
            ArrayList<Pair<Pair<BigInteger>>> tmp = new ArrayList<Pair<Pair<BigInteger>>>(hexCrossSize);
            tmp.addAll(hexCrosses);
            // Set the pointer of primeList to that of tmp, and point tmp to null.
            hexCrosses = tmp;
            tmp = null;
        }
        // Add the given BigInteger to the primeList, and increase the count.
        hexCrosses.add(new Pair<Pair<BigInteger>>(a, b));
    }

    // Output the prime list. Each prime should be on a separate line and the total number of primes should be on the following line.
    public void printPrimes() {
        for (BigInteger bigInteger : primeList) System.out.println(bigInteger.toString());
        System.out.println("Total Primes: " + primeList.size() + '\n');
    }

    // Output the twin prime list. Each twin prime should be on a separate line with a comma separating them, and the total number of twin primes should be on the following line.
    public void printTwins() {
        for (Pair<BigInteger> bigIntegerPair : twinList) System.out.println(bigIntegerPair.toString());
        System.out.println("Total Twins: " + twinList.size() + '\n');
    }

    // Output the hexagon cross list. Each should be on a separate line listing the two twin primes and the corresponding hexagon cross, with the total number on the following line.
    public void printHexes() {
        for (int i = 0; i < hexCrosses.size(); ++i) {
            System.out.println("Prime Pairs: "
                    + hexCrosses.get(i).getMember_a().toString()
                    + " and "
                    + hexCrosses.get(i).getMember_b().toString()
                    + " separated by "
                    + (hexCrosses.get(i).getMember_a().getMember_a().intValue() + 1)
                    + ", "
                    + (hexCrosses.get(i).getMember_b().getMember_a().intValue() + 1)
            );
        }
        System.out.println("Total Hexes: " + hexCrosses.size() + '\n');
    }

    // Generate and store a list of primes.
    public void generatePrimes(int count) {
        /*
            Brute-force method to look for primes until the number of primes generated matches the input count.
         */
        int primes_found = 0;
        Integer iterator = 2;
        boolean isPrime = true;

        while (primes_found != count){

            // Skip 2 & 3 here, since we know they're prime
            if(iterator == 2 || iterator == 3) {
                ++primes_found;
                addPrime(new BigInteger(iterator.toString()));
                ++iterator;
                continue;
            }

            // Check if any of the numbers below the given integer are factors of the given iterator
            for (Integer i = 2; i <= java.lang.Math.floor(java.lang.Math.sqrt(iterator)); ++i){
                // On the first instance of a factor, break the loop and tell the next function to not add it to the list
                if (iterator % i == 0){
                    isPrime = false;
                    break;
                }
            }

            if(isPrime){
                ++primes_found;
                addPrime(new BigInteger(iterator.toString()));
            }

            ++iterator;
            isPrime = true;

        }
    }

    // Generate and store a list of twin primes.
    public void generateTwinPrimes() {
        for (int i = 1; i < primeList.size(); ++i){
            if(primeList.get(i).intValue() - primeList.get(i - 1).intValue() == 2){
                addTwins(primeList.get(i - 1), primeList.get(i));
            }
        }
    }

    // Generate and store the hexagon crosses, along with the two twin primes that generate the hexagon cross.
    public void generateHexPrimes() {
        // Run through all the elements in the twinList ArrayList
        for (int i = 0; i < twinList.size() - 1; ++i){
            for (int j = i + 1; j < twinList.size(); ++j){
                if(((twinList.get(i).getMember_a().intValue() + 1) * 2) == (twinList.get(j).getMember_a().intValue() + 1)){
                    addHexes(twinList.get(i), twinList.get(j));
                }
            }
        }
    }
}
