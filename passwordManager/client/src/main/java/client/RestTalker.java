package client;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.json.JSONObject;

/**
 * RestTalker is a class to communicate with the server.
 */
public class RestTalker {
  /**
   * OK response value.
   */
  private static final int OK_CODE = 200;
  /**
   * URL to remote server.
   */
  private final String url;
  /**
   * Port to the remote server.
   */
  private final int port;

  private String loggedInUsername;
  private String loggedInPassword;


  /**
   * Default constructor.
   */
  public RestTalker() {
    this.url = "http://localhost";
    this.port = 8080;
  }

  /**
   * Constructs a RestTalker from a builder.
   *
   * @param serverUrl server base url.
   * @param serverPort server port.
   */
  public RestTalker(String serverUrl, int serverPort) {
    this.url = serverUrl;
    this.port = serverPort;
  }

  /**
   * Setter for client when login is successful.

   * @param username Sets the username after login.
   * @param password Sets the password after login.
   */
  public void setLoggedIn(String username, String password) {
    this.loggedInPassword = password;
    this.loggedInUsername = username;
  }

  public String getUsername() {
    return this.loggedInUsername;
  }

  public String getUrl() {
    return this.url;
  }

  /**
   * Elementary synchronous get request.
   *
   * @param endpoint Where to send the request to.
   * @return The Http response.
   * @throws URISyntaxException If the query entries ruin the query string syntax.
   * @throws InterruptedException If the request was interrupted before retrieving the http
   *         response.
   * @throws ExecutionException If the request completed exceptionally.
   * @throws ServerResponseException If there was an error with the server response.
   */
  private HttpResponse<String> get(final String endpoint)
      throws URISyntaxException, InterruptedException, ExecutionException, ServerResponseException {
    HttpResponse<String> response = this.getAsync(endpoint).get();

    if (response.statusCode() != OK_CODE) {
      throw new ServerResponseException(HttpResponses.getResponseText(response.statusCode()),
          response.statusCode());
    }

    return response;
  }

  /**
   * Elementary asynchronous get request.
   *
   * @param endpoint Where to send the request to.
   * @return The Http response promise.
   * @throws URISyntaxException If the URI syntax is incorrect.
   */
  private CompletableFuture<HttpResponse<String>> getAsync(final String endpoint)
      throws URISyntaxException {
    HttpClient client = HttpClient.newBuilder().build();

    HttpRequest request = HttpRequest.newBuilder().GET()
        .uri(new URI(this.getUrl() + ":" + this.port + endpoint)).build();

    return client.sendAsync(request, BodyHandlers.ofString());
  }

  /**
   * Elementary synchronous post request.

   * @param endpoint Where to send the request to.
   * @param payload What to send to the server.
   * @return the entry id.
   * @throws URISyntaxException If the URI syntax is incorrect.
   * @throws InterruptedException If the underlying asynchronous request was interrupted before
   *         retrieval.
   * @throws ExecutionException If the underlying asynchronous request completed exceptionally.
   * @throws ServerResponseException If there was an error with the server response.
   */
  private HttpResponse<String> post(final String endpoint, final String payload)
      throws URISyntaxException, InterruptedException, ExecutionException, ServerResponseException {
    HttpResponse<String> response = this.postAsync(endpoint, payload).get();

    if (response.statusCode() != OK_CODE) {
      throw new ServerResponseException(HttpResponses.getResponseText(response.statusCode()),
          response.statusCode());
    }
    return response;
  }

  /**
   * Elementary asynchronous post request.
   *
   * @param endpoint Where to send the requests to.
   * @param payload Where to send to the server.
   * @return The Http response promise.
   * @throws URISyntaxException If the URI syntax is incorrect.
   */
  private CompletableFuture<HttpResponse<String>> postAsync(final String endpoint,
      final String payload) throws URISyntaxException {
    HttpClient client = HttpClient.newBuilder().build();

    HttpRequest request =
        HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString(payload))
            .uri(new URI(this.getUrl() + ":" + this.port + endpoint)).build();

    return client.sendAsync(request, BodyHandlers.ofString());
  }

  /**
   * Request login to the server.
   */
  public boolean login(String username, String password)
      throws URISyntaxException, InterruptedException, ExecutionException, ServerResponseException {
    HttpResponse<String> response =
        this.get("/api/v1/entries/login?username=" + username + "&password=" + password);

    if (response.body().equals("Success")) {
      this.setLoggedIn(username, password);
      return true;
    }

    return false;
  }

  /**
   * Request logout to the server.
   */
  public String getProfiles()
      throws URISyntaxException, InterruptedException, ExecutionException, ServerResponseException {
    HttpResponse<String> response = this.get("/api/v1/entries/getProfiles?username="
        + this.loggedInUsername + "&password=" + this.loggedInPassword);
    return response.body();
  }


  /**
    * Register a new user. 

   * @param username The username of the new user.
   * @param password The password of the new user.
   * @return Returns true if the user was successfully registered.
   * @throws URISyntaxException If the URI syntax is incorrect.
   * @throws InterruptedException If the underlying asynchronous request was interrupted before
   * @throws ExecutionException If the underlying asynchronous request completed exceptionally.
   * @throws ServerResponseException If there was an error with the server response.
   */
  public boolean registerUser(String username, String password)
      throws URISyntaxException, InterruptedException, ExecutionException, ServerResponseException {
    JSONObject obj = new JSONObject();
    obj.put("username", username);
    obj.put("password", password);
    HttpResponse<String> response = this.post("/api/v1/entries/register", obj.toString());
    return response.body().equals("Success");
  }


  /**
   * Insert a new entry (profile) into the database.

   * @param username The username of the new user.
   * @param title The title of the new entry.
   * @param password The password of the new entry.
   * @return Returns true if the entry was successfully inserted.
   * @throws URISyntaxException If the URI syntax is incorrect.
   * @throws InterruptedException If the underlying asynchronous request was interrupted before
   * @throws ExecutionException If the underlying asynchronous request completed exceptionally.
   * @throws ServerResponseException If there was an error with the server response.
   */
  public boolean insertProfile(String username, String title, String password)
      throws URISyntaxException, InterruptedException, ExecutionException, ServerResponseException {
    JSONObject json = new JSONObject();
    json.put("username", username);
    json.put("title", title);
    json.put("password", password);
    json.put("parentUsername", loggedInUsername);
    json.put("parentPassword", loggedInPassword);
    HttpResponse<String> response = this.post("/api/v1/entries/insertProfile", json.toString());
    return response.body().equals("Success");
  }


  /**
   * UserValidatior validates the user input.

   * @param username The username of the new user.
   * @param password The password of the new user.
   * @param passwordRepeat The password of the new user.
   * @return Returns "OK" if the user input is valid, else the reason why it is invalid.
   * @throws URISyntaxException If the URI syntax is incorrect.
   * @throws InterruptedException If the underlying asynchronous request was interrupted.
   * @throws ExecutionException If the underlying asynchronous request completed exceptionally.
   * @throws ServerResponseException If there was an error with the server response.
   */
  public String userValidator(String username, String password, String passwordRepeat)
      throws URISyntaxException, InterruptedException, ExecutionException, ServerResponseException {
    HttpResponse<String> response = this.get("/api/v1/entries/userValidator?username=" + username
        + "&password=" + password + "&passwordRepeat=" + passwordRepeat);
    return response.body();
  }

  /**
   * Delete a profile from the database.

   * @param user The user who wants to delete the profile.
   * @param title The title of the profile to be deleted.
   * @param username The username of the profile to be deleted.
   * @param password The password of the profile to be deleted.
   * @return Returns true if the profile was successfully deleted.
   * @throws URISyntaxException If the URI syntax is incorrect.
   * @throws InterruptedException If the underlying asynchronous request was interrupted.
   * @throws ExecutionException If the underlying asynchronous request completed exceptionally.
   * @throws ServerResponseException If there was an error with the server response.
   */
  public boolean deleteProfile(String user, String title, String username, String password, int id)
      throws URISyntaxException, InterruptedException, ExecutionException, ServerResponseException {
    JSONObject json = new JSONObject();
    json.put("user", user);
    json.put("userPassword", loggedInPassword);
    json.put("title", title);
    json.put("username", username);
    json.put("password", password);
    json.put("id", id);
    HttpResponse<String> response = this.post("/api/v1/entries/deleteProfile", json.toString());
    return response.body().equals("Success");
  }

  /**
   * Change the path of the database to test path.
   */
  public void doDatabaseTest() {
    try {
      this.post("/api/v1/entries/doDatabaseTest", "[]");
    } catch (URISyntaxException | InterruptedException | ExecutionException
        | ServerResponseException e) {
      e.printStackTrace();
    }
  }
}
