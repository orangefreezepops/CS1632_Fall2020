package DeathStar;

import static org.junit.Assert.*;
import org.mockito.*;

import org.junit.Test;

public class DeathStarTest {

	/*
	 * Preconditions: A new DeathStar deathStar is created
	 *                A new Planet planet is created with hit points 10
	 * Execution Steps: shoot planet with deathStar
	 * PostConditions: Return value of deathStar.shoot(planet) is "Wimpy planet was hit by the superlaser!"
	 *                 Planet receives a damage of 100 hit points
	 */
	@Test
	public void testGetDamageShootTwice() {
		// TODO: Fill in!
		DeathStar ds = new DeathStar(); //new death star
		Planet p = Mockito.mock(Planet.class); //mocked planet
		Mockito.when(p.getHitPoints()).thenReturn(10); //mock the number of hitpoints for the planet
		Mockit.when(p.toString()).thenReturn("Wimpy planet"); //mock the toString method
		String ret = ds.shoot(p); //shoot the planet
		assertEquals("Wimpy planet was hit by the superlaser!", ret); //verify the return string afte being shot
		Mockito.verify(p).damage(100); //verify the damage was 100
	}
}
