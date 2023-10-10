package queryTests;

import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.Roles;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class TestGetAllRoles extends BaseClass {

    @Test
    public void testGetAllRoles() {
        GraphQLResponse graphQLResponse = client.sendRequest(
                RequestFactory.builder()
                        .operation(new Roles())
                        .asJson()
        );
        List<Role> actualRoles = Arrays.asList(responseProcessor.assertAndReturn(graphQLResponse, Role[].class));
        TestLogger.info("Found {} roles.", actualRoles.size());

        assertThat(actualRoles)
                .withFailMessage("Not all expected roles could be found.")
                .containsAll(testData.getTestRoles());

        TestLogger.info("Test passed!");
    }
}
