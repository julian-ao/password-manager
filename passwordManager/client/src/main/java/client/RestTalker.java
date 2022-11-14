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

  /**
   * Constructs a LogClient from a builder.
   *
   * @param serverUrl server base url.
   * @param serverPort server port.
   */
  public RestTalker() {
    this.url = "http://localhost";
    this.port = 8080;
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

    HttpRequest request =
        HttpRequest.newBuilder().GET().uri(new URI(this.url + ":" + this.port + endpoint)).build();

    return client.sendAsync(request, BodyHandlers.ofString());
  }

  /***
   * Elementary synchronous post request.
   * 
   * @param endpoint Where to send the request to.
   * @param payload What to send to the server.
   * @throws URISyntaxException If the URI syntax is incorrect.
   * @throws InterruptedException If the underlying asynchronous request was interrupted before
   *         retrieval.
   * @throws ExecutionException If the underlying asynchronous request completed exceptionally.
   * @throws ServerResponseException If there was an error with the server response.
   * @return the entry id.
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
            .uri(new URI(this.url + ":" + this.port + endpoint)).build();

    return client.sendAsync(request, BodyHandlers.ofString());
  }

/*
 * ------------------------------------------NEXT IS REQ CODE-----------------------------------------------------
 */

  public String login(String username, String password) {
    try {
      HttpResponse<String> response =
          this.get("/api/v1/entries/login?username=" + username + "&password=" + password);
      return response.body();
    } catch (URISyntaxException | InterruptedException | ExecutionException
        | ServerResponseException e) {
      e.printStackTrace();
      return "error";
    }
  }

  public String getProfiles() {
    try {
      HttpResponse<String> response =
          this.get("/api/v1/entries/getProfiles");
      return response.body();
    } catch (URISyntaxException | InterruptedException | ExecutionException
        | ServerResponseException e) {
      e.printStackTrace();
      return "error";
    }
  }

  // get username
  public String getUsername() {
    try {
      HttpResponse<String> response = this.get("/api/v1/entries/username");
      return response.body();
    } catch (URISyntaxException | InterruptedException | ExecutionException
        | ServerResponseException e) {
      e.printStackTrace();
      return "error";
    }
  }

  //post request to create a new user, sends JSON object {"username": username, "password": password}
  public boolean registerUser(String username, String password) {
    try {
      JSONObject obj = new JSONObject();
      obj.put("username", username);
      obj.put("password", password);
      HttpResponse<String> response = this.post("/api/v1/entries/register", obj.toString());
      return response.body().equals("Success");
    } catch (URISyntaxException | InterruptedException | ExecutionException
        | ServerResponseException e) {
      e.printStackTrace();
      return false;
    }
  }

  //post request to create a new user, sends JSON object {"username": username, "password": password, "title": title}
  public boolean insertProfile(String username, String title, String password) {
    try {
      JSONObject json = new JSONObject();
      json.put("username", username);
      json.put("title", title);
      json.put("password", password);
      HttpResponse<String> response = this.post("/api/v1/entries/insertProfile", json.toString());
      return response.body().equals("Success");
    } catch (URISyntaxException | InterruptedException | ExecutionException
        | ServerResponseException e) {
      e.printStackTrace();
      return false;
    }
  }

  public boolean logout() {
    try {
      HttpResponse<String> response = this.get("/api/v1/entries/logout");
      return response.body().equals("Success");
    } catch (URISyntaxException | InterruptedException | ExecutionException
        | ServerResponseException e) {
      e.printStackTrace();
      return false;
    }
  }

  public String userValidator(String username, String password, String passwordRepeat) {
    try {
      HttpResponse<String> response = this.get("/api/v1/entries/userValidator?username=" + username + "&password=" + password + "&passwordRepeat=" + passwordRepeat);
      return response.body();
    } catch (URISyntaxException | InterruptedException | ExecutionException
        | ServerResponseException e) {
      e.printStackTrace();
      return "error";
    }
  }

  //delete profile
  public boolean deleteProfile(String user, String title, String username, String password) {
    try {
      JSONObject json = new JSONObject();
      json.put("user", user);
      json.put("title", title);
      json.put("username", username);
      json.put("password", password);
      HttpResponse<String> response = this.post("/api/v1/entries/deleteProfile", json.toString());
      return response.body().equals("Success");
    } catch (URISyntaxException | InterruptedException | ExecutionException
        | ServerResponseException e) {
      e.printStackTrace();
      return false;
    }
  }
}
