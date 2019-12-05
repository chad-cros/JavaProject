// This class is for all our configuration data. By putting it all in one place, we can easily make changes to the program without having to hunt down where in the code a constant is defined.

public class Config {
  /**
   * A Configuration class is provided to allow you a central location to configure
   * your application name, data path, prime file name and cross file name.
   * These defaults and values must be in Config and not hard-coded into the body of the program.
   */
  public final static String DATAPATH = "data/";
  public final static String APPLICATIONNAME = "OptimusPrime";
  public static String Prime_file_name = "default_primes.txt"; // Prime file: One prime per line.
  public static String Cross_file_name = "default_crosses.txt"; // Cross file: Two primes per line, separated by a comma.
}