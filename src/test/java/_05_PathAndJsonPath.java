import io.restassured.response.Response;
import mODEL.Location;
import mODEL.Place;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class _05_PathAndJsonPath {
    @Test
    public void extractingPath() {
        // gelen body bilgisi disari almanin 2 yöntemini gördük
        // .extract.path("")   , as(Todo.Class)
        String postcode =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .log().body()
                        .extract().path("'post code'");
        System.out.println("postcode = " + postcode);
        int postCodeInt = Integer.parseInt(postcode);
        System.out.println("postCodeInt = " + postCodeInt);
    }

    @Test
    public void extractingJosonPath() {
        // gelen body bilgisi disari almanin 2 yöntemini gördük
        // .extract.path("")   , as(Todo.Class)
        int postcode =
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .log().body()
                        .extract().jsonPath().getInt("'post code'")
                        //tip dönüsümü otomatik ,uygun tip verilmeli
                ;
        System.out.println("postcode = " + postcode);
    }

    @Test
    public void getzipCode(){
        Response response=
                given()
                        .when()
                        .get("http://api.zippopotam.us/us/90210")
                        .then()
                        .log().body()
                        .extract().response();

        Location locationAsPath=response.as(Location.class);//Bütün class yapisini yazmak zorundayiz
        System.out.println("locationAsPath.getPlaces() = " + locationAsPath.getPlaces());
        //bana sadece place dizisi lazim olsa bile, bütün diger class lari yazmak zorundayim

        List<Place>  places=response.jsonPath().getList("place",Place.class);
        // Daha önceki örneklerde (as) Clas dönüşümleri için tüm yapıya karşılık gelen
        // gereken tüm classları yazarak dönüştürüp istediğimiz elemanlara ulaşıyorduk.

        // Burada ise(JsonPath) aradaki bir veriyi clasa dönüştürerek bir list olarak almamıza
        // imkan veren JSONPATH i kullandık.Böylece tek class ile veri alınmış oldu
        // diğer class lara gerek kalmadan

        // path : class veya tip dönüşümüne imkan veremeyen direk veriyi verir. List<String> gibi
        // jsonPath : class dönüşümüne ve tip dönüşümüne izin vererek , veriyi istediğimiz formatta verir.
    }
    @Test
    public void Test(){
        // https://gorest.co.in/public/v1/users  endpointte dönen Sadece Data Kısmını POJO
        // dönüşümü ile alarak yazdırınız.
        Response response=
                given()
                        .when()
                        .get("https://gorest.co.in/public/v1/users")
                        .then()
                        .log().body()
                        .extract().response();
    }
}
                                                                                                                                                                                                                                                                                                                                