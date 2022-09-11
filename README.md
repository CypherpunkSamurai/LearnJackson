# Jackson Test 

A Project to test Jackson's Deduction feature.

## Case Scenario

We have a case scenario where we need to pase two JSON.

Case 1:
```json
{
  "status": "running",
  "running": "working..."
}
```

Case 2:
```json
{
  "error": "Error Exception Case..."
}
```

## How to parse
First we need to declare an interface that will serve as a template for JSON.

Contents of `ApiResponse.java
```java
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(
  {
    @JsonSubTypes.Type(StatusResult.class),
    @JsonSubTypes.Type(StatusError.class)
  }
)
public interface ApiResponse {
  // Interface for JSON
}
```

**Notice we use `StatusResult.class` and `StatusError.class` class as subtypes?**
This is for jackson to identify the subclasses.

Thus we need to declare the classes.

Contents of `StatusResult.java`
```java
public class StatusResult implements ApiResponse {
  /*
    Java POJO Class for json:
      { "status": "running", "running": "working..." }
  */
  String status;
  String result;

  public String getStatus() {
    return status;
  }

  public String getResult() {
    return result;
  }
}
```

Contents of `StatusError.java`:
```java
public class StatusError implements ApiResponse {
  /*
    Java POJO Class for json:
      { "error": "Error Exception Case..." }
  */
  
  String error;

  public String getError() {
    return this.error;
  }
}
```

## Parsing the JSON
To parse the json we need to first read the json into a string, then use `com.fasterxml.jackson.databind.ObjectMapper` to read the json.

```java
// Imports
import com.fasterxml.jackson.databind.ObjectMapper;

// Test JSON
  static String test_running = "{'status': 'running', 'result': 'working...'}";
  static String test_errors = "{'error': 'error code 1'}";

  // replae single ' quotes with double quotes "
  public static String formatJson(String input) {
    return input.replaceAll("'", "\"");
  }

  // Main
  public static void main() {
    // Test Cases
    String testJson = formatJson(test_running);

    // Init a instace of ObjectMapper
    ObjectMapper objectMapper = new ObjectMapper();

    // Use ObjectMapper instance to read
    ApiResponse response = objectMapper.readValue(testJson, ApiResponse.class);

    // Check the response type
    if (response instanceof StatusResult) {
        System.out.println("Result...");
    } else if (response instanceof StatusError) {
        System.out.println("Error...");
    }
  }
```

## Reading from Getters
Now we can parse to check the JSON, but what about reading the JSON values. For that we need to cast the Interface instance object to a inherited class object.

```java
// if ...

  // Cast the object into a new class object for use
  StatusResult pojo = (StatusResult) response;
  // Getter and Setter work now :D
  System.out.println("Result: " + pojo.getResult());
  
// ...
```

## Conclusion

I'm really helpfull to
* [pjfanning](https://github.com/pjfanning) and [cowtowncoder](https://github.com/cowtowncoder) For helping me out
* [This Article on Jackson and Deduction](https://www.baeldung.com/jackson-deduction-based-polymorphism)