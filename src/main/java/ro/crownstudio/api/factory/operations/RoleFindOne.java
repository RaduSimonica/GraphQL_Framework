package ro.crownstudio.api.factory.operations;

public class RoleFindOne implements Operation {

    @Override
    public String getType() {
        return "query";
    }

    @Override
    public String getName() {
        return "FindRoleById";
    }

    @Override
    public String getString() {
        return "RoleFindOne(id: %s) { createdAt deletedAt id name skills { id weight } updatedAt }";
    }
}
