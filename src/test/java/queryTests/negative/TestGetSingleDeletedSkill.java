package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.SkillCreateOne;
import ro.crownstudio.api.factory.operations.SkillDeleteOne;
import ro.crownstudio.api.factory.operations.SkillFindOne;
import ro.crownstudio.api.pojo.DeleteResult;
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
        Skill createdSkill = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(SkillCreateOne.getInstance())
                .withArgs(skillName)
                .assertError()
                .getResponseObject();

        TestLogger.info("Created Skill with id: {}", createdSkill.getId());

        DeleteResult deleteResult = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(SkillDeleteOne.getInstance())
                .withArgs(createdSkill.getId())
                .assertError()
                .getResponseObject();

        TestLogger.info(
                "Deleted Skill with id: {}. Entities affected: {}",
                createdSkill.getId(),
                deleteResult.getAffected()
        );
        Assert.assertEquals(deleteResult.getAffected(), 1);

        Skill skillAfterDeletion = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(SkillFindOne.getInstance())
                .withArgs(createdSkill.getId())
                .assertError()
                .getResponseObject();

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
