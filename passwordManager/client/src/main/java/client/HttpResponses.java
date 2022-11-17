package client;

import java.util.Map;

/**
 * HttpResponses is a class to get responses as string from the server.
 */
public final class HttpResponses {
  /**
   * All possible responses.
   */
  static final Map<Integer, String> RESPONSES = Map.of(200, "OK", 400, "Bad Request", 404, "Not Found", 500,
      "Internal Server Error");

  private HttpResponses() {
  }

  /**
   * Converts code to response.
   *
   * @param code code.
   * @return response.
   */
  public static String getResponseText(final int code) {
    return RESPONSES.get(code);
  }
}
