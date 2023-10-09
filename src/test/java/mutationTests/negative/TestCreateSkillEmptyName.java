package mutationTests.negative;

import org.testng.Assert;
import org.testng.annotations.Test;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.core.BaseClass;

public class TestCreateSkillEmptyName extends BaseClass {

    @Test
    public void testCreateSkillEmptyName() {
        String skillname = "";
        GraphQLResponse response = client.sendRequest(
                Mutation.SKILL_CREATE_ONE.getQuery(skillname)
        );
        Role createdSkill = responseProcessor.assertAndReturn(response, Role.class);

        Assert.assertNotEquals(
                createdSkill.getName(),
                skillname,
                "Skill was created with empty name"
        );
    }
}
