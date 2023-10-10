package ro.crownstudio.api.factory.operations;

public class SkillCreateOne implements Operation {

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
}
