package ApiTest;

import static io.restassured.RestAssured.*;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpartanWithHamcrest {

    @BeforeClass
    public void beforeclass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }
    @Test

    public void test1(){

        Response response = given()
                .pathParam("id", 15)
                .when().get(baseURI + "/api/spartans/{id}");

       String str= baseURI + "/spartans/{id}";
        System.out.println("str = " + str);

        response.body().prettyPrint();
        System.out.println("response.statusCode() = " + response.statusCode());

    }

    @Test
    public void test2(){


         given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get(baseURI + "/api/spartans/{id}")
                .then().statusCode(200)
                .and().contentType("application/json")
                .body("id", Matchers.equalTo(15), "name",
                        Matchers.equalTo("Meta")
               , "gender", Matchers.equalTo("Female") , "phone",
                        Matchers.equalTo(1938695106) );
   }


   @Test
    public void TestDesrialization(){

//       Map<String,Object> objectMap= new HashMap<>();
//
//        objectMap.put("name","Meta");
//        objectMap.put("id",15);
//        objectMap.put("gender","Female");
//        objectMap.put("phone",1938695106);

       Gson gson=new Gson();

       Response response = given().accept(ContentType.JSON)
               .and().pathParam("id", 15)
               .when().get(baseURI + "/api/spartans/{id}");


       Map map = response.body().as(Map.class);

       System.out.println("map.get(\"id\") = " + map.get("id"));
       System.out.println("map.get(\"name\") = " + map.get("name"));
       System.out.println("map.get(\"gender\") = " + map.get("gender"));
       System.out.println("map.get(\"phone\") = " + map.get("phone"));





   }

    @Test
    public void TestDesrialization2(){

        Response response = given().accept(ContentType.JSON)
                .and().get(baseURI + "/api/spartans");


        List<Map<String,Object>> list = response.body().as(List.class);
        System.out.println("list.size() = " + list.size());
        System.out.println("list.get(0).get(\"id\") = " + list.get(0).get("id"));


    }
    @Test
    public void TestDesrializationPojo(){

        Response response = given().accept(ContentType.JSON)
                .and().pathParam("id", 15)
                .when().get(baseURI + "/api/spartans/{id}");

        SpartanPojo spartanPojo= response.body().as(SpartanPojo.class);

        System.out.println("spartanPojo.toString() = " + spartanPojo.toString());

        System.out.println("spartanPojo.getId() = " + spartanPojo.getId());


    }

    @Test
    public void TestDesrializationPojoGSON(){

    Gson gson=new Gson();

    String myJsonbody= "{\n" +
            "    \"id\": 15,\n" +
            "    \"name\": \"Meta\",\n" +
            "    \"gender\": \"Female\",\n" +
            "    \"phone\": 1938695106\n" +
            "}";


    // Using gson method do de serialize our json body
SpartanPojo   spartan= gson.fromJson(myJsonbody,SpartanPojo.class);


        System.out.println("spartan.toString() = " + spartan.toString());


        // serilization  Java object-->  JSON BODY

        SpartanPojo json = new SpartanPojo(129, "Samuel","Male",109228866);

        String jsonbody= gson.toJson(json);

        System.out.println("jsonbody = " + jsonbody);


    }

    @Test
    public void PostExample(){

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .and().body("{\n" +
                        "   \n" +
                        "    \"name\": \"Metalica\",\n" +
                        "    \"gender\": \"Female\",\n" +
                        "    \"phone\": 193864557106\n" +
                        "}")
                .when().post(baseURI + "/api/spartans/");

      //  response.prettyPrint();

        JsonPath json=response.jsonPath();
        System.out.println("json.getString(\"data.id\") = " + json.getInt("data.id"));
        System.out.println("json.getString(\"data.name\") = " + json.getString("data.name"));


    }

    @Test
    public void PostExampleMap(){

        Map<String,Object> requestMap = new HashMap<>();
        requestMap.put("name","Michel");
        requestMap.put("gender","Male");
        requestMap.put("phone",193864557106l);


        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(requestMap)
                .when().post(baseURI + "/api/spartans/");

            response.prettyPrint();

    }

    @Test
    public void PostExamplePojo(){

        SpartanPojo spartanPojo= new SpartanPojo();
        spartanPojo.setGender("Male");
        spartanPojo.setName("JohnJohn");
        spartanPojo.setPhone(455566754334l);

        Response response = given().accept(ContentType.JSON)
                .and().contentType(ContentType.JSON)
                .body(spartanPojo)
                .post(baseURI + "/api/spartans/");
        System.out.println("response.statusCode() = " + response.statusCode());

        response.prettyPrint();



    }

}
