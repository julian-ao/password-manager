package restserver;

import core.UserSession;
import core.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/api/v1/entries")
public class PasswordManagerController {
  
      private UserSession userSession = UserSession.getInstance();
  
      /**
      * Gets the entries for a given profile.
      *
      * @param userEntry the name of the profile
      * @return the entries for the given profile
      */
      @GetMapping("/username")
      public @ResponseBody
      String getUserEntry() {
          return userSession.getUsername();
      }

      // Get test that logs the parameters
      @GetMapping("/test")
      public @ResponseBody
      String getTest(@RequestParam String param1, @RequestParam String param2) {
          return param1 + " " + param2;
      }

    }