package Campus;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class _06_CountryTest {

    RequestSpecification reqSpec;
    Faker randomUretici = new Faker();
    String countryName="";
    String countryCode="";
    String countryID="";

    @BeforeClass
    public void LoginCampus() {
        baseURI = "https://test.mersys.io";
        // token cookies icinde geldi
        //cookies i alicam
        //request spec hazirlayacagim
        //encironment varible:baseURI
        //{"username": "turkeyts", "password": "TechnoStudy123","rememberMe": "true"}
        String userCredential = "{\"username\": \"turkeyts\", \"password\": \"TechnoStudy123\",\"rememberMe\": \"true\"}";
        Map<String, String> userCredMap = new HashMap<>();
        userCredMap.put("username", "turkeyts");
        userCredMap.put("password", "TechnoStudy123");
        userCredMap.put("rememberMe", "true");

        Cookies gelenCookies =
                given()
                        //.body(userCredential)
                        .body(userCredMap)
                        .contentType(ContentType.JSON)
                        .when()
                        .post("/auth/login")
                        .then()
                        //.log().all()
                        .statusCode(200)
                        .extract().response().getDetailedCookies();
        //System.out.println("gelenCookies = " + gelenCookies);

        reqSpec = new RequestSpecBuilder()
                .addCookies(gelenCookies)
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test
    public void CreateCountry() {
        //burada gelen tooken ın yine cookies içinde geri gitmesi lazım :spec

        countryName=randomUretici.address().country()+randomUretici.address().countryCode()+randomUretici.address().latitude();
        countryCode=randomUretici.address().countryCode();

        Map<String,String> newCountry=new HashMap<>();
        newCountry.put("name",countryName);
        newCountry.put("code",countryCode);

        //Not: Spec bilgileri givendan hemen sonra yazılmalı!
        countryID=
        given()
                .spec(reqSpec)  // gelen cookies, yeni istek için login olduğumun kanıtı olarak gönderildi.
                .body(newCountry)
                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(201)
                .extract().path("id")
                ;

    }
    @Test(dependsOnMethods = "CreateCountry")
    public void CreateCountryNegative(){
        // Yukarıda gönderilen name ve codu tekrar göndererek kayıt yapılamadığını doğrulayınız.
        //burada gelen tooken ın yine cookies içinde geri gitmesi lazım:spec
        Map<String,String>  reNewContry=new HashMap<>();
        reNewContry.put("name",countryName);
        reNewContry.put("code",countryCode);

        given()
                .spec(reqSpec)  // gelen cookies, yeni istek için login olduğumun kanıtı olarak gönderildi.
                .body(reNewContry)
                .when()
                .post("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(400)
        ;

    }
    @Test (dependsOnMethods = "CreateCountryNegative")
    public void UpdateCountry() {
        // yukarıda create edilen ülkenin adını bir başka ülke adı ile update ediniz.
        // benim id ye ihtiyacım var, id nerde oluşuyor
        String updCountryName="ismet"+randomUretici.address().country()+
                randomUretici.address().latitude();

        Map<String, String> updCountry = new HashMap<>();
        updCountry.put("id", countryID);
        updCountry.put("name", updCountryName);
        updCountry.put("code", countryCode);

        given()
                .spec(reqSpec)
                .body(updCountry)
                .when()
                .put("/school-service/api/countries")

                .then()
                .log().body()
                .statusCode(200)
                .body("name",equalTo(updCountryName)) // gönderdiginiz ülke adinin, dönen body ülke adiyla ayni
        ;

        // TODO: bu örneğe gönderdiğiniz ülke adının , dönen body deki ülke adıyla aynı
        // olup olmadığı testini ekleyiniz.
    }
    @Test(dependsOnMethods = "UpdateCountry")
    public void DeleteCountry()
    {
        // TODO : DELETECountry yi yapınız
        // TODO : DELETECountryNegative i yapınız.
        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/countries/"+countryID)

                .then()
                .statusCode(200)
        ;

    }

    @Test(dependsOnMethods = "DeleteCountry")
    public void DeleteCountryNegative()
    {        // TODO : DELETECountryNegative i yapınız.
        given()
                .spec(reqSpec)
                .when()
                .delete("/school-service/api/countries/"+countryID)
                .then()
                .statusCode(400)
        ;

    }

}
