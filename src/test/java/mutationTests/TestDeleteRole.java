package mutationTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.DeleteResult;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;

import java.sql.Date;
import java.time.Instant;

public class TestDeleteRole extends BaseClass {

    @Test
    public void testCreateRole() {
        GraphQLResponse response = client.sendRequest(
                Mutation.ROLE_CREATE_ONE.getQuery("Created Test Role")
        );
        Role createdRole = responseProcessor.assertAndReturn(response, Role.class);

        GraphQLResponse deleteRoleResponse = client.sendRequest(
                Mutation.ROLE_DELETE_ONE.getQuery(createdRole.getId())
        );
        DeleteResult deleteResult = responseProcessor.assertAndReturn(deleteRoleResponse, DeleteResult.class);

        Assert.assertEquals(deleteResult.getAffected(), 1);

        GraphQLResponse refreshedRoleResponse = client.sendRequest(
                Query.ROLE_FIND_ONE.getQuery(createdRole.getId())
        );

        Role refreshedRole = responseProcessor.assertAndReturn(refreshedRoleResponse, Role.class);

        Assert.assertNull(
                refreshedRole,
                "Role found after deletion"
        );

    }
}
