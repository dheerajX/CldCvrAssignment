package com.cldcvr.junit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;

@RunWith(SerenityRunner.class)
public class StackExchange {

	@Before
	public void init() {
		RestAssured.baseURI = "https://api.stackexchange.com/2.2/";
	}

	@Test
	public void getBadgePositveFlow() {
		SerenityRest.given().when().get("badges/tags?order=desc&sort=rank&site=stackoverflow").then().log().all()
				.statusCode(200);
	}

	// Expecting this one to fail but its not failing
	@Test
	public void getBadgeWithWrongSiteName() {
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Type", "wwrsfksjdfnkjs");
		SerenityRest.given().headers(header).when().get("badges/tags?order=desc&sort=rank&site=stackover").then()
				.statusCode(400);
	}

	@Test
	public void getBadgeWithWrongSortCode() {
		SerenityRest.given().when().get("badges/tags?order=desc&sort=wrong&site=stackover").then().statusCode(400);

	}

}
