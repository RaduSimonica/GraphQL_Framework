package ro.crownstudio.api.factory.operations;

import ro.crownstudio.api.pojo.Skill;

import java.lang.reflect.Type;

public class Skills implements Operation {

    private static Skills INSTANCE;

    private Skills() {}

    public static Skills getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Skills();
        }
        return INSTANCE;
    }

    @Override
    public String getType() {
        return "query";
    }

    @Override
    public String getName() {
        return "GetAllSkills";
    }

    @Override
    public String getString() {
        return "Skills { createdAt deletedAt id name updatedAt }";
    }

    @Override
    public Type getReturnType() {
        return Skill[].class;
    }
}
