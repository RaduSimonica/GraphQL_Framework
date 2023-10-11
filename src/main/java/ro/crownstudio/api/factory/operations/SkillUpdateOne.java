package ro.crownstudio.api.factory.operations;

import ro.crownstudio.api.pojo.Skill;

import java.lang.reflect.Type;

public class SkillUpdateOne implements Operation {

    private static SkillUpdateOne INSTANCE;

    private SkillUpdateOne() {}

    public static SkillUpdateOne getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SkillUpdateOne();
        }
        return INSTANCE;
    }

    @Override
    public String getType() {
        return "mutation";
    }

    @Override
    public String getName() {
        return "UpdateSkillById";
    }

    @Override
    public String getString() {
        return "SkillUpdateOne(id: %s, name: \"%s\") { createdAt deletedAt id name updatedAt }";
    }

    @Override
    public Type getReturnType() {
        return Skill.class;
    }
}
