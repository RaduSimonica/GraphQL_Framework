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
import java.util.UUID;

public class TestDeleteRole extends BaseClass {

    @Test
    public void testDeleteRole() {
        String roleName = "Created Test Role " + UUID.randomUUID();
        GraphQLResponse response = client.sendRequest(
                Mutation.ROLE_CREATE_ONE.getQuery(roleName)
        );
        Role createdRole = responseProcessor.assertAndReturn(response, Role.class);

        GraphQLResponse deleteRoleResponse = client.sendRequest(
                Mutation.ROLE_DELETE_ONE.getQuery(createdRole.getId())
        );
        DeleteResult deleteResult = responseProcessor.assertAndReturn(deleteRoleResponse, DeleteResult.class);

        Assert.assertEquals(
                deleteResult.getAffected(),
                1,
                "Affected entities after deletion does not match the expected one"
        );
    }
}
