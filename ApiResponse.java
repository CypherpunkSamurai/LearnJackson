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

}