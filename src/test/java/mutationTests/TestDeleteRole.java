package mutationTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleCreateOne;
import ro.crownstudio.api.factory.operations.RoleDeleteOne;
import ro.crownstudio.api.pojo.DeleteResult;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.util.UUID;

public class TestDeleteRole extends BaseClass {

    @Test
    public void testDeleteRole() {
        String roleName = "Created Test Role " + UUID.randomUUID();
        Role createdRole = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(RoleCreateOne.getInstance())
                .withArgs(roleName)
                .assertError()
                .getResponseObject();

        TestLogger.info("Created role named: {} with id: {}", createdRole.getName(), createdRole.getId());

        DeleteResult deleteResult = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(RoleDeleteOne.getInstance())
                .withArgs(createdRole.getId())
                .assertError()
                .getResponseObject();

        TestLogger.info(
                "Deleted role with id: {}. Entities affected: {}",
                createdRole.getId(),
                deleteResult.getAffected()
        );

        Assert.assertEquals(
                deleteResult.getAffected(),
                1,
                "Affected entities after deletion does not match the expected one"
        );
        TestLogger.info("Test passed!");
    }
}
