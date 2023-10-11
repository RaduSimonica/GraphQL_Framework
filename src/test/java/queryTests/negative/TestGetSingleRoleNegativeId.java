package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleFindOne;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetSingleRoleNegativeId extends BaseClass {

    @Test
    public void testGetSingleRoleNegativeId() {
        Role actualRole = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(RoleFindOne.getInstance())
                .withArgs(-20)
                .assertError()
                .getResponseObject();

        TestLogger.info("Tried to get role with negative ID: -20. Result is {}", actualRole);

        Assert.assertNull(actualRole, "Found a role with Id negative 20 (-20)");

        TestLogger.info("Test passed!");
    }
}
