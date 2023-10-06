package ro.crownstudio.api.pojo;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.*;


import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Skill extends ResponseObject {

    @SerializedName("createdAt")
    private Date createdAt;

    @SerializedName("deletedAt")
    private Date deletedAt;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("updatedAt")
    private Date updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return id == skill.id &&
                Objects.equals(createdAt, skill.createdAt) &&
                Objects.equals(deletedAt, skill.deletedAt) &&
                Objects.equals(name, skill.name) &&
                Objects.equals(updatedAt, skill.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, deletedAt, id, name, updatedAt);
    }
}
