package mutationTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleCreateOne;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.util.UUID;

public class TestCreateDuplicateRole extends BaseClass {

    @Test
    public void testCreateDuplicateRole() {
        String roleName = "Test Role " + UUID.randomUUID();

        Role createdRole = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(RoleCreateOne.getInstance())
                .withArgs(roleName)
                .assertError()
                .getResponseObject();

        TestLogger.info(
                "Created role named {} with id: {}",
                createdRole.getName(),
                createdRole.getId()
        );

        Assert.assertNotNull(
                createdRole,
                "1st role could not be created"
        );
        // Create the same role again
        GraphQLResponse response2 = RequestFactory.builder()
                .apiClient(client)
                .operation(RoleCreateOne.getInstance())
                .withArgs(roleName)
                .sendRequest();
        TestLogger.info("Tried to get the same role again");

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

        TestLogger.info("Test passed!");
    }
}
