package queryTests;

import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class TestGetAllRoles extends BaseClass {

    @Test
    public void testGetAllRoles() {
        GraphQLResponse graphQLResponse = client.sendRequest(Query.ROLES.getQuery());
        List<Role> actualRoles = Arrays.asList(responseProcessor.assertAndReturn(graphQLResponse, Role[].class));
        assertThat(actualRoles)
                .withFailMessage("Not all expected roles could be found.")
                .containsAll(testData.getTestRoles());
    }
}
