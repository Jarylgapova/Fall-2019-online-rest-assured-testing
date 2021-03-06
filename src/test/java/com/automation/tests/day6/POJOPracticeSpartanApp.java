package com.automation.tests.day6;

import com.automation.pojos.Spartan;
import com.automation.utilities.ConfigurationReader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.is;

public class POJOPracticeSpartanApp {

    @BeforeAll
    public static void beforeAll(){
        baseURI = ConfigurationReader.getProperty("SPARTAN.URI");
        authentication = basic("admin", "admin");

    }

    @Test
    public void addSpartanTest(){
        /**
         * {
         *   "gender": "Male",
         *   "name": "Nursultan",
         *   "phone": "123112312312"
         * }
         */

        Map<String, String> spartan = new HashMap<>();
        spartan.put("gender", "Male");
        spartan.put("name", "Nursultan");
        spartan.put("phone", "123112312312");

        RequestSpecification requestSpecification = given().
                auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(spartan);

        Response response = given().
                auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(spartan).
                when().
                post("/spartans").prettyPeek();

        response.then().statusCode(201);
        response.then().body("success", is("A Spartan is Born!"));

        //deserialization
        Spartan spartanResponse = response.jsonPath().getObject("data", Spartan.class);
        Map<String, Object> spartanResponseMap = response.jsonPath().getObject("data", Map.class);

        System.out.println(spartanResponse);
        System.out.println(spartanResponseMap);

        //spartanResponse is a Spartan
        System.out.println(spartanResponse instanceof Spartan);// must be true
    }

    @Test
    public void updateSpartanTeat(){
        int userToUpdate = 267;
        String name = "Nurs";

        //HTTP PUT request to update exiting record, for example exiting spartan.
        //PUT - requires to provide ALL parameters in body
        //PUT requires same body as POST
        //If you miss at least one parameter, it will not work

        Spartan spartan = new Spartan(name, "Male", 123112312312L);

        //get spartan from web service
        Spartan spartanToUpdate = given().
                auth().basic("admin", "admin").
                accept(ContentType.JSON).
                when().
                get("/spartans/{id}", userToUpdate).as(Spartan.class);

        //update property that you need without affecting other properties
        System.out.println("Before update: " + spartanToUpdate);

        spartanToUpdate.setName(name);//change only name

        System.out.println("After update: " + spartanToUpdate);

        //request to update existing user with id 101
        Response response = given().
                auth().basic("admin", "admin").
                contentType(ContentType.JSON).
                body(spartanToUpdate).
                when().
                put("/spartans/{id}", userToUpdate).prettyPeek();

        //verify that status code is 204 after update
        response.then().statusCode(204);
        System.out.println("##############################################");
        //to get user with id 101, the one that we've just updated
        given().
                auth().basic("admin", "admin").
                when().
                get("/spartans/{id}", userToUpdate).prettyPeek().
                then().
                statusCode(200).body("name", is(name));
        //verify that name is correct, after update
    }

    @Test
    @DisplayName("Verify that user can perform PATCH request")
    public void patchUserTest1() {
        // PATCH - partial update of executing record

        int userId = 1; // user to update make user with this id

        // let's put the code to take random user
        // get all spartans
        Response response0 = given().accept(ContentType.JSON).when().get("/spartans");

        // I can save them all into the list
        List<Spartan> allSpartans = response0.jsonPath().getList("", Spartan.class);
        //Spartan.class - data type of collection
        //getList - get JSON body as a list

        Random random = new Random();
        int randomIndex = random.nextInt(allSpartans.size());
        int randomUserId = allSpartans.get(randomIndex).getId();

        System.out.println("NAME BEFORE: " + allSpartans.get(randomIndex).getName());
        userId = randomUserId;//to assign random user id

        System.out.println(allSpartans);


        Map<String , String> update = new HashMap<>();

        update.put("name", "Aidar");
        //this is a request to update user
        Response response = given().contentType(ContentType.JSON).
                body(update).when().patch("/spartans/{id}", userId);

        response.then().assertThat().statusCode(204);


        //after we sent PATCH request, let's make sure that name is updated
        //this is a request to verify that name was updated and status code is correct as well
        given().accept(ContentType.JSON).
                when().get("/spartans/{id}", userId).prettyPeek().
                then().assertThat().statusCode(200).
                body("name", is("Aidar"));
    }
}
