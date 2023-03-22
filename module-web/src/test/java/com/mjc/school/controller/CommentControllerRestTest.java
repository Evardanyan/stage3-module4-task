package com.mjc.school.controller;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;




public class CommentControllerRestTest {

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/v1";
    }

    @Test
    public void testGetComments() {
        given().get("/comments").then().assertThat().statusCode(200);
    }


    @Test
    public void testGetCommentsById() {
        given().get("/comments/3").then().assertThat().statusCode(200);
    }


}
