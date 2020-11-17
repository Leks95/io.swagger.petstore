package io.swagger.petstore;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.Matchers;
import org.junit.Test;

public class TestPet {

    @Test
    public void petTest(){
        String idTestValue = RandomStringUtils.randomNumeric(5);
        RestAssured.given()
                .baseUri("https://petstore.swagger.io/")
                .basePath("/v2/pet")
                .contentType(ContentType.JSON)
                .header("api_key", "888999aaa")
                .body("{\n" +
                        "  \"id\": "+ idTestValue +",\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"string\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")
                .when().post()
                .then()
//                .extract().response()
//                .statusCode(200)
                .body("id", Matchers.equalTo(Integer.valueOf(idTestValue)))
//                .prettyPrint()
            ;
    }
}
