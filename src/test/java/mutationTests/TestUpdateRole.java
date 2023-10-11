package mutationTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleCreateOne;
import ro.crownstudio.api.factory.operations.RoleFindOne;
import ro.crownstudio.api.factory.operations.RoleUpdateOne;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class TestUpdateRole extends BaseClass {

    @Test
    public void testUpdateRole() {
        String roleName = "Created Test Role " + UUID.randomUUID();
        String updatedRoleName = "Updated Test Role " + UUID.randomUUID();

        Role createdRole = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(RoleCreateOne.getInstance())
                .withArgs(roleName)
                .assertError()
                .getResponseObject();

        TestLogger.info("Created role named: {} with id: {}", createdRole.getName(), createdRole.getId());

        Role updatedRole = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(RoleUpdateOne.getInstance())
                .withArgs(createdRole.getId(), updatedRoleName)
                .assertError()
                .getResponseObject();

        TestLogger.info("Updated role with id: {}. New name is {}", createdRole.getId(), updatedRole.getName());

        Role refreshedRole = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(RoleFindOne.getInstance())
                .withArgs(createdRole.getId())
                .assertError()
                .getResponseObject();

        Assert.assertEquals(
                updatedRole.getCreatedAt(),
                createdRole.getCreatedAt(),
                "Creation date changed after update"
        );
        Assert.assertNull(
                updatedRole.getDeletedAt(),
                "Role has deleted date after update"
        );
        Assert.assertEquals(
                updatedRole.getId(),
                createdRole.getId(),
                "Role id changed after update"
        );
        Assert.assertEquals(
                updatedRole.getName(),
                updatedRoleName,
                "Role name did not change after update"
        );
        Assert.assertTrue(
                updatedRole.getSkills().isEmpty(),
                "Role has skills after update"
        );
        Assert.assertTrue(
                updatedRole.getUpdatedAt().before(Date.from(Instant.now())),
                "Role updated date is not before now"
        );
        Assert.assertTrue(
                updatedRole.getUpdatedAt().after(updatedRole.getCreatedAt()),
                "Role updated date is not after created date"
        );

        // Bonus: Check if getting the role by ID after update, equals the role provided by the update mutation
        Assert.assertEquals(
                refreshedRole,
                updatedRole,
                "Role after update does not match role provided by update mutation"
        );

        TestLogger.info("Test passed!");
    }
}
