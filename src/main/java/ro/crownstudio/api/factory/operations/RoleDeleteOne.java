package ro.crownstudio.api.factory.operations;

import ro.crownstudio.api.pojo.DeleteResult;

import java.lang.reflect.Type;

public class RoleDeleteOne implements Operation {

    private static RoleDeleteOne INSTANCE;

    private RoleDeleteOne() {}

    public static RoleDeleteOne getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RoleDeleteOne();
        }
        return INSTANCE;
    }

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

    @Override
    public Type getReturnType() {
        return DeleteResult.class;
    }
}
