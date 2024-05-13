import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class _03_ApiTestExtract {
    @Test
    public void extractingJsonPath(){
        
      String ulkeAdi=
              given()
                      .when()
                      .get("http://api.zippopotam.us/us/90210")
                      .then()
                      .extract().path("country"); //PATH i country olan degeri EXTRACT yap

        System.out.println("ulkeAdi = " + ulkeAdi);
        Assert.assertEquals(ulkeAdi,"United States"); //alinan deger buna esit mi
      
    }
    @Test
    public void extractingJsonPath2(){
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu testNG Assertion ile doğrulayınız
        String statename=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .extract().path("places[0].state");

        System.out.println("statename= " + statename);
       Assert.assertEquals(statename,"California"); //alinan deger buna esit mi
    }

    @Test
    public void extractingJsonPath3(){
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının place name değerinin  "Beverly Hills"
        // olduğunu testNG Assertion ile doğrulayınız
        String placename=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .extract().path("places[0].'place name'"); //places[0]["place name"] buda olur

        System.out.println("placename= " + placename);
        Assert.assertEquals(placename,"Beverly Hills");
    }
    @Test
    public void extractingJsonPath4(){
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // limit bilgisinin 10 olduğunu testNG ile doğrulayınız.

        int limit=
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .extract().path("meta.pagination.limit"); //

        System.out.println("limit= " + limit);
        Assert.assertTrue(limit==10);

    }

    @Test
    public void extractingJsonPath5(){

        List<Integer> idler=
        given()
                .when()
                .get("https://gorest.co.in/public/v1/users")
                .then()
                .log().body()
                .extract().path("data.id")  // id lerin yer aldigi bir array
        ;
        System.out.println("idler = " + idler);
    }

    @Test
    public void extractingJsonPath6(){
        // Soru : "https://gorest.co.in/public/v1/users"  endpoint in den dönen
        // bütün name leri yazdırınız
    List<String> namelist=
        given()
                .when()
                .get("https://gorest.co.in/public/v1/users")
                .then()
                .log().body()
                .extract().path("data.name")
        ;
        System.out.println("namelist = " + namelist);
    }
    @Test
    public void extractingJsonPathResponsAll(){
        Response donendata=
        given()
                .when()
                .get("https://gorest.co.in/public/v1/users")
                .then()
                //.log().body()
                .extract().response() // dönen tüm data alindi
                ;

        List<Integer>  idler=donendata.path("data.id");
        List<String>   names=donendata.path("data.name");
        int limit=donendata.path("meta.pagination.limit");

        System.out.println("limit = " + limit);
        System.out.println("names = " + names);
        System.out.println("idler = " + idler);

        Assert.assertTrue(idler.contains(6880125));
        Assert.assertTrue(names.contains("Karunanidhi Jain"));
        Assert.assertTrue(limit==10);
    }

}
