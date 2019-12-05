import java.math.BigInteger;

public class NaiveTest
{
	/** A NaiveTest.java file is included with a simple, functional isPrime function.
	 * You should replace this function with a more efficient test for primality.
	 * This test ​must​ be absolute.
	 * This means you can not use any of the probabilistic tests as your sole determiner of primality.
	 * (This includes but is not limited to Fermat’s, Miller-Rabin, and Solovay-Strasson, Adleman-Huang,
	 * Baille-PSW, and Frobenius.)
	 * You must use a deterministic primality test.
	 * You can use a fast probabilistic tests as an initial qualifier, as the NaiveTest uses
	 * BigInteger’s isProbablePrime function, but you must then use an absolute test.
	 * You are not only allowed, but encouraged to search Stack Overflow and other online resources for ideas and code
	 * to implement this one feature; however, you must cite your sources and give appropriate credit
	 * for any code that you do not come up with solely on your own.
	 *
	 * @param candidate
	 * @return
	 */
	public static boolean isPrime(BigInteger candidate)
	{
		if (!candidate.isProbablePrime((100))) { return false; } // Weed out the likely not primes.
		
		BigInteger loopStop = candidate.sqrt().add(BigInteger.ONE);
		for (BigInteger outerIndex = BigInteger.TWO; !outerIndex.equals(loopStop); outerIndex = outerIndex.add(BigInteger.ONE))
		{
			for (BigInteger innerIndex = outerIndex; !innerIndex.equals(loopStop); innerIndex = innerIndex.add(BigInteger.ONE))
			{
				if (candidate.equals(innerIndex.multiply(outerIndex))) { return false; }
			}
		}
		return true;
	}
}
