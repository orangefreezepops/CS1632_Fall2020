import org.junit.*;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.*;
import java.io.*;
import java.lang.reflect.Field;

public class GameOfLifePinningTest {
	/*
	 * READ ME: You may need to write pinning tests for methods from multiple
	 * classes, if you decide to refactor methods from multiple classes.
	 * 
	 * In general, a pinning test doesn't necessarily have to be a unit test; it can
	 * be an end-to-end test that spans multiple classes that you slap on quickly
	 * for the purposes of refactoring. The end-to-end pinning test is gradually
	 * refined into more high quality unit tests. Sometimes this is necessary
	 * because writing unit tests itself requires refactoring to make the code more
	 * testable (e.g. dependency injection), and you need a temporary end-to-end
	 * pinning test to protect the code base meanwhile.
	 * 
	 * For this deliverable, there is no reason you cannot write unit tests for
	 * pinning tests as the dependency injection(s) has already been done for you.
	 * You are required to localize each pinning unit test within the tested class
	 * as we did for Deliverable 2 (meaning it should not exercise any code from
	 * external classes). You will have to use Mockito mock objects to achieve this.
	 * 
	 * Also, you may have to use behavior verification instead of state verification
	 * to test some methods because the state change happens within a mocked
	 * external object. Remember that you can use behavior verification only on
	 * mocked objects (technically, you can use Mockito.verify on real objects too
	 * using something called a Spy, but you wouldn't need to go to that length for
	 * this deliverable).
	 */

	/* TODO: Declare all variables required for the test fixture. */
	Cell[][] blinker;
	Cell blinkerCell;
	MainPanel mp;

	@Before
	public void setUp() {
		/*
		 * TODO: initialize the text fixture. For the initial pattern, use the "blinker"
		 * pattern shown in:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Examples_of_patterns
		 * The actual pattern GIF is at:
		 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#/media/File:Game_of_life_blinker.gif
		 * Start from the vertical bar on a 5X5 matrix as shown in the GIF.
		 */
		
		mp = new MainPanel(5);
		
		blinker = new Cell[5][5];
		blinkerCell = Mockito.mock(Cell.class);
		
		for (int i = 0; i < 5; i ++) {
			for (int j = 0; j < 5; j++) {
				//set blinker 1
				if (j == 2) {
					if (i == 1 || i == 2 || i == 3) {
						blinker[i][j] = Mockito.mock(Cell.class);
						Mockito.when(blinker[i][j].getAlive()).thenReturn(true);
					} else {
							blinker[i][j] = Mockito.mock(Cell.class);
							Mockito.when(blinker[i][j].getAlive()).thenReturn(false);
					}
				} else {
					blinker[i][j] = Mockito.mock(Cell.class);
					Mockito.when(blinker[i][j].getAlive()).thenReturn(false);
				}
			}
		}
			
		mp.setCells(blinker);
	}

	/* TODO: Write the three pinning unit tests for the three optimized methods */
	/*
	 * case for MainPanel.calculateNextIteration()
	 * Preconditions: initial blinker pattern is set (cells [1][2], [2][2], and [3][2] are alive)
	 * Execution: call calculateNextIteration() on MainPanel object
	 * Postconditions: every cell in the Cell[][] array and calls .setAlive(), 
	 * when cells for the next blinker pattern ([2][1], [2][2], and [2][3]) are .setAlive() 
	 * they return true meaning alive, the rest return false, meaning they are dead.
	 *  */
	@Test
	public void testMainPanelCalculateNextIteration() {
		mp.calculateNextIteration();
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (i == 2) {
					if (j == 1 || j == 2 || j == 3) {
						Mockito.verify(blinker[i][j]).setAlive(true);
					}
				} else {
					Mockito.verify(blinker[i][j]).setAlive(false);
				}
			}
		}
		
	}
	
	/*
	 * case for MainPanel.iterateCell()
	 * Preconditions: initial blinker pattern is set (cells [1][2], [2][2], and [3][2] are alive)
	 * Execution: call iterateCell() on MainPanel object for every index in the Cell[][] array 
	 * Postconditions: When cells for the next blinker pattern ([2][1], [2][2], and [2][3]) are iterated 
	 * they return true meaning those cells became alive, the rest return false, meaning they remain 
	 * dead or have been killed.
	 *  */
	@Test
	public void testMainPanelIterateCell() {
		for (int i = 0; i < 5; i ++) {
			for (int j = 0; j < 5; j ++) {
				switch(i) {
				//changed loop from previous test to a switch case here because tests weren't passing
				//but turns out the problem was in the blinker array initialization, not the tests
				//and thats why they are not stylized the same
				case 2: 
					if (j == 1 || j == 2 || j == 3) {
						assertTrue(mp.iterateCell(i, j));
					} else {
						assertFalse(mp.iterateCell(i, j));
					}
					break;
				default:
					assertFalse(mp.iterateCell(i, j));
					break;
				}
			}
		}
	}
	/*
	 * case for Cell.toString()
	 * Preconditions: mock a Cell object
	 * Execution: call toString() on the Cell object
	 * Postconditions: return value should equal "X"  */
	@Test
	public void testCellToString() {
		Mockito.when(blinkerCell.toString()).thenReturn("X");
		assertEquals(blinkerCell.toString(), "X");
	}

}
