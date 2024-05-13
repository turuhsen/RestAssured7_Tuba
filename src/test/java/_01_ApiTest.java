import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class _01_ApiTest {
    @Test
    public  void Test1()
    {
        given().
            //Hazirlik kodlari buraya yaziliyor
                when().
                //endpoint(url), metoduyla birlikte istek gönderilme asamasi

                then();
                // assertion, test, data islemleri
    }

    @Test
    public void statusCodeTest(){
        given()
                //gönderilecek bilgiler burada olacak
                .when()
                .get("http://api.zippopotam.us/us/90210") //post,put, delete
                .then()
                .log().body() //gelen body kismini göster,all() dersek her seyi gösterir
                .statusCode(200); //test kismi assertion status 200 mü
                ;
    }

    @Test
    public void contentTypeTest(){
        given()
                .when()

                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body() //dönen body bilgisini göster
                .statusCode(200) //dönen kod 200 mü
                .contentType(ContentType.JSON) // dönen datanin tipi JSON mi
        ;
    }

    @Test
    public void checkCountryResponseBody(){
        given()
                .when()

                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body() //dönen body bilgisini göster
                .statusCode(200) //dönen kod 200 mü
                .body("country", equalTo("United States"))  //country  yi disari almadan
                // bulundu yeri (path i) vererek icerde assertion hamcrest kütüphanesi yapiyor
        ;
    }

    @Test
    public void checkCountryResponseBody2() {
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint indne dönen
        // place dizisinin ilk elemanının state değerinin  "California"
        // olduğunu doğrulayınız
        given()
                .when()

                .get("http://api.zippopotam.us/us/90210")
                .then()
                //.statusCode(200)
                .body("places[0].state", equalTo("California")) // places in 0. elemaninin
        ;
    }
    @Test
    public void checkHasItem(){
        // Soru : "http://api.zippopotam.us/tr/01000"  endpoint in dönen
        // place dizisinin herhangi bir elemanında  "Dörtağaç Köyü" değerinin
        // olduğunu doğrulayınız
        given()
                .when()

                .get("http://api.zippopotam.us/tr/01000")
                .then()
                .log().body()
                .body("places.'place name'",hasItem("Dörtağaç Köyü")) //places icineki bütün place name lerin icinde dörtagac köyü varmi

        ;
    }
    @Test
    public void bodyArrayHasSizeTest(){
        // Soru : "http://api.zippopotam.us/us/90210"  endpoint in dönen
        // place dizisinin dizi uzunluğunun 1 olduğunu doğrulayınız.

        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places",hasSize(1)) //places in eleman uzunlugu 1 mi

        ;

    }

    @Test
    public void combiningTest(){
        given()
                .when()
                .get("http://api.zippopotam.us/us/90210")
                .then()
                .log().body()
                .body("places",hasSize(1)) //places in eleman uzunlugu 1 mi
                .body("places[0].state",equalTo("California"))
                .body("places[0].'place name'",equalTo("Beverly Hills"))

        ;

    }

    @Test
    public void  pathParamTest(){
        given() // gönderilecek hazirliklar
                .pathParam("ulke","us")
                .pathParam("postaKodu",90210)
                .log().uri() // request linki ni göndermeden önce görebilirsin
                .when()
                .get("http://api.zippopotam.us/{ulke}/{postaKodu}")
                .then()
                .log().body()
        ;
    }
    @Test
    public void  queryParamTest(){
        given()
                .param("page",1) //?page=1 param:soru isareti ile parametre gönderecegim
                .log().uri()
                .when()
                .get("https://gorest.co.in/public/v1/users")
                //.get("https://gorest.co.in/public/v1/users?page=3")

                .then()
                .log().body()
                ;
    }
    @Test
    public void  queryParamTest2(){
        // https://gorest.co.in/public/v1/users?page=3
        // bu linkteki 1 den 10 kadar sayfaları çağırdığınızda response daki donen page degerlerinin
        // çağrılan page nosu ile aynı olup olmadığını kontrol ediniz.
        //given()
        for (int i = 1; i <= 10; i++) {
            given()
            .param("page",i)
                    .log().uri()
                    .when()
                    .get("https://gorest.co.in/public/v1/users")
                    .then()
                    .body("meta.pagination.page",equalTo(i))
                    ;
        }


    }
}
