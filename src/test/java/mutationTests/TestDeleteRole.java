package mutationTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleCreateOne;
import ro.crownstudio.api.factory.operations.RoleDeleteOne;
import ro.crownstudio.api.pojo.DeleteResult;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.util.UUID;

public class TestDeleteRole extends BaseClass {

    @Test
    public void testDeleteRole() {
        String roleName = "Created Test Role " + UUID.randomUUID();
        GraphQLResponse response = client.sendRequest(
                RequestFactory.builder()
                        .operation(new RoleCreateOne())
                        .withArgs(roleName)
                        .asJson()
        );
        Role createdRole = responseProcessor.assertAndReturn(response, Role.class);
        TestLogger.info("Created role named: {} with id: {}", createdRole.getName(), createdRole.getId());

        GraphQLResponse deleteRoleResponse = client.sendRequest(
                RequestFactory.builder()
                        .operation(new RoleDeleteOne())
                        .withArgs(createdRole.getId())
                        .asJson()
        );
        DeleteResult deleteResult = responseProcessor.assertAndReturn(deleteRoleResponse, DeleteResult.class);
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
