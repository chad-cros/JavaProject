// This file gives access to the underlying datafile and stores the data in the Workout class.
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class FileAccess
{
    /**
     * A FileAccess.java file is provided for you to create static functions to load/save primes and crosses.
     * They should return true if the operation succeeds and false if it fails.
     * This success or failure should be used to update the main window’s status bar with the appropriate
     *  notification for the user.
     * Your save functions must use the “​for-each​” Java language construct and iterators to work through the
     *  prime and cross lists.
     * All saves and loads should be to the “data” subdirectory, but should allow user-specified filenames from the GUI.
     * This custom filename ability can allow the user to specify additional path information as part of
     * the custom filename.
     *
     * @param primes A list of prime numbers
     * @param filename A file(path) to read from & write to
     * @return boolean values of whether or not the functions correctly executed.
     */

    public static boolean loadPrimes(Primes primes, String filename)
    {
        try{
            Scanner sc = new Scanner(new File(Config.DATAPATH + filename));

            while (sc.hasNextBigInteger()) {
                primes.addPrime(sc.nextBigInteger());
            }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("File not found.\n");
                return false;
            }
        return true;
    }
  
    public static boolean loadCrosses(Primes primes, String filename)
    {
        try{
            BufferedReader reader = new BufferedReader(new FileReader(Config.DATAPATH + filename));
            String line = null;
            Scanner sc = null;

            while ((line = reader.readLine()) != null) { // Read file line by line until there are no more lines.
                sc = new Scanner(line); // Create a new scanner to parse through each line in the file.
                sc.useDelimiter(",");   // Set the delimiter between each BigInt to a comma

                primes.addCross(new Pair<BigInteger>(sc.nextBigInteger(), sc.nextBigInteger()));

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found.\n");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to read line from file.\n");
            return false;
        }
        return true;
    }
  
    public static boolean savePrimes(Primes primes, String filename)
    {
        try {
            Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                    new FileOutputStream(Config.DATAPATH + filename)
                    , StandardCharsets.UTF_8
                )
            );
            Iterable<BigInteger> iterable = primes.iteratePrimes();
            for (BigInteger bigInteger : iterable) {
                writer.write(bigInteger.toString());
                writer.write('\n');
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found.\n");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception occurred.\n");
            return false;
        }

        return true;
    }
  
    public static boolean saveCrosses(Primes primes, String filename)
    {
        try {
            Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(Config.DATAPATH + filename)
                            , StandardCharsets.UTF_8
                    )
            );

            Iterable<Pair<BigInteger>> iterable = primes.iterateCrosses();

            for (Pair<BigInteger> bigIntegerPair : iterable) {
                writer.write(bigIntegerPair.toString());
                writer.write('\n');
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found.\n");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception occurred.\n");
            return false;
        }
        return true;
    }
}