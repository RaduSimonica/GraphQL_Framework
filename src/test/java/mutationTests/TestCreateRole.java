package mutationTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;

import java.sql.Date;
import java.time.Instant;

public class TestCreateRole extends BaseClass {

    @Test
    public void testCreateRole() {
        GraphQLResponse response = client.sendRequest(
                Mutation.ROLE_CREATE_ONE.getQuery("Created Test Role")
        );
        Role createdRole = responseProcessor.assertAndReturn(response, Role.class);

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
                "Created Test Role",
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
    }
}
