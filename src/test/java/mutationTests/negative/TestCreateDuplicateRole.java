package mutationTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;

import java.util.UUID;

public class TestCreateDuplicateRole extends BaseClass {

    @Test
    public void testCreateDuplicateRole() {
        String roleName = "Test Role " + UUID.randomUUID();
        GraphQLResponse response1 = client.sendRequest(
                Mutation.ROLE_CREATE_ONE.getQuery(roleName)
        );
        Role createdRole = responseProcessor.assertAndReturn(response1, Role.class);

        Assert.assertNotNull(
                createdRole,
                "1st role could not be created"
        );

        // Create the same role again
        GraphQLResponse response2 = client.sendRequest(
                Mutation.ROLE_CREATE_ONE.getQuery(roleName)
        );
        Assert.assertNotNull(
                response2.getError(),
                "No error was return while trying to create a duplicate role"
        );
        Assert.assertFalse(
                response2.getError().isEmpty(),
                "No error was return while trying to create a duplicate role"
        );

        // The current error message: "an error occurred" is too generic.
        Assert.assertEquals(
                response2.getError().get(0).getMessage(),
                "Cannot create two roles with the same name."
        );
    }
}
