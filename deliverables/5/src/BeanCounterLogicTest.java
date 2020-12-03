import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import gov.nasa.jpf.vm.Verify;
import java.util.Random;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Code by @author Wonsun Ahn
 * 
 * <p>Uses the Java Path Finder model checking tool to check BeanCounterLogic in
 * various modes of operation. It checks BeanCounterLogic in both "luck" and
 * "skill" modes for various numbers of slots and beans. It also goes down all
 * the possible random path taken by the beans during operation.
 */

public class BeanCounterLogicTest {
	private static BeanCounterLogic logic; // The core logic of the program
	private static Bean[] beans; // The beans in the machine
	private static String failString; // A descriptive fail string for assertions

	private static int slotCount; // The number of slots in the machine we want to test
	private static int beanCount; // The number of beans in the machine we want to test
	private static boolean isLuck; // Whether the machine we want to test is in "luck" or "skill" mode

	/**
	 * Sets up the test fixture.
	 */
	@BeforeClass
	public static void setUp() {
		if (Config.getTestType() == TestType.JUNIT) {
			slotCount = 5;
			beanCount = 3;
			isLuck = true;
		} else if (Config.getTestType() == TestType.JPF_ON_JUNIT) {
			/*
			 * TODO: Use the Java Path Finder Verify API to generate choices for slotCount,
			 * beanCount, and isLuck: slotCount should take values 1-5, beanCount should
			 * take values 0-3, and isLucky should be either true or false. For reference on
			 * how to use the Verify API, look at:
			 * https://github.com/javapathfinder/jpf-core/wiki/Verify-API-of-JPF
			 */
			slotCount = Verify.getInt(1, 5);
			beanCount = Verify.getInt(0, 3);
			isLuck = Verify.getBoolean();
		} else {
			assert (false);
		}

		// Create the internal logic
		logic = BeanCounterLogic.createInstance(slotCount);
		// Create the beans
		beans = new Bean[beanCount];
		for (int i = 0; i < beanCount; i++) {
			beans[i] = Bean.createInstance(slotCount, isLuck, new Random(42));
		}

		// A failstring useful to pass to assertions to get a more descriptive error.
		failString = "Failure in (slotCount=" + slotCount
				+ ", beanCount=" + beanCount + ", isLucky=" + isLuck + "):";
	}

	/**
	 * Tears down the test fixture.
	 */
	@AfterClass
	public static void tearDown() {
		slotCount = 0;
		beanCount = 0;
		isLuck = false;
	}

	/**
	 * Test case for void void reset(Bean[] beans).
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 * Invariants: If beanCount is greater than 0,
	 *             remaining bean count is beanCount - 1
	 *             in-flight bean count is 1 (the bean initially at the top)
	 *             in-slot bean count is 0.
	 *             If beanCount is 0,
	 *             remaining bean count is 0
	 *             in-flight bean count is 0
	 *             in-slot bean count is 0.
	 */
	@Test
	public void testReset() {
		// TODO: Implement
		//Execution
		logic.reset(beans);
		
		for (int i = 0; i < beanCount; i++) {
			//invariants
			//i = current bean in bean count
			if (beanCount > 0) {
				//remaining bean count is beancount -1
				assertEquals(logic.getRemainingBeanCount(), beanCount - 1);
				//in flight bean count is 1
				//only bean is bean ready to drop at position (0,0)
				if (logic.getInFlightBeanXPos(0) != -1) {
					assertEquals(logic.getInFlightBeanXPos(0), 0);
				}
				//in slot bean count is 0
				//loop through all slots and assert that they are empty
				for (int j = 0; j < slotCount; j++) {
					assertEquals(logic.getSlotBeanCount(j), 0);
				}
			} else {
				//remaining bean count is 0
				assertEquals(logic.getRemainingBeanCount(), 0);
				//in flight bean count is 0
				assertEquals(logic.getInFlightBeanXPos(0), -1);
				//in slot bean count is 0
				for (int j = 0; j < slotCount; j++) {
					assertEquals(logic.getSlotBeanCount(j), 0);
				}
			}
		}
	}

	/**
	 * Test case for boolean advanceStep().
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: After each advanceStep(),
	 *             all positions of in-flight beans are legal positions in the logical coordinate system.
	 */
	@Test
	public void testAdvanceStepCoordinates() {
		// TODO: Implement
		//Execution
		logic.reset(beans);
		//while you can advance
		while (logic.advanceStep()) {
			//check each bean position in flight
			//make sure its in a valid range
			for (int i = 0; i < slotCount; i++) {
				if (logic.getInFlightBeanXPos(i) != -1) {
					assertTrue(logic.getInFlightBeanXPos(i) > -1 
							&& logic.getInFlightBeanXPos(i) < slotCount);
				}
			}
		}
	}

	/**
	 * Test case for boolean advanceStep().
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: After each advanceStep(),
	 *             the sum of remaining, in-flight, and in-slot beans is equal to beanCount.
	 */
	@Test
	public void testAdvanceStepBeanCount() {
		// TODO: Implement
		logic.reset(beans);
		while (logic.advanceStep()) {
			int slottedBeans = 0;
			int inFlightBeans = 0;
			for (int i = 0; i < slotCount; i++) {
				slottedBeans += logic.getSlotBeanCount(i);
				if (logic.getInFlightBeanXPos(i) != -1) {
					inFlightBeans++;
				}
			}
			assertEquals((logic.getRemainingBeanCount() + slottedBeans + inFlightBeans), beanCount);
		}
	}

	/**
	 * Test case for boolean advanceStep().
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: After the machine terminates,
	 *             remaining bean count is 0
	 *             in-flight bean count is 0
	 *             in-slot bean count is beanCount.
	 */
	@Test
	public void testAdvanceStepPostCondition() {
		logic.reset(beans);
		
		boolean ret = true;
		while (ret) {
			ret = logic.advanceStep();
		}
		
		int sum = 0;
		assertEquals(logic.getRemainingBeanCount(), 0);
		for (int i = 0; i < slotCount; i++) {
			assertEquals(logic.getInFlightBeanXPos(i), -1);
			sum += logic.getSlotBeanCount(i);
		}
		assertEquals(sum, beanCount);
	}
	
	/**
	 * Test case for void lowerHalf()().
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Call logic.lowerHalf().
	 * Invariants: After calling logic.lowerHalf(),
	 *             slots in the machine contain only the lower half of the original beans.
	 *             Remember, if there were an odd number of beans, (N+1)/2 beans should remain.
	 *             Check each slot for the expected number of beans after having called logic.lowerHalf().
	 */
	@Test
	public void testLowerHalf() {
		logic.reset(beans);
		
		boolean ret = true;
		while (ret) {
			ret = logic.advanceStep();
		}
		
		logic.lowerHalf();
		
		int numBeans = beanCount / 2;
		
		if (beanCount % 2 != 0) {
			numBeans = (beanCount + 1) / 2;
		}
		
		for (int i = slotCount - 1; i >= 0; i--) {
			numBeans -= logic.getSlotBeanCount(i);
		}
		
		assertEquals(numBeans, 0);
	}
	
	/**
	 * Test case for void upperHalf().
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Call logic.lowerHalf().
	 * Invariants: After calling logic.upperHalf(),
	 *             slots in the machine contain only the upper half of the original beans.
	 *             Remember, if there were an odd number of beans, (N+1)/2 beans should remain.
	 *             Check each slot for the expected number of beans after having called logic.upperHalf().
	 */
	@Test
	public void testUpperHalf() {
		logic.reset(beans);
		
		boolean ret = true;
		while (ret) {
			ret = logic.advanceStep();
		}
		
		logic.upperHalf();
		
		int numBeans = beanCount / 2;
		
		if (beanCount % 2 != 0) {
			numBeans = (beanCount + 1) / 2;
		}
		
		for (int i = slotCount - 1; i >= 0; i--) {
			numBeans -= logic.getSlotBeanCount(i);
		}
		
		assertEquals(numBeans, 0);
	}
	
	/**
	 * Test case for void repeat().
	 * Preconditions: None.
	 * Execution steps: Call logic.reset(beans).
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 *                  Call logic.repeat();
	 *                  Call logic.advanceStep() in a loop until it returns false (the machine terminates).
	 * Invariants: If the machine is operating in skill mode,
	 *             bean count in each slot is identical after the first run and second run of the machine. 
	 */
	@Test
	public void testRepeat() {
		int[] slotCounts = new int[slotCount];
		
		logic.reset(beans);
		boolean ret = true;
		while (ret) {
			ret = logic.advanceStep();
		}
		
		if (!isLuck) {
			for (int i = 0; i < slotCounts.length; i++) {
				slotCounts[i] = logic.getSlotBeanCount(i);
			}
		}
		
		logic.repeat();
		ret = true;
		while (ret) {
			ret = logic.advanceStep();
		}
		
		if (!isLuck) {
			for (int i = 0; i < slotCounts.length; i++) {
				assertEquals(slotCounts[i], logic.getSlotBeanCount(i));
			}
		}
	}
	
	/**
	 * Test case for double getAverageSlotBeanCount()
	 * preconditions: None
	 * execution: 	Call logic.reset(beans)
	 * 				Call logic advanceStep() in loop until the machine terminates
	 * invariants:	After the game has run, calling 
	 * 				getAverageSlotBeanCount() should return
	 * 				the correct average of beans per slot regardless of game mode
	 */
	@Test
	public void testGetAverageSlotBeanCount() {
		//execution
		int total = 0;
		int testAverage = 0;
		double average = 0.0;
		logic.reset(beans);
		//keep the game running until it finishes
		while (logic.advanceStep()) {
			continue;
		}
		
		//compute own average
		for (int i = 0; i < slotCount; i++) {
			testAverage += i * logic.getSlotBeanCount(i);
			total += logic.getSlotBeanCount(i);
		}
		
		if (total == 0) {
			assertTrue(logic.getAverageSlotBeanCount() == 0);
		} else {
			average = (double) testAverage / total;
			assertTrue(logic.getAverageSlotBeanCount() == average);
		}
	}
}
