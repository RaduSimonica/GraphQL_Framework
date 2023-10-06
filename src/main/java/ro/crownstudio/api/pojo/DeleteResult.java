package ro.crownstudio.api.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeleteResult extends ResponseObject {

    @SerializedName("affected")
    private int affected;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteResult that = (DeleteResult) o;
        return affected == that.affected;
    }

    @Override
    public int hashCode() {
        return Objects.hash(affected);
    }
}
