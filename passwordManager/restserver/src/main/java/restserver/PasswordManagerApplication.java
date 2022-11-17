package restserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PasswordManagerApplication {
  /**
   * Starts the spring server.
   *
   * @param strings launch args.
   */
  public static void main(final String... strings) {
    SpringApplication.run(PasswordManagerApplication.class, strings);
  }

}
