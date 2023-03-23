package com.mjc.school.controller;


import com.mjc.school.repository.impl.CommentRepository;
import com.mjc.school.service.impl.CommentService;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;

public class CommentControllerRestTest {
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    private Long commentId;


    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/v1";
        commentService = mock(CommentService.class);
//        commentRepository = mock(CommentRepository.class);

        Map<String, Object> comment = new HashMap<>();
        comment.put("content", "xyx1111");
        comment.put("newsId", "90");


        int id = given()
                .contentType("application/json")
                .body(comment)
                .when().post("/comments")
                .then()
                .statusCode(201)
                .extract().path("id"); // Assumes that the response contains the ID field

        commentId = Long.valueOf(id);

        System.out.println("Created comment ID: " + commentId);


    }

    @Test
    public void testGetComments() {
        given().get("/comments").then().assertThat().statusCode(200);
    }


    @Test
    public void testGetCommentsById() {
        given().get("/comments/3").then().assertThat().statusCode(200);
    }

    @Test
    public void testCreateComment() {
        Map<String, String> comment = new HashMap<>();
        comment.put("content", "xyx1111");
        comment.put("newsId", "90");

        given()
                .contentType("application/json")
                .body(comment)
                .when().post("/comments").then()
                .statusCode(201);

    }

    @Test
    public void testCreateTComment() {
        Map<String, String> comment = new HashMap<>();
        comment.put("content", "xyx1111");
        comment.put("newsId", "90");

        // Extract the ID from the API response
//       int commentId = given()
       int id = given()
                .contentType("application/json")
                .body(comment)
                .when().post("/comments")
                .then()
                .statusCode(201)
                .extract().path("id"); // Assumes that the response contains the ID field

        commentId = Long.valueOf(id);
        System.out.println(id);

        System.out.println("Created comment ID: " + commentId);
    }


    @Test
    public void testUpdateComment() {
        System.out.println(commentId);
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("id", commentId);
        requestBody.put("content", "updated content");
        requestBody.put("newsId", null);

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/comments")
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    public void testDeleteComment() {
        // Assuming you have the commentId you want to delete
        Long commentIdToDelete = commentId; // Replace this with the actual commentId you want to delete

        given()
                .contentType("application/json")
                .when()
                .delete("/comments/" + commentIdToDelete)
                .then()
                .statusCode(204); // No Content

        // Now, try to get the deleted comment to check if it has been deleted
        given()
                .contentType("application/json")
                .when()
                .get("/comments/" + commentIdToDelete)
                .then()
                .statusCode(404); // Not Found it trhow another error and  show 422
    }


}
