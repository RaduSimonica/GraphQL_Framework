package mutationTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

public class TestUpdateRole extends BaseClass {

    @Test
    public void testUpdateRole() {
        String roleName = "Created Test Role " + UUID.randomUUID();
        String updatedRoleName = "Updated Test Role " + UUID.randomUUID();

        GraphQLResponse createdRoleResponse = client.sendRequest(
                Mutation.ROLE_CREATE_ONE.getQuery(roleName)
        );
        Role createdRole = responseProcessor.assertAndReturn(createdRoleResponse, Role.class);

        GraphQLResponse updatedRoleResponse = client.sendRequest(
                Mutation.ROLE_UPDATE_ONE.getQuery(createdRole.getId(), updatedRoleName)
        );
        Role updatedRole = responseProcessor.assertAndReturn(updatedRoleResponse, Role.class);

        GraphQLResponse refreshedRoleResponse = client.sendRequest(
                Query.ROLE_FIND_ONE.getQuery(createdRole.getId())
        );
        Role refreshedRole = responseProcessor.assertAndReturn(refreshedRoleResponse, Role.class);

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
    }
}
