package com.automation.tests.day2;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class ORDSTest {

    String BASE_URL = "http://54.224.118.38:1000/ords/hr/";

    @Test
    @DisplayName("Get list of all employees")
    public void getAllEmployees(){

    //response can be saved in the Response object
    //prettyPeek() - method that prints response info in nice format
    // also this method returns Response method
    //Response contains body, header and status line
    //body(playlaod) - contains content that we requested from the web service
    // header -contains meta data
    Response response=  given().baseUri(BASE_URL).when().get("/employees").prettyPeek();

        /**
         * RestAssured request has similar structure to BDD scenario:
         *
         * Start building the request part of the test
         * given() - used for request setup and authentication
         * Syntactic sugar,
         * when() - to specify type of HTTP  request: get, put, post, delete, patch. head, etc..
         * then() - to verify response, perform assertion
         */
    }

    @Test
    @DisplayName("Get employee under specific ID")
    public void getOneEmployee(){
        //in URL we can specify path and query parameters
        //path parameters are used to retrieve specific resource: for example 1 employee not all of them
        //{id} - path variable, that will be replace with a value after comma
        //after when() we specify HTTP request type/method/verb
        //The path parameters. E.g. if path is "/book/{hotelId}/{roomNumber}" you can do <code>get("/book/{hotelName}/{roomNumber}", "Hotels R Us", 22);</code>.
        Response response = given().baseUri(BASE_URL).when().get("/employees/{id}",100).prettyPeek();

        int  statusCode= response.statusCode();
        response.then().statusCode(statusCode); // to verify that status code is 200

        Assertions.assertEquals(200, statusCode);


        //if assertions fails, you will get this kind of message:
        /**
         * java.lang.AssertionError: 1 expectation failed.
         * Expected status code <201> but was <200>.
         * 200 is always expected status code after GET requset
         */
    }

    @Test
    @DisplayName("Dispalay employee with statuscode 200")
    public void getEmployeeWithStatusCode200(){
        given().baseUri(BASE_URL).when().get("/countries").prettyPeek().then().statusCode(200);


    }

}
