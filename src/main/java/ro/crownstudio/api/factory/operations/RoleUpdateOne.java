package ro.crownstudio.api.factory.operations;

public class RoleUpdateOne implements Operation {

    @Override
    public String getType() {
        return "mutation";
    }

    @Override
    public String getName() {
        return "UpdateRoleById";
    }

    @Override
    public String getString() {
        return "RoleUpdateOne(id: %s, name: \"%s\") { createdAt deletedAt id name skills { id weight } updatedAt }";
    }
}
