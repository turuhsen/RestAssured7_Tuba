import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class _02_ApiTest {

    RequestSpecification requestSpec;
    ResponseSpecification responseSpec;

    @BeforeClass
    public void Setup(){
       baseURI="https://gorest.co.in/public/v1"; //when // hazirda tanimlanmis RESTASSURED ait degisken

        requestSpec=new RequestSpecBuilder() //given
                .setContentType(ContentType.JSON)
                .log(LogDetail.URI) //log().uri()
                .build();

        responseSpec=new ResponseSpecBuilder() //then
                .expectStatusCode(200) //statusCode(200)
                .log(LogDetail.BODY) //log().body()
                .expectContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void Test1(){
        given()
                //request özellikleri
                .spec(requestSpec)
                .when()
                .get("/users") //basinda http yok ise baseURI basina eklenir
                //https://gorest.co.in/public/v1/users
                .then()
                //test özellikleri, response özellikleri
                .spec(responseSpec)
                ;

    }
}
