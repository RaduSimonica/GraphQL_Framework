package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetSingleRoleNonExistentId extends BaseClass {

    @Test
    public void testGetSingleRoleNonExistentId() {
        GraphQLResponse graphQLResponse = client.sendRequest(Query.ROLE_FIND_ONE.getQuery(Integer.MAX_VALUE));
        Role actualRole = responseProcessor.assertAndReturn(graphQLResponse, Role.class);
        TestLogger.info("Tried to get role with ID {}. Result is {}", Integer.MAX_VALUE, actualRole);

        Assert.assertNull(actualRole, "Found a role with Id " + Integer.MAX_VALUE);

        TestLogger.info("Test passed!");
    }
}
