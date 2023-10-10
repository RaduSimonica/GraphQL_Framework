package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.SkillCreateOne;
import ro.crownstudio.api.factory.operations.SkillDeleteOne;
import ro.crownstudio.api.factory.operations.SkillFindOne;
import ro.crownstudio.api.pojo.DeleteResult;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

import java.util.UUID;

public class TestGetSingleDeletedSkill extends BaseClass {

    // There's a bit of confusion in this scenario. We have "deletedAt" property, that seems to not be used.
    // After a skill is deleted, it cannot be found anymore. What's the scope of "deletedAt" in this case?

    @Test
    public void testgetSingleDeletedSkill() {
        String skillName = "Created Test Skill " + UUID.randomUUID();
        GraphQLResponse response = client.sendRequest(
                RequestFactory.builder()
                        .operation(new SkillCreateOne())
                        .withArgs(skillName)
                        .asJson()
        );
        Skill createdSkill = responseProcessor.assertAndReturn(response, Skill.class);
        TestLogger.info("Created Skill with id: {}", createdSkill.getId());

        GraphQLResponse deleteSkillResponse = client.sendRequest(
                RequestFactory.builder()
                        .operation(new SkillDeleteOne())
                        .withArgs(createdSkill.getId())
                        .asJson()
        );
        DeleteResult deleteResult = responseProcessor.assertAndReturn(deleteSkillResponse, DeleteResult.class);
        TestLogger.info(
                "Deleted Skill with id: {}. Entities affected: {}",
                createdSkill.getId(),
                deleteResult.getAffected()
        );
        Assert.assertEquals(deleteResult.getAffected(), 1);

        GraphQLResponse getResponse = client.sendRequest(
                RequestFactory.builder()
                        .operation(new SkillFindOne())
                        .withArgs(createdSkill.getId())
                        .asJson()
        );

        Skill skillAfterDeletion = responseProcessor.assertAndReturn(getResponse, Skill.class);
        TestLogger.info(
                "Tried to get deleted skill with id: {}. Result is: {}",
                createdSkill.getId(),
                skillAfterDeletion
        );
        Assert.assertNull(
                skillAfterDeletion,
                "Skill found after deletion"
        );
        TestLogger.info("Test passed!");
    }
}
