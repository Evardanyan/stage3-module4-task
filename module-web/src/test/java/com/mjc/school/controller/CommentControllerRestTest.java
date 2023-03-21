package com.mjc.school.controller;

import com.jayway.restassured.RestAssured;
//import io.restassured.RestAssured;
import org.junit.BeforeClass;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.given;
//import static io.restassured.RestAssured.given;

public class CommentControllerRestTest {

    @BeforeClass
    public static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/v1";
    }

    @Test
    public void testGetComments() {
        given().get("/comments").then().assertThat().statusCode(200);
    }
}
