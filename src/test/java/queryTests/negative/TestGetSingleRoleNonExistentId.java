package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleFindOne;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetSingleRoleNonExistentId extends BaseClass {

    @Test
    public void testGetSingleRoleNonExistentId() {
        Role actualRole = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(RoleFindOne.getInstance())
                .withArgs(Integer.MAX_VALUE)
                .assertError()
                .getResponseObject();

        TestLogger.info("Tried to get role with ID {}. Result is {}", Integer.MAX_VALUE, actualRole);

        Assert.assertNull(actualRole, "Found a role with Id " + Integer.MAX_VALUE);

        TestLogger.info("Test passed!");
    }
}
