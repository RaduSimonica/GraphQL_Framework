package ro.crownstudio.api.factory.operations;

public class Roles implements Operation {

    @Override
    public String getType() {
        return "query";
    }

    @Override
    public String getName() {
        return "FindAllRoles";
    }

    @Override
    public String getString() {
        return "Roles { createdAt deletedAt id name skills { id weight } updatedAt }";
    }
}
