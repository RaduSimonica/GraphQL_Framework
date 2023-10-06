package ro.crownstudio.api.pojo;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.util.Date;
import java.util.Objects;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleSkill extends ResponseObject{

    @SerializedName("createdAt")
    private Date createdAt;

    @SerializedName("deletedAt")
    private Date deletedAt;

    @SerializedName("id")
    private int id;

    @SerializedName("roleId")
    private int roleId;

    @SerializedName("skill")
    private Skill skill;

    @SerializedName("skillId")
    private int skillId;

    @SerializedName("weight")
    protected float weight;

    @SerializedName("updatedAt")
    private Date updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleSkill roleSkill = (RoleSkill) o;
        return id == roleSkill.id &&
                roleId == roleSkill.roleId &&
                skillId == roleSkill.skillId &&
                Float.compare(roleSkill.weight, weight) == 0 &&
                Objects.equals(createdAt, roleSkill.createdAt) &&
                Objects.equals(deletedAt, roleSkill.deletedAt) &&
                Objects.equals(skill, roleSkill.skill) &&
                Objects.equals(updatedAt, roleSkill.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(createdAt, deletedAt, id, roleId, skill, skillId, updatedAt, weight);
    }
}
