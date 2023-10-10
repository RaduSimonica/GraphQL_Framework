package ro.crownstudio.api.factory.operations;

public class SkillFindOne implements Operation {

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
}
