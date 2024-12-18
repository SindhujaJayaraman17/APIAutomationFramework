package RedCapCloud.base;

//Common code to be run before and after the test case

import RedCapCloud.asserts.AssertActions;
import RedCapCloud.endpoints.APIConstants;
import RedCapCloud.listeners.RetryListener;
import RedCapCloud.modules.PayloadManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;

public class BaseTests {

    public PayloadManager payloadmanager;
    public AssertActions assertactions;
    public RetryListener retrylistener;
    public APIConstants apiConstants;
    public Response response;
    public JsonPath jsonpath;
    public ValidatableResponse validateresponse;
    public RequestSpecification requestSpecification;

   @BeforeTest
    public void setup() {
       payloadmanager = new PayloadManager();
       assertactions = new AssertActions();

       requestSpecification = RestAssured
               .given().baseUri(APIConstants.BASE_URL)
               .contentType(ContentType.JSON).log().all();

   }
    public String getToken(){
        // Setting the payload
        String payload = payloadmanager.setAuthPayload();

        // Get the Token
        response = requestSpecification.contentType(ContentType.JSON).body(payload).when().post();

    //Token to string conversion

        String token = payloadmanager.getTokenFromJSON(response.asString());
        return token;

    }
}
