package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class _07_GoRestUsersTest {

    RequestSpecification reqSpec;
    Faker randomuretici = new Faker();
    int userID =0;

    //
    @BeforeClass
    public void Setup() {

        baseURI = "https://gorest.co.in/public/v2/users/";
        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer b550499f732a6084eb3dbd5071b3529ed3f8883f712bd8dee1bd6154f8775777")
                .setContentType(ContentType.JSON)
                .build();

    }

    @Test
    public void CreateUser() {
        String rndFullname = randomuretici.name().fullName();
        String rndEmail = randomuretici.internet().emailAddress();

        Map<String, String> newuser = new HashMap<>();
        newuser.put("name", rndFullname);
        newuser.put("gender", "male");
        newuser.put("email", rndEmail);
        newuser.put("status", "active");


              userID= given()
                .spec(reqSpec)
                .body(newuser)
                .when()
                .post("")
                .then()
                .log().body()
                .statusCode(201)
                       .extract().path("id")
        ;
    }
    @Test(dependsOnMethods = "CreateUser")
    public void GetUserById(){

        given()
                .spec(reqSpec)
                .when()
                .get("https://gorest.co.in/public/v2/users/"+userID)
                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
                ;
    }

    @Test(dependsOnMethods = "GetUserById")
    public void UpdateUser(){
        String updtname=randomuretici.name().fullName();
        Map<String, String> upduser = new HashMap<>();
        upduser.put("name",updtname );


        given()
                .spec(reqSpec)
                .body(upduser)
                .when()
                .put("/"+userID)
                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(userID))
                .body("name",equalTo(updtname))
               // .extract().response().statusCode()
        ;
    }

    @Test(dependsOnMethods = "UpdateUser")
    public void deleteUser(){
        given()
                .spec(reqSpec)
                .when()
                .delete("/"+userID)
                .then()
                .log().body()
                .statusCode(204)
                ;
    }
    @Test(dependsOnMethods = "deleteUser")
    public void deleteUsernegative(){
        given()
                .spec(reqSpec)
                .when()
                .delete("/"+userID)
                .then()
                .statusCode(404);
    }
}
