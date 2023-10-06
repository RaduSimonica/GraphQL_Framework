package ro.crownstudio.api.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends ResponseObject {

    @SerializedName("createdAt")
    private Date createdAt;

    @SerializedName("deletedAt")
    private Date deletedAt;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("skills")
    private List<RoleSkill> skills;

    @SerializedName("updatedAt")
    private Date updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id &&
                Objects.equals(createdAt, role.createdAt) &&
                Objects.equals(deletedAt, role.deletedAt) &&
                Objects.equals(name, role.name) &&
                Objects.equals(skills, role.skills) &&
                Objects.equals(updatedAt, role.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, deletedAt, id, name, skills, updatedAt);
    }
}
