package ro.crownstudio.api.factory.operations;

import ro.crownstudio.api.pojo.Skill;

import java.lang.reflect.Type;

public class SkillFindOne implements Operation {

    private static SkillFindOne INSTANCE;

    private SkillFindOne() {}

    public static SkillFindOne getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SkillFindOne();
        }
        return INSTANCE;
    }

    @Override
    public String getType() {
        return "query";
    }

    @Override
    public String getName() {
        return "FindSkillById";
    }

    @Override
    public String getString() {
        return "SkillFindOne(id: %s) { createdAt deletedAt id name updatedAt }";
    }

    @Override
    public Type getReturnType() {
        return Skill.class;
    }
}
