package ro.crownstudio.api.factory.operations;

public class RoleSkillsOverwrite implements Operation {

    @Override
    public String getType() {
        return "mutation";
    }

    @Override
    public String getName() {
        return "OverwriteRoleSkills";
    }

    @Override
    public String getString() {
        return "RoleSkillsOverwrite(roleId: %s, skills: %s) { createdAt deletedAt id roleId skill " +
                "{ createdAt deletedAt id name updatedAt } skillId updatedAt weight }";
    }
}
