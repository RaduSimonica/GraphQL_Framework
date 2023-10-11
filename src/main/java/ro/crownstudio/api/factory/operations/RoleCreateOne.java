package ro.crownstudio.api.factory.operations;

import ro.crownstudio.api.pojo.Role;

import java.lang.reflect.Type;

public class RoleCreateOne implements Operation {

    private static RoleCreateOne INSTANCE;

    private RoleCreateOne() {}

    public static RoleCreateOne getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoleCreateOne();
        }
        return INSTANCE;
    }

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

    @Override
    public Type getReturnType() {
        return Role.class;
    }
}
