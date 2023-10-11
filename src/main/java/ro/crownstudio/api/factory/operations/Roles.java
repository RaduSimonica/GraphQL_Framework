package ro.crownstudio.api.factory.operations;

import ro.crownstudio.api.pojo.Role;

import java.lang.reflect.Type;

public class Roles implements Operation {

    private static Roles INSTANCE;

    private Roles() {}

    public static Roles getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Roles();
        }
        return INSTANCE;
    }

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

    @Override
    public Type getReturnType() {
        return Role[].class;
    }
}
