package ro.crownstudio.api.factory.operations;

public class SkillUpdateOne implements Operation {

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
}
