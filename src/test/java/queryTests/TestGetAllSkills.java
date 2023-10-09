package queryTests;

import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class TestGetAllSkills extends BaseClass {

    @Test
    public void testGetAllSkills() {
        GraphQLResponse graphQLResponse = client.sendRequest(Query.SKILLS.getQuery());
        List<Skill> actualSkills = Arrays.asList(responseProcessor.assertAndReturn(graphQLResponse, Skill[].class));
        TestLogger.info("Got {} skills", actualSkills.size());

        assertThat(actualSkills)
                .withFailMessage("Not all expected roles could be found")
                .containsAll(testData.getTestSkills());

        TestLogger.info("Test passed!");
    }
}
