package ro.crownstudio.api.factory.operations;

public class RoleDeleteOne implements Operation {

    @Override
    public String getType() {
        return "mutation";
    }

    @Override
    public String getName() {
        return "DeleteRoleById";
    }

    @Override
    public String getString() {
        return "RoleDeleteOne(id: %s) { affected }";
    }
}
