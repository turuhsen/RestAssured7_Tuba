import io.restassured.http.ContentType;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;
import mODEL.Location;
import mODEL.Place;
import mODEL.ToDo;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class _04_Tasks {
    /**
     * Task 1
     * create a request to https://jsonplaceholder.typicode.com/todos/2
     * expect status 200
     * expect content type JSON
     * expect title in response body to be "quis ut nam facilis et officia qui"
     */
    @Test
    public void Test1(){

        ResponseAwareMatcher<Response> equalTo;
        given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .contentType(ContentType.JSON)
                        .body("title",equalTo("quis ut nam facilis et officia qui"))
                ;

        }
    @Test
    public void Test2a(){
        /**
         Task 2
         create a request to https://jsonplaceholder.typicode.com/todos/2
         expect status 200
         expect content type JSON
         *a) expect response completed status to be false(hamcrest) *b) extract completed field and testNG assertion(testNG)*/

        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .log().body()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("completed",equalTo(false))
                ;

    }
    @Test
    public void Test2b(){
        /**
         Task 2
         create a request to https://jsonplaceholder.typicode.com/todos/2
         expect status 200
         expect content type JSON
         *a) expect response completed status to be false(hamcrest)*b) extract completed field and testNG assertion(testNG)*/
        boolean completedData=
        given()
                .when()
                .get("https://jsonplaceholder.typicode.com/todos/2")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .extract().path("completed")
        ;
        Assert.assertFalse(completedData);

    }
    @Test
    public void Test3(){
        /** Task 3
         * create a request to https://jsonplaceholder.typicode.com/todos/2
         * expect status 200
         * Converting Into POJO and write
         */
        ToDo todoNesnesi=
                given()
                        .when()
                        .get("https://jsonplaceholder.typicode.com/todos/2")
                        .then()
                        .extract().body().as(ToDo.class)
                ;
        System.out.println("todoNesnesi = " + todoNesnesi);
        System.out.println("todoNesnesi.getTitle() = " + todoNesnesi.getTitle());
        System.out.println("todoNesnesi.getId() = " + todoNesnesi.getId());
        System.out.println("todoNesnesi.isCompleted() = " + todoNesnesi.isCompleted());
        
        
    }
    }

