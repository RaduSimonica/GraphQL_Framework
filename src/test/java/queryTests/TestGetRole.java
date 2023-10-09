package queryTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetRole extends BaseClass {

    @Test
    public void testGetSingleRoleById() {
        Role expectedRole = testData.getTestRoles().get(0);
        GraphQLResponse graphQLResponse = client.sendRequest(Query.ROLE_FIND_ONE.getQuery(expectedRole.getId()));
        Role actualRole = responseProcessor.assertAndReturn(graphQLResponse, Role.class);
        TestLogger.info("Got role named: {}", actualRole.getName());

        Assert.assertEquals(
                actualRole,
                expectedRole,
                "Actual role does not match the expected role"
        );

        TestLogger.info("Test passed!");
    }
}
