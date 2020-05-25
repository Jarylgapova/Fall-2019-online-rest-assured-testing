package com.automation.tests.day3;

import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExchangeRatesAPITest {

    /**
     * *  @BeforeAll methods must have a {@code void} return type,
     * * must not be {@code private}, and must be {@code static} by default.
     */
    @BeforeAll
    public static void setup(){
        //for every single request this is a base URI
        baseURI = "http://api.openrates.io";



    }
    // get latest currency rates
    @Test
    public void getLatestRates(){

        // after ? we specify query parameters. If there are couple of them we use & to concatenate them
        //http://www.google.com/index.html?q=apple&zip=123123
        //q - query parameter
        //zip - another query parameter
        //with rest assured, we provide query parameters into given() part.
        //give() - request preparation
        //you can specify query parameters in URL explicitly: http://api.openrates.io/latest?base=USD
        //rest assured, will just assemble URL for you
        Response response = given().queryParam("base", "USD").when().
                get("/latest").prettyPeek();


        //to read header of the response
        Headers headers = response.getHeaders();

        String contentType = headers.getValue("Content-Type");
        System.out.println(contentType);

        //verify tat GET to the endpoint was successful
        response.then().statusCode(200);

        response.then().assertThat().body("base", is("USD"));

        // verify that response contain today's date
        //this line returns today's date in the required format:  yyyy-MM-dd
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        response.then().assertThat().body("date", containsString("2020-05-22"));

    }

    // get history of rates
    @Test
    public void getHistoryOfRate(){
        Response response = given().queryParam("base", "USD").when().get("/2008-01-02").prettyPeek();

        Headers headers = response.getHeaders();
        System.out.println(headers);

        response.then().assertThat().statusCode(200).and().body("date", is("2008-01-02"));

        //and() doesn't have a functional role, it's just a syntax sugar
        //we can chain validations
        //how we can retrieve

        //rates - it's an object
        //all currencies are like instance variables
        //to get any instance variable (property), objectName.propertyName
        float actual = response.jsonPath().get("rates.USD");

        assertEquals(1.0, actual);

    }

}
