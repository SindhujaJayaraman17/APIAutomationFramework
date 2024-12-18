package tests.crud;

import RedCapCloud.base.BaseTests;
import RedCapCloud.endpoints.APIConstants;
import RedCapCloud.modules.PayloadManager;
import RedCapCloud.pojos.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.ITest;
import org.testng.ITestContext;
import org.testng.annotations.Test;
import RedCapCloud.pojos.Booking;

public class TestIntegrationFlow1 extends BaseTests {
    // Create A Booking, Create a Token
    // Get booking
    // Update the Booking
    // Delete the Booking


    @Test(groups = "qa", priority = 1)
    @Owner("Promode")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be Created")

    public void testCreateBooking(ITestContext iTestContext){
        //POST
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured
                .given(requestSpecification)
                .when().body(payloadmanager.createPayloadBookingAsString()).post();
        validateresponse = response.then().log().all();
        validateresponse.statusCode(200);

        //Converting the booking id response to String readable format
        BookingResponse bookingResponse = payloadmanager.bookingResponseJava(response.asString());
        iTestContext.setAttribute("bookingid", bookingResponse.getBookingid());

    }
//******************************************************************************//

    @Test(groups = "qa", priority = 2)
    @Owner("Promode")
    @Description("TC#INT1 - Step 2. Verify that the Booking By ID")

    public void testVerifyBookingId(ITestContext ITestContext){
        Integer bookingid = (Integer) ITestContext.getAttribute("bookingid");
        //GET
        String basepathGET = APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid;
        System.out.println(basepathGET);

        requestSpecification.basePath(basepathGET);
        response = RestAssured
                .given(requestSpecification)
                .when().get();
        validateresponse = response.then().log().all();
        validateresponse.statusCode(200);

        Booking booking = payloadmanager.getResponseFromJSON(response.asString());
        assertactions.verifyResponsebody(booking.getFirstname(),"Sindhuja","Verify first name");
        assertactions.verifyResponsebody(booking.getLastname(),"Jayaraman","Verify last name");
        assertactions.verifyStatusCode(response,200);

    }

    //******************************************************************************//

    @Test(groups = "qa", priority = 3)
    @Owner("Promode")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")

    public void testUpdateBookingByID(ITestContext iTestContext) {

        String token = getToken();
        iTestContext.setAttribute("token",token);
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathPUTPATCH);

        requestSpecification.basePath(basePathPUTPATCH);
        response = RestAssured
                .given(requestSpecification).cookie("token", token)
                .when().body(payloadmanager.fullUpdatePayloadAsString()).put();
        validateresponse = response.then().log().all();
        // Validatable Assertion
        validateresponse.statusCode(200);

        Booking booking = payloadmanager.getResponseFromJSON(response.asString());

    }


    //******************************************************************************//
//    @Test(groups = "qa", priority = 4)
//    @Owner("Promode")
//    @Description("TC#INT1 - Step 4. Delete the Booking by ID")
//
//    public void testDeleteBookingById(ITestContext ITestContext){
//        String token = (String) ITestContext.getAttribute("Token");
//       Integer bookingid = (Integer) ITestContext.getAttribute("bookingid");
//
//       String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL+"/"+bookingid;
//        System.out.println(basePathDELETE);
//
//        //when deleting a booking request, use cookie to pass the token for delete
//
//        requestSpecification.basePath(basePathDELETE).cookie("token",token);
//        response = RestAssured.given().spec(requestSpecification).when().delete();
//        validateresponse = response.then().log().all();
//        validateresponse.statusCode(403);
//        assertactions.verifyStatusCode(response,403);
    }

