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

public class TestUpdateRole extends BaseClass {

    @Test
    public void testUpdateRole() {
        GraphQLResponse createdRoleResponse = client.sendRequest(
                Mutation.ROLE_CREATE_ONE.getQuery("Created Test Role")
        );
        Role createdRole = responseProcessor.assertAndReturn(createdRoleResponse, Role.class);

        GraphQLResponse updatedRoleResponse = client.sendRequest(
                Mutation.ROLE_UPDATE_ONE.getQuery(createdRole.getId(), "Updated Test Role")
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
                "Updated Test Role",
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
