package unitTests.ro.crownstudio.api.factory.operations;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.operations.*;
import ro.crownstudio.api.pojo.DeleteResult;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.api.pojo.RoleSkill;
import ro.crownstudio.api.pojo.Skill;

public class OperationTest {

    @Test
    public void testRoleCreateOne() {
        Operation operation = RoleCreateOne.getInstance();
        Assert.assertEquals(
                operation.getType(),
                "mutation"
        );
        Assert.assertEquals(
                operation.getName(),
                "CreateRole"
        );
        Assert.assertEquals(
                operation.getString(),
                "RoleCreateOne(name: \"%s\") { createdAt deletedAt id name skills { id weight } updatedAt }"
        );
        Assert.assertEquals(
                operation.getReturnType(),
                Role.class
        );
    }

    @Test
    public void testRoleDeleteOne() {
        Operation operation = RoleDeleteOne.getInstance();
        Assert.assertEquals(
                operation.getType(),
                "mutation"
        );
        Assert.assertEquals(
                operation.getName(),
                "DeleteRoleById"
        );
        Assert.assertEquals(
                operation.getString(),
                "RoleDeleteOne(id: %s) { affected }"
        );
        Assert.assertEquals(
                operation.getReturnType(),
                DeleteResult.class
        );
    }

    @Test
    public void testRoleFindOne() {
        Operation operation = RoleFindOne.getInstance();
        Assert.assertEquals(
                operation.getType(),
                "query"
        );
        Assert.assertEquals(
                operation.getName(),
                "FindRoleById"
        );
        Assert.assertEquals(
                operation.getString(),
                "RoleFindOne(id: %s) { createdAt deletedAt id name skills { id weight } updatedAt }"
        );
        Assert.assertEquals(
                operation.getReturnType(),
                Role.class
        );
    }

    @Test
    public void testRoles() {
        Operation operation = Roles.getInstance();
        Assert.assertEquals(
                operation.getType(),
                "query"
        );
        Assert.assertEquals(
                operation.getName(),
                "FindAllRoles"
        );
        Assert.assertEquals(
                operation.getString(),
                "Roles { createdAt deletedAt id name skills { id weight } updatedAt }"
        );
        Assert.assertEquals(
                operation.getReturnType(),
                Role[].class
        );
    }

    @Test
    public void testRoleSkillsOverwrite() {
        Operation operation = RoleSkillsOverwrite.getInstance();
        Assert.assertEquals(
                operation.getType(),
                "mutation"
        );
        Assert.assertEquals(
                operation.getName(),
                "OverwriteRoleSkills"
        );
        Assert.assertEquals(
                operation.getString(),
                "RoleSkillsOverwrite(roleId: %s, skills: %s) { createdAt deletedAt id roleId skill " +
                        "{ createdAt deletedAt id name updatedAt } skillId updatedAt weight }"
        );
        Assert.assertEquals(
                operation.getReturnType(),
                RoleSkill[].class
        );
    }

    @Test
    public void testRoleUpdateOne() {
        Operation operation = RoleUpdateOne.getInstance();
        Assert.assertEquals(
                operation.getType(),
                "mutation"
        );
        Assert.assertEquals(
                operation.getName(),
                "UpdateRoleById"
        );
        Assert.assertEquals(
                operation.getString(),
                "RoleUpdateOne(id: %s, name: \"%s\") { createdAt deletedAt id name skills { id weight } updatedAt }"
        );
        Assert.assertEquals(
                operation.getReturnType(),
                Role.class
        );
    }

    @Test
    public void testSkillCreateOne() {
        Operation operation = SkillCreateOne.getInstance();
        Assert.assertEquals(
                operation.getType(),
                "mutation"
        );
        Assert.assertEquals(
                operation.getName(),
                "CreateSkill"
        );
        Assert.assertEquals(
                operation.getString(),
                "SkillCreateOne(name: \"%s\") { createdAt deletedAt id name updatedAt }"
        );
        Assert.assertEquals(
                operation.getReturnType(),
                Skill.class
        );
    }

    @Test
    public void testSkillDeleteOne() {
        Operation operation = SkillDeleteOne.getInstance();
        Assert.assertEquals(
                operation.getType(),
                "mutation"
        );
        Assert.assertEquals(
                operation.getName(),
                "SkillDeleteOne"
        );
        Assert.assertEquals(
                operation.getString(),
                "SkillDeleteOne(id: %s) { affected }"
        );
        Assert.assertEquals(
                operation.getReturnType(),
                DeleteResult.class
        );
    }

    @Test
    public void testSkillFindOne() {
        Operation operation = SkillFindOne.getInstance();
        Assert.assertEquals(
                operation.getType(),
                "query"
        );
        Assert.assertEquals(
                operation.getName(),
                "FindSkillById"
        );
        Assert.assertEquals(
                operation.getString(),
                "SkillFindOne(id: %s) { createdAt deletedAt id name updatedAt }"
        );
        Assert.assertEquals(
                operation.getReturnType(),
                Skill.class
        );
    }

    @Test
    public void testSkills() {
        Operation operation = Skills.getInstance();
        Assert.assertEquals(
                operation.getType(),
                "query"
        );
        Assert.assertEquals(
                operation.getName(),
                "GetAllSkills"
        );
        Assert.assertEquals(
                operation.getString(),
                "Skills { createdAt deletedAt id name updatedAt }"
        );
        Assert.assertEquals(
                operation.getReturnType(),
                Skill[].class
        );
    }

    @Test
    public void testSkillUpdateOne() {
        Operation operation = SkillUpdateOne.getInstance();
        Assert.assertEquals(
                operation.getType(),
                "mutation"
        );
        Assert.assertEquals(
                operation.getName(),
                "UpdateSkillById"
        );
        Assert.assertEquals(
                operation.getString(),
                "SkillUpdateOne(id: %s, name: \"%s\") { createdAt deletedAt id name updatedAt }"
        );
        Assert.assertEquals(
                operation.getReturnType(),
                Skill.class
        );
    }
}