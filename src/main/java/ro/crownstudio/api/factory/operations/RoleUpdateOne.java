package ro.crownstudio.api.factory.operations;

import ro.crownstudio.api.pojo.Role;

import java.lang.reflect.Type;

public class RoleUpdateOne implements Operation {

    private static RoleUpdateOne INSTANCE;

    private RoleUpdateOne() {}

    public static RoleUpdateOne getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoleUpdateOne();
        }
        return INSTANCE;
    }

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

    @Override
    public Type getReturnType() {
        return Role.class;
    }
}
