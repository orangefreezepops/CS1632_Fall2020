package Square;
import static org.junit.Assert.*;
import org.mockito.*;

import org.junit.Test;

public class SquareTest {

	/*
	 * Preconditions: A new Square square is created
	 *                A new Number number is created
	 * Execution Steps: Call sq.setSquared(n, 3)
	 * PostConditions: The value of number is set to 9
	 */
	@Test
	public void testSetSquared() {
		// TODO: Fill in!
		Square square = new Square(); //new square
		Number n = Mockito.mock(Number.class); //mock the external object class
		square.setSquared(n, 3;); //call the squared method
		Mockito.verify(n, Mockito.times(1)).setVal(9); //verify that mocked number item's value has been set (1) times and is equal to 9
		
		//WHY?
		//Instead of checking state change in a mocked object, which is impossible to do, we can at least verify that the 
		//method that causes the state change is called with the correct arguments.
	}

}
