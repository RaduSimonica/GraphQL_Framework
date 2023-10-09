package mutationTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestCreateRoleEmptyName extends BaseClass {

    @Test
    public void testCreateRoleEmptyName() {
        String roleName = "";
        GraphQLResponse response = client.sendRequest(
                Mutation.ROLE_CREATE_ONE.getQuery(roleName)
        );
        Role createdRole = responseProcessor.assertAndReturn(response, Role.class);
        TestLogger.info(
                "Created role named: {} with id: {}",
                createdRole.getName(),
                createdRole.getId()
        );

        Assert.assertNotEquals(
                createdRole.getName(),
                roleName,
                "Role was created with empty name"
        );
        TestLogger.info("Test passed!");
    }
}
