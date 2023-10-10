package ro.crownstudio.api.factory.operations;

public class SkillDeleteOne implements Operation {

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
}
