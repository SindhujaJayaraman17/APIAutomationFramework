package tests.crud;

import RedCapCloud.asserts.AssertActions;
import RedCapCloud.base.BaseTests;
import RedCapCloud.endpoints.APIConstants;
import RedCapCloud.modules.PayloadManager;
import RedCapCloud.pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.TmsLink;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.*;

import static io.restassured.RestAssured.*;

public class TestVerifyCreateBooking extends BaseTests {


    @Owner("Sindhuja")
    @Description("To verify that POST request is working fine")
    @Link("JIRA Link example1")
    @TmsLink("TestRailLink Example1")
    @Test
    public void testVerifyCreateBookingPOST01() {

        //Step1. Setting up the URL
        requestSpecification.given().baseUri(APIConstants.BASE_URL);
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);



        //Step2. Setting up the response in body and passing the payload

        response = RestAssured
                .given(requestSpecification)
                .when().body(payloadmanager.createPayloadBookingAsString())
                .post();
        validateresponse = response.then().statusCode(200).log().all();

        //Step3. Verifying the response

        //************ 3 ways to validate the response *************

        //1. Default Rest Assured - Validation
        validateresponse.body("booking.firstname", Matchers.equalTo("Sindhuja"));
        validateresponse.body("booking.lastname", Matchers.equalTo("Jayaraman"));

        //De-Serialization
        //Booking response is converted to String to do Assert Action verification
        BookingResponse bookingResponse = payloadmanager.bookingResponseJava(response.asString());

        //2. AssertJ validation
        assertThat(bookingResponse.getBooking().getFirstname()).isEqualTo("Sindhuja");
        assertThat(bookingResponse.getBooking().getLastname()).isEqualTo("Jayaraman");
        assertThat(bookingResponse.getBookingid()).isNotNull();

        //3.TestNG Validation

        //verifyStatusCode function is from AssertActions class
        assertactions.verifyStatusCode(response, 200);
        //verifyResponsebody function is from AssertActions class
        assertactions.verifyResponsebody(bookingResponse.getBooking().getFirstname(), "Sindhuja", "Verify the first name");
        assertactions.verifyResponsebody(bookingResponse.getBooking().getLastname(), "Jayaraman", "Verify Last name");

        //GetStringKey function is from AssertActions class
        assertactions.getStringKey(bookingResponse.getBooking().getFirstname(), "Sindhuja");
    }
}