package queryTests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.SkillFindOne;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetSkill extends BaseClass {

    @Test
    public void testGetSingleSkill() {
        Skill expectedSkill = testData.getTestSkills().get(0);
        Skill actualSkill = RequestFactory.builder()
                .apiClient(client)
                .responseProcessor(responseProcessor)
                .operation(SkillFindOne.getInstance())
                .withArgs(expectedSkill.getId())
                .assertError()
                .getResponseObject();

        TestLogger.info("Got skill named: {}", actualSkill.getName());

        Assert.assertEquals(
                actualSkill,
                expectedSkill,
                "Actual skill does not match the expected role"
        );

        TestLogger.info("Test passed!");
    }
}
