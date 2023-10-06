package ro.crownstudio.api.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

@Getter
public class Error extends ResponseObject {

    @SerializedName("message")
    private String message;

    @SerializedName("locations")
    private List<Location> locations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return Objects.equals(message, error.message) && Objects.equals(locations, error.locations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, locations);
    }
}
