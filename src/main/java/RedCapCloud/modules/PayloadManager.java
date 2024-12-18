package RedCapCloud.modules;

import RedCapCloud.pojos.*;
import com.google.gson.Gson;

public class PayloadManager {

    // ******************  PAYLOAD GENERATION ************************************
    //Step 1 : Passing the payload as GSON format

    //JSON Request format
    //     {
    //          "firstname" : "Sindhuja",
    //        "lastname" : "Jayaraman",
    //        "totalprice" : 111,
    //        "depositpaid" : true,
    //        "bookingdates" : {
    //        "checkin" : "2018-01-01",
    //        "checkout" : "2019-01-01"
    //        },
    //        "additionalneeds" : "{{additionalneeds}}"
    //       }

    Gson gson;

    public String createPayloadBookingAsString() {
        Booking booking = new Booking();
        booking.setFirstname("Sindhuja");
        booking.setLastname("Jayaraman");
        booking.setTotalprice(111);
        booking.setDepositpaid(true);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2018-01-01");
        bookingdates.setCheckout("2019-01-01");

        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);

        //Step 2 - Creating a GSON object
        gson = new Gson();

        //Step 3 -SERIALIZATION -  Converting GSON to JSON String to generate JSON Payload

        String jsonStringPayload = gson.toJson(booking);
        return jsonStringPayload;
    }

//***********DESERIALIZATION -  Converting Response from JSON to GSON Object readable format ************************

    public BookingResponse bookingResponseJava(String responseString) {
        gson = new Gson();
        BookingResponse bookingresponse = gson.fromJson(responseString, BookingResponse.class);
        return bookingresponse;
    }

    public Booking getResponseFromJSON(String getResponse){
        Booking booking = gson.fromJson(getResponse,Booking.class);
        return booking;
    }

    //get Token
    public String setAuthPayload() {
        // Auth Object -> json String
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");
        gson = new Gson();
        String jsonPayloadString = gson.toJson(auth);
        System.out.println("Payload set to the -> " + jsonPayloadString);
        return jsonPayloadString;

    }

    // Convert above Token to JSON string readable format - Deserialisation

    public String getTokenFromJSON(String tokenResponse) {
        gson = new Gson();
        TokenResponse tokenResponse1 = gson.fromJson(tokenResponse, TokenResponse.class);
        return tokenResponse1.getToken();
    }

    public String fullUpdatePayloadAsString() {

        gson = new Gson();
        Booking booking = new Booking();
        booking.setFirstname("Santhosh");
        booking.setLastname("RR");
        booking.setTotalprice(112);
        booking.setDepositpaid(true);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-05");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");
        return gson.toJson(booking);
    }
}





