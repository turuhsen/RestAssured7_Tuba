package GoRest;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class _08_GoRestCommentTest {
    //     {
//          "id": 95069,
//             "post_id": 122490,
//             "name": "Malti Kaur",
//             "email": "kaur_malti@strosin-keebler.example",
//             "body": "In ut vel."
//     }

    RequestSpecification reqSpec;
    Faker randomuretici = new Faker();
    int commentID=0;

    @BeforeClass
    public void Setup() {

        baseURI = "https://gorest.co.in/public/v2/comments";
        reqSpec = new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer b550499f732a6084eb3dbd5071b3529ed3f8883f712bd8dee1bd6154f8775777")
                .setContentType(ContentType.JSON)
                .build();

    }

    //Soru:CreateComment testini yapiniz
    @Test
    public void CreateComment(){
        String fullname=randomuretici.name().fullName();
        String email=randomuretici.internet().emailAddress();
        String body=randomuretici.lorem().paragraph();
        String postId="122490";

        Map<String ,String> newComment=new HashMap<>();
        newComment.put("name",fullname);
        newComment.put("email",email);
        newComment.put("body",body);
        newComment.put("post_id",postId);
        
        commentID=
        given()
                .spec(reqSpec)
                .body(newComment)
                .when()
                .post("")
                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")
                ;

        System.out.println("commentID = " + commentID);
    }

    @Test(dependsOnMethods = "CreateComment")
    public void GetCommendById(){
        given()
                .spec(reqSpec)
                .when()
                .get("/"+commentID)
                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(commentID))
                ;
    }

    @Test(dependsOnMethods = "GetCommendById")
    public void UpdateComment(){
        String updFullname=randomuretici.name().fullName();
        Map<String,String>  uodcomment=new HashMap<>();
        uodcomment.put("name",updFullname);

        given()
                .spec(reqSpec)
                .body(uodcomment)
                .when()
                .put(""+commentID)
                .then()
                .log().body()
                .statusCode(200)
                .body("id",equalTo(commentID))
                .body("name",equalTo(updFullname))
                ;

    }

    @Test(dependsOnMethods = "UpdateComment")
    public void DeleteComment(){

        given()
                .spec(reqSpec)
                .when()
                .delete(""+commentID)
                .then()
                .log().body()
                .statusCode(204)
                ;

    }
    @Test(dependsOnMethods = "DeleteComment")
    public void DeleteCommentNegativ(){
        given()
                .spec(reqSpec)
                .when()
                .delete(""+commentID)
                .then()
                .log().body()
                .statusCode(404)
                ;
    }
}
