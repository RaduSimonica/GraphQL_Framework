package ro.crownstudio.api.factory.operations;

import ro.crownstudio.api.pojo.RoleSkill;

import java.lang.reflect.Type;

public class RoleSkillsOverwrite implements Operation {

    private static RoleSkillsOverwrite INSTANCE;

    private RoleSkillsOverwrite() {}

    public static RoleSkillsOverwrite getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoleSkillsOverwrite();
        }
        return INSTANCE;
    }

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

    @Override
    public Type getReturnType() {
        return RoleSkill[].class;
    }
}
