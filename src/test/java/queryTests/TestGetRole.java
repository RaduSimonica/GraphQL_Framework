package queryTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleFindOne;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetRole extends BaseClass {

    @Test
    public void testGetSingleRoleById() {
        Role expectedRole = testData.getTestRoles().get(0);
        GraphQLResponse graphQLResponse = client.sendRequest(
                RequestFactory.builder()
                        .operation(new RoleFindOne())
                        .withArgs(expectedRole.getId())
                        .asJson()
        );
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
