package ro.crownstudio.api.factory.operations;

import ro.crownstudio.api.pojo.Role;

import java.lang.reflect.Type;

public class RoleFindOne implements Operation {

    private static RoleFindOne INSTANCE;

    private RoleFindOne() {}

    public static RoleFindOne getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoleFindOne();
        }
        return INSTANCE;
    }

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

    @Override
    public Type getReturnType() {
        return Role.class;
    }
}
