
public class Pair<T> {
	/** A Pair.java class is provided to give pair functionality.
	 * You may use this class or the one you wrote for Part 1.
	 * If you use the one your wrote for Part1, take advantage of Eclipse’s refactoring tools to automatically
	 * move it to a new class.
	 *
	 */
	private T m_left;
	private T m_right;
	
	Pair(T l, T r) 
	{
		m_left = l;
		m_right = r;
	}
	public T left() { return m_left; }
	public T right() {return m_right; }

	@Override
	public String toString() {
		return m_left + "," + m_right;
	}
}