import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;

class Main {
  /*
    A class that tests the Jackson JSON Parsing
  */

  public static String formatJson(String input) {
    return input.replaceAll("'", "\"");
  }

  // Test JSON
  static String test_running = "{'status': 'running', 'result': 'working...'}";
  static String test_errors = "{'error': 'error code 1'}";

  public static void main(String[] args) {
    // Test Cases
    String testJson = formatJson(test_running);
    // String testJson = formatJson(test_errors);

    // Print the JSON
    System.out.println(testJson);

    // Try mapping
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      // Object Map to the ApiResponse Jackson interface
      // Jackson will deduct it to types (subtypes)
      ApiResponse response = objectMapper.readValue(testJson, ApiResponse.class);
      // Check instace of
      if (response instanceof StatusResult) {
        // Cast the object into a new class object for use
        StatusResult pojo = (StatusResult) response;
        // Getter and Setter work now :D
        System.out.println("Result: " + pojo.getResult());
      } else if (response instanceof StatusError) {
        StatusError pojo = (StatusError) response;
        System.out.println("Error: " + pojo.getError());
      }
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
      return;
    }
  }
}