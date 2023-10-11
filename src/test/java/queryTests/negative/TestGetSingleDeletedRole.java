package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleCreateOne;
import ro.crownstudio.api.factory.operations.RoleDeleteOne;
import ro.crownstudio.api.factory.operations.RoleFindOne;
import ro.crownstudio.api.pojo.DeleteResult;
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
        Role createdRole = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(RoleCreateOne.getInstance())
                .withArgs(roleName)
                .assertError()
                .getResponseObject();
        TestLogger.info("Created role with ID: {}", createdRole.getId());

        DeleteResult deleteResult = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(RoleDeleteOne.getInstance())
                .withArgs(createdRole.getId())
                .assertError()
                .getResponseObject();
        TestLogger.info(
                "Deleted role with ID: {}. Entities affected: {}",
                createdRole.getId(),
                deleteResult.getAffected()
        );
        Assert.assertEquals(deleteResult.getAffected(), 1);

        Role roleAfterDeletion = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(RoleFindOne.getInstance())
                .withArgs(createdRole.getId())
                .assertError()
                .getResponseObject();

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
