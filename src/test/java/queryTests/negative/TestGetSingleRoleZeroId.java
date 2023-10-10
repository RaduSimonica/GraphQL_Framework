package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleFindOne;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetSingleRoleZeroId extends BaseClass {

    @Test
    public void testGetSingleRoleZeroId() {
        GraphQLResponse graphQLResponse = client.sendRequest(
                RequestFactory.builder()
                        .operation(new RoleFindOne())
                        .withArgs(0)
                        .asJson()
        );
        Role actualRole = responseProcessor.assertAndReturn(graphQLResponse, Role.class);
        TestLogger.info("Tried to get role with id: 0. Result is: {}", actualRole);

        Assert.assertNull(actualRole, "Found a role with Id zero (0)");

        TestLogger.info("Test passed!");
    }
}
