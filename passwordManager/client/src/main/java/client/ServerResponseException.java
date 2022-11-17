package client;

/**
 * Class for dealing with server responses exceptions.
 */
public class ServerResponseException extends Exception {
  private final int code;

  /**
   * A throwable exception pertaining to server issues.
   *
   * @param message the exception message.
   * @param responseCode the response code.
   */
  public ServerResponseException(final String message, final int responseCode) {
    super(message);
    this.code = responseCode;
  }

  /**
   * Gets the response code.
   *
   * @return the response code.
   */
  public int getCode() {
    return this.code;
  }
}
