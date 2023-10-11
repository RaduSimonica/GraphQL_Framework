package ro.crownstudio.api.factory.operations;

import ro.crownstudio.api.pojo.Skill;

import java.lang.reflect.Type;

public class SkillCreateOne implements Operation {

    private static SkillCreateOne INSTANCE;

    private SkillCreateOne() {}

    public static SkillCreateOne getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SkillCreateOne();
        }
        return INSTANCE;
    }

    @Override
    public String getType() {
        return "mutation";
    }

    @Override
    public String getName() {
        return "CreateSkill";
    }

    @Override
    public String getString() {
        return "SkillCreateOne(name: \"%s\") { createdAt deletedAt id name updatedAt }";
    }

    @Override
    public Type getReturnType() {
        return Skill.class;
    }
}
