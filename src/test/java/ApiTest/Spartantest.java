package ApiTest;

import  io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Spartantest {

    String spartanurl="http://18.233.164.111:8000";
    @Test
    public void Test1(){
        Response response = RestAssured.get(spartanurl + "/api/spartans");
        System.out.println("response.statusCode() = " + response.statusCode());

        System.out.println("response.body().asString() = " + response.body().prettyPrint());


    }

    @Test
    public void Test2(){
        Response response = RestAssured.get(spartanurl + "/api/spartans");

        System.out.println("response.statusCode() = " + response.statusCode());
        Assert.assertEquals(response.statusCode(),200);
      Assert.assertTrue(response.body().asString().contains("Allen"));

    }

    @Test
    public void test3(){
        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get(spartanurl + "/api/spartans");

       Assert.assertEquals(response.statusCode(),200);
       Assert.assertEquals(response.contentType(),"application/json");

    }

    @Test
    public void test4(){

        Response response = RestAssured.given().accept(ContentType.JSON).when().
                pathParam("id", 18).get(spartanurl + "/api/spartans/{id}");

        Assert.assertEquals(response.statusCode(),200);
        Assert.assertEquals(response.contentType(),"application/json");
        Assert.assertTrue(response.body().asString().contains("Allen"));
            response.prettyPrint();
    }


    @Test
    public void test5(){

        Response response = RestAssured.given().accept(ContentType.JSON)
                .and().pathParam("id", 500)
                .get(spartanurl + "/api/spartans/search");

        Assert.assertEquals(response.statusCode(),404);
        Assert.assertEquals(response.contentType(),"application/json");
        Assert.assertTrue(response.body().asString().contains("Not Found"));


    }

    @Test
    public void test6(){

        Response response = RestAssured.given().accept(ContentType.JSON).and().queryParam("gender", "Female").and()
                .queryParam("nameContains", "J")
                .when().get(spartanurl + "/api/spartans/search");
         //   response.body().prettyPrint();
            Assert.assertEquals(response.statusCode(),200);
            Assert.assertEquals(response.contentType(),"application/json");
            Assert.assertTrue(response.body().asString().contains("Female"));
              Assert.assertTrue(response.body().asString().contains("Janette"));


    }

        @Test
    public void test7(){

            Map<String,Object> param= new HashMap<>();
            param.put("gender","Female");
            param.put("nameContains","J");

            Response response = RestAssured.given().accept(ContentType.JSON)
                    .when().params("gender", "Female")
                    .params("nameContains", "J")
                    .when().get(spartanurl + "/api/spartans/search");


            Assert.assertEquals(response.statusCode(),200);
            Assert.assertEquals(response.contentType(),"application/json");
            Assert.assertTrue(response.body().asString().contains("Female"));
            Assert.assertTrue(response.body().asString().contains("Janette"));


        }

        @Test
    public void setup(){

            Response id = RestAssured.given().accept(ContentType.JSON).when()
                    .pathParam("id", 10)
                    .when().get(spartanurl + "/api/spartans/{id}");

        Assert.assertEquals(id.statusCode(),200);
        Assert.assertEquals(id.contentType(),"application/json");
        Assert.assertTrue(id.prettyPrint().contains("10"));

            System.out.println("id.path(\"id\",10) = " + id.path("id"));

        }

        @Test
    public void testpath(){

            Response response1 = RestAssured.get(spartanurl + "/api/spartans");

            int firstId= response1.path("id[0]");
            System.out.println("firstId = " + firstId);

            List<String> names= response1.path("names");



        }

@Test
    public void JsanPath(){
    Response response = RestAssured.given().accept(ContentType.JSON)
            .when().pathParam("id", 11)
            .and().get(spartanurl + "/api/spartans/{id}");

            Assert.assertEquals(response.statusCode(),200);
            Assert.assertEquals(response.contentType(),"application/json");
    JsonPath json = response.jsonPath();
    int id = json.getInt("id");
    String name= json.getString("name");
    String gender= json.getString("gender");
    long phone = json.getLong("phone");

    System.out.println("id = " + id);
    System.out.println("name = " + name);
    System.out.println("gender = " + gender);
    System.out.println("phone = " + phone);

    Assert.assertEquals(id,11);
    Assert.assertEquals(name,"Nona");
    Assert.assertEquals(gender,"Female");
    Assert.assertEquals(phone,7959094216l);

}

@Test
    public void Hamcrest(){

     RestAssured.given().accept(ContentType.JSON)
            .pathParam("id", 15)
            .when().get(spartanurl + "/api/spartans/{id}")
             .then().statusCode(200).and()
             .assertThat().contentType("application/json");




}
    @Test
    public void Hamcrest1(){

        RestAssured.given().accept(ContentType.JSON)
                .pathParam("id", 15)
                .when().get(spartanurl + "/api/spartans/{id}")
                .then().statusCode(200).and()
                .assertThat().contentType("application/json")
                .and().assertThat().body("id", Matchers.equalTo(15),"name"
                        ,Matchers.equalTo("Meta"),"gender",Matchers.equalTo("Female"),"phone",
                        Matchers.equalTo(1938695106));

    }



}
