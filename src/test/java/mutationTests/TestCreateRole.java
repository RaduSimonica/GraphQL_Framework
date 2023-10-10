package mutationTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleCreateOne;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class TestCreateRole extends BaseClass {

    @Test
    public void testCreateRole() {
        String roleName = "Created Test Role " + UUID.randomUUID();
        GraphQLResponse response = client.sendRequest(
                RequestFactory.builder()
                        .operation(new RoleCreateOne())
                        .withArgs(roleName)
                        .asJson()
        );
        Role createdRole = responseProcessor.assertAndReturn(response, Role.class);
        TestLogger.info("Created role named: {} with id: {}", createdRole.getName(), createdRole.getId());

        Assert.assertTrue(
                createdRole.getCreatedAt().before(Date.from(Instant.now())),
                "Role creation date is not earlier than now"
        );
        Assert.assertNull(
                createdRole.getDeletedAt(),
                "Role has deletion date"
        );
        Assert.assertTrue(
                createdRole.getId() > 0,
                "Role Id is less or equal to 0"
        );
        Assert.assertEquals(
                createdRole.getName(),
                roleName,
                "Role name does not match the expected name"
        );
        Assert.assertTrue(
                createdRole.getSkills().isEmpty(),
                "Role has skills"
        );
        Assert.assertTrue(
                createdRole.getUpdatedAt().before(Date.from(Instant.now())),
                "Role updated date is not earlier than now"
        );

        TestLogger.info("Test passed!");
    }
}
