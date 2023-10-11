package queryTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.SkillFindOne;
import ro.crownstudio.api.pojo.Skill;
import ro.crownstudio.core.BaseClass;
import ro.crownstudio.core.TestLogger;

public class TestGetSingleSkillNegativeId extends BaseClass {

    @Test
    public void testGetSingleSkillNegativeId() {
        Skill actualSkill = RequestFactory.builder()
                .responseProcessor(responseProcessor)
                .apiClient(client)
                .operation(SkillFindOne.getInstance())
                .withArgs(-20)
                .assertError()
                .getResponseObject();

        TestLogger.info("Tried to get single skill with id: -20. Result is {}", actualSkill);

        Assert.assertNull(actualSkill, "Found a skill with Id negative 20 (-20)");

        TestLogger.info("Test passed!");
    }
}
