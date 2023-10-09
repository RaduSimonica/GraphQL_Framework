package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;

public class TestGetSingleRoleNonExistentId extends BaseClass {

    @Test
    public void testGetSingleRoleNonExistentId() {
        GraphQLResponse graphQLResponse = client.sendRequest(Query.ROLE_FIND_ONE.getQuery(Integer.MAX_VALUE));
        Role actualRole = responseProcessor.assertAndReturn(graphQLResponse, Role.class);

        Assert.assertNull(actualRole, "Found a role with Id " + Integer.MAX_VALUE);
    }
}
