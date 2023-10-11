package ro.crownstudio.api.factory.operations;

import ro.crownstudio.api.pojo.DeleteResult;

import java.lang.reflect.Type;

public class SkillDeleteOne implements Operation {

    private static SkillDeleteOne INSTANCE;

    private SkillDeleteOne() {}

    public static SkillDeleteOne getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SkillDeleteOne();
        }
        return INSTANCE;
    }

    @Override
    public String getType() {
        return "mutation";
    }

    @Override
    public String getName() {
        return "SkillDeleteOne";
    }

    @Override
    public String getString() {
        return "SkillDeleteOne(id: %s) { affected }";
    }


    @Override
    public Type getReturnType() {
        return DeleteResult.class;
    }
}
