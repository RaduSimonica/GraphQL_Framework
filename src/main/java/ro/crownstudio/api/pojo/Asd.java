package ro.crownstudio.api.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.Objects;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Asd extends ResponseObject {

    @SerializedName("weight")
    private float weight;

    @SerializedName("skillId")
    private int skillId;

    @Override
    public String toString() {
        return "{weight: %s, skillId: %s}".formatted(weight, skillId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asd asd = (Asd) o;
        return Float.compare(asd.weight, weight) == 0 && skillId == asd.skillId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, skillId);
    }
}
