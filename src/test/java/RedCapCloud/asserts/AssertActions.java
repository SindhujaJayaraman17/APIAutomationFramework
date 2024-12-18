package RedCapCloud.asserts;

import io.restassured.response.Response;
import org.testng.Assert;
import static org.assertj.core.api.Assertions.*;

//Common Assertions which can be reused
public class AssertActions {

    public void verifyResponsebody(String actual, String expected, String description)
    {
        Assert.assertEquals(actual,expected,description);
    }

    public void verifyResponsebody(Integer actual, Integer expected, Integer description)
    {
        Assert.assertEquals(actual,expected,description);
    }

    //To Verify status code for every test case
   public void verifyStatusCode(Response response,Integer expected)
   {
       Assert.assertEquals(response.getStatusCode(),expected);
   }

   public void getStringKey(String expected, String Actual){
        assertThat(expected).isNotBlank();
        assertThat(Actual).isNotBlank();
   }
}
