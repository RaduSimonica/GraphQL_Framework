package queryTests;

import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.Roles;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import static org.assertj.core.api.Assertions.assertThat;


public class TestGetAllRoles extends BaseClass {

    @Test
    public void testGetAllRoles() {
        Role[] actualRoles = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(Roles.getInstance())
                .assertError()
                .getResponseObject();

        TestLogger.info("Found {} roles.", actualRoles.length);

        assertThat(actualRoles)
                .withFailMessage("Not all expected roles could be found.")
                .containsAll(testData.getTestRoles());

        TestLogger.info("Test passed!");
    }
}
