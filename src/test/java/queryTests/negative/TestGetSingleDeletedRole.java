package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.DeleteResult;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.util.UUID;

public class TestGetSingleDeletedRole extends BaseClass {

    // There's a bit of confusion in this scenario. We have "deletedAt" property, that seems to not be used.
    // After a role is deleted, it cannot be found anymore. What's the scope of "deletedAt" in this case?

    @Test
    public void testGetSingleDeletedRole() {
        String roleName = "Created Test Role " + UUID.randomUUID();
        GraphQLResponse response = client.sendRequest(
                Mutation.ROLE_CREATE_ONE.getQuery(roleName)
        );
        Role createdRole = responseProcessor.assertAndReturn(response, Role.class);
        TestLogger.info("Created role with ID: {}", createdRole.getId());

        GraphQLResponse deleteRoleResponse = client.sendRequest(
                Mutation.ROLE_DELETE_ONE.getQuery(createdRole.getId())
        );
        DeleteResult deleteResult = responseProcessor.assertAndReturn(deleteRoleResponse, DeleteResult.class);
        TestLogger.info(
                "Deleted role with ID: {}. Entities affected: {}",
                createdRole.getId(),
                deleteResult.getAffected()
        );
        Assert.assertEquals(deleteResult.getAffected(), 1);

        GraphQLResponse getResponse = client.sendRequest(
                Query.ROLE_FIND_ONE.getQuery(createdRole.getId())
        );

        Role roleAfterDeletion = responseProcessor.assertAndReturn(getResponse, Role.class);
        TestLogger.info("Tried to delete role with ID: {} again. Entities affected: {}",
                createdRole.getId(),
                deleteResult.getAffected()
        );

        Assert.assertNull(
                roleAfterDeletion,
                "Role found after deletion"
        );

        TestLogger.info("Test passed!");
    }
}
