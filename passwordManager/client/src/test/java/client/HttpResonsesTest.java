package client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class HttpResonsesTest {
  @Test
  public void HttpResponsesTest() {
    assertEquals("OK", HttpResponses.getResponseText(200));
    assertEquals("Bad Request", HttpResponses.getResponseText(400));
    assertEquals("Not Found", HttpResponses.getResponseText(404));
    assertEquals("Internal Server Error", HttpResponses.getResponseText(500));
  }
}
