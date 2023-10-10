package ro.crownstudio.api.factory.operations;

public class Skills implements Operation {

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
}
