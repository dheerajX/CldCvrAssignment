package com.cldcvr.junit;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
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
	
	@Test
	public void getBadgeWithWrongMinMax() {
		@SuppressWarnings("rawtypes")
		ResponseBody rb = SerenityRest.get("badges?order=desc&min=1&max=010&sort=rank&site=stackoverflow");
		JsonPath jsonPathEvaluator = rb.jsonPath();
		String errorID = jsonPathEvaluator.get("error_id")+"";
		String error_message = jsonPathEvaluator.get("error_message");
		String error_name = jsonPathEvaluator.get("error_name");
		
		// Validation for the body contents
		Assert.assertTrue(errorID.equalsIgnoreCase("400"));
		Assert.assertTrue(error_message.equalsIgnoreCase("min"));
		Assert.assertTrue(error_name.equalsIgnoreCase("bad_parameter"));
		
	}

}
