package ro.crownstudio.api.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Location extends ResponseObject {

    @SerializedName("line")
    private int line;

    @SerializedName("column")
    private int column;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return line == location.line && column == location.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(line, column);
    }
}
