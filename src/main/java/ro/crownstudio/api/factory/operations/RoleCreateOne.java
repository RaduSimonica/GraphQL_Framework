package ro.crownstudio.api.factory.operations;

public class RoleCreateOne implements Operation {

    @Override
    public String getType() {
        return "mutation";
    }

    @Override
    public String getName() {
        return "CreateRole";
    }

    @Override
    public String getString() {
        return "RoleCreateOne(name: \"%s\") { createdAt deletedAt id name skills { id weight } updatedAt }";
    }
}
