import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class TestClass {

    @DataProvider(name = "data")
    public Object[][] dataInformation() {
        return new Object[][]{
                {"automation", "Automation@!@123", "1204", "User exists!"},
                {"automation", "Auto@2", "1300", "Passwords must have at least one non alphanumeric character, one digit ('0'-'9'), one uppercase ('A'-'Z'), one lowercase ('a'-'z'), one special character and Password must be eight characters or longer."},
                {"automation", "", "1200", "UserName and Password required."},
        };
    }

    @Test(dataProvider = "data")
    public void testUserPost(String userName, String password, String expectedCode, String expectedMessage) throws JsonProcessingException {

        String requestBody = String.format("{\"userName\": \"%s\", \"password\": \"%s\"}", userName, password);

        // Send the POST request
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("https://bookstore.toolsqa.com/swagger/#/Account/AccountV1UserPost");


        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 200);

        String actualCode=response.getBody().path("code").toString();
        System.out.println(actualCode);



        String actualResponseBody = response.getBody().asString();
//        System.out.println(actualResponseBody);


//        JsonPath jsonPathEvaluator = response.jsonPath().getJsonObject("code");



//        String code = jsonPathEvaluator.get(expectedCode);
//        String message = jsonPathEvaluator.get(expectedMessage);
//        System.out.println(code);
//        System.out.println(message);

        System.out.println("code "+expectedCode);
        System.out.println("message "+expectedMessage);

    /*    Assert.assertTrue(actualResponseBody.contains(expectedCode));
        Assert.assertTrue(actualResponseBody.contains(expectedMessage));*/

    }
}

