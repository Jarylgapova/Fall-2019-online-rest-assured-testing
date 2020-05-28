package com.automation.tests.day6;

import com.automation.pojos.Employee;
import com.automation.pojos.Link;
import com.automation.utilities.ConfigurationReader;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.get;

public class POJOPracticeORDS {

    @BeforeAll
    public static void setup(){
        baseURI = ConfigurationReader.getProperty("ORDS.URI");

    }

    @Test
    public void getEmployeeTest(){
        Response response = get("/employees/{id}", 100).prettyPeek();

        Employee employee = response.as(Employee.class);
        System.out.println(employee);

        List<Link> links = employee.getLinks();
        System.out.println(links);
    }
}
