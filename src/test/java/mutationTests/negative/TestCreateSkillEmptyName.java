package mutationTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.SkillCreateOne;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestCreateSkillEmptyName extends BaseClass {

    @Test
    public void testCreateSkillEmptyName() {
        String skillname = "";
        GraphQLResponse response = client.sendRequest(
                RequestFactory.builder()
                        .operation(new SkillCreateOne())
                        .withArgs(skillname)
                        .asJson()
        );
        Skill createdSkill = responseProcessor.assertAndReturn(response, Skill.class);
        TestLogger.info("Created skill named: {} with id: {}", createdSkill.getName(), createdSkill.getId());

        Assert.assertNotEquals(
                createdSkill.getName(),
                skillname,
                "Skill was created with empty name"
        );

        TestLogger.info("Test passed!");
    }
}
