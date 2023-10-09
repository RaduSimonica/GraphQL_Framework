package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetSingleRoleNegativeId extends BaseClass {

    @Test
    public void testGetSingleRoleNegativeId() {
        GraphQLResponse graphQLResponse = client.sendRequest(Query.ROLE_FIND_ONE.getQuery(-20));
        Role actualRole = responseProcessor.assertAndReturn(graphQLResponse, Role.class);
        TestLogger.info("Tried to get role with negative ID: -20. Result is {}", actualRole);

        Assert.assertNull(actualRole, "Found a role with Id negative 20 (-20)");

        TestLogger.info("Test passed!");
    }
}
