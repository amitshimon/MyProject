/**
 * 
 */
package exception;

/**
 * @author аемвд
 *
 */
public class ThereIsNoSuchPrice extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ThereIsNoSuchPrice(String massage) {
		super(massage);
	}
}
