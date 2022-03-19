package Day8;

import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class BookItAuthTest {

    @BeforeClass
    public void before(){
        baseURI = "https://cybertek-reservation-api-qa2.herokuapp.com";
    }

    String accessToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxNDIwIiwiYXVkIjoic3R1ZGVudC10ZWFtLW1lbWJlciJ9.jGs3jHQtTzXgRYVyIgciD_FdCEVpHyx05sp4m6t8wR8";

    @Test
    public void getAllCampuses(){

        Response response = given().header("Authorization",accessToken).
                when().get("/api/campuses");

        response.prettyPrint();
        System.out.println(response.statusCode());

    }
}