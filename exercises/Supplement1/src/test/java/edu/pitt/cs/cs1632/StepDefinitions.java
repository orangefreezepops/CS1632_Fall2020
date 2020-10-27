package edu.pitt.cs.cs1632;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.junit.Assert.*;

public class StepDefinitions {
	private RentACat r;
	private String listResult;
	private int cat_id;
	private boolean rented;
	private boolean returned;
	
	// TODO: Add more member variables and methods as necessary

	@Given("a rent-a-cat facility")
	public void aRentACatFacility() {
		r = RentACat.createInstance();
	}
	
	@Given("no cats")
	public void noCats() {
		// nothing to do really
	}
	
	@Given("a cat with ID {int} and name {string}")
	public void aCatWithIDAndName(Integer id, String name) {
		r.addCat(new Cat(id, name));
		System.out.println("Created cat " + id + ". " + name);
	}
	
	@When("I list the cats")
	public void iListTheCats() {
		listResult = r.listCats();
	}
	
	@When("I rent cat number {int}")
	public void iRentCatNumber(Integer id) {
		// TODO: Implement
		cat_id = id;
		rented = r.rentCat(id);
	}
	
	@When("I return cat number {int}")
	public void iReturnCatNumber(Integer id) {
		// TODO: Implement
		returned = r.returnCat(id);
	}
	
	@Then("the listing is: {string}")
	public void theListingIs(String result) {
		assertEquals(result.replaceAll("\\\\n", "\n"), listResult);
	}
	
	@Then("the rent is successful")
	public void theRentIsSuccessful() {
		// TODO: Implement
		assertTrue(rented);
	}

	@Then("the rent is unsuccessful")
	public void theRentIsUnsuccessful() {
		// TODO: Implement
		assertFalse(rented);
	}
	
	@Then("the return is successful")
	public void theReturnIsSuccessful() {
		// TODO: Implement
		assertTrue(returned);
	}

	@Then("the return is unsuccessful")
	public void theReturnIsUnsuccessful() {
		// TODO: Implement
		assertFalse(returned);
	}
}
