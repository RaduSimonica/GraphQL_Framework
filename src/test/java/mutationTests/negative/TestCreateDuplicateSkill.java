package mutationTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.util.UUID;

public class TestCreateDuplicateSkill extends BaseClass {

    @Test
    public void testCreateDuplicateSKill() {
        String skillName = "Test Skill " + UUID.randomUUID();
        GraphQLResponse response1 = client.sendRequest(
                Mutation.SKILL_CREATE_ONE.getQuery(skillName)
        );
        Skill createdSkill = responseProcessor.assertAndReturn(response1, Skill.class);
        TestLogger.info(
                "Created skill named {} with id: {}",
                createdSkill.getName(),
                createdSkill.getId()
        );

        Assert.assertNotNull(
                createdSkill,
                "1st skill could not be created"
        );

        // Create the same role again
        GraphQLResponse response2 = client.sendRequest(
                Mutation.SKILL_CREATE_ONE.getQuery(skillName)
        );
        TestLogger.info("Tried to create the same skill again.");

        Assert.assertNotNull(
                response2.getError(),
                "No error was return while trying to create a duplicate skill"
        );
        Assert.assertFalse(
                response2.getError().isEmpty(),
                "No error was return while trying to create a duplicate skill"
        );

        // The current error message: "an error occurred" is too generic.
        Assert.assertEquals(
                response2.getError().get(0).getMessage(),
                "Cannot create two skills with the same name."
        );
        TestLogger.info("Test passed!");
    }
}
