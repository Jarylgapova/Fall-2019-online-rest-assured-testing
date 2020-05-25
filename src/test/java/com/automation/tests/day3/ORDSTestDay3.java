package com.automation.tests.day3;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class ORDSTestDay3 {

    @BeforeAll
    public static void setup(){
        baseURI = "http://54.224.118.38:1000/ords/hr/";
    }

    /**
     * given resource path is "/regions/{id}"
     * when user makes get request
     * and region id is equals to 1
     * then assert that status code is 200
     * and assert that region name is Europe
     * and assert that region id is 1
     */
    @Test
    public void verifyFirstRegion(){
        given().pathParam("id", 1).
                when().get("/regions/{id}").prettyPeek().
                then().assertThat().statusCode(200).
                body("region_name", is("Europe")).
                body("region_id", is(1)).
                time(lessThan(5L), TimeUnit.SECONDS); //verify that response time is less than 5 sec

    }

    @Test
    public void verifyEmployee() {
        Response response = given().accept(ContentType.JSON).
                when().get("/employees");

        JsonPath jsonPath = response.jsonPath();

        String nameOfFirstEmployee = jsonPath.getString("items[0].first_name");//0 - to get first item in the list
        String nameOfLastEmployee = jsonPath.getString("items[-1].first_name");//-1 - to get last item in the list, like length() - n

        System.out.println("First name first employee: " + nameOfFirstEmployee);

        System.out.println("First name of the last employee: " + nameOfLastEmployee );

        Map<String, ?> firstEmployee = jsonPath.get("items[0]");
        System.out.println(firstEmployee);
    }
}
