package ro.crownstudio.core;

import com.google.gson.Gson;
import lombok.Getter;
import okhttp3.OkHttpClient;
import org.testng.Assert;
import ro.crownstudio.api.actions.Mutation;
import ro.crownstudio.api.actions.Query;
import ro.crownstudio.api.pojo.Asd;
import ro.crownstudio.api.pojo.GraphQLResponse;
import ro.crownstudio.api.pojo.Role;
import ro.crownstudio.api.pojo.Skill;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestData {

    private static TestData INSTANCE;

    private final ApiClient client;
    private final ResponseProcessor responseProcessor;

    @Getter
    private final List<Skill> testSkills;
    @Getter
    private final List<Role> testRoles;

    private TestData() {
        Gson gson = new Gson();
        client = new ApiClient(new OkHttpClient(), gson);
        responseProcessor = new ResponseProcessor(gson);

        testSkills = new ArrayList<>();
        testSkills.addAll(createTestSkills(4));
        testRoles = new ArrayList<>();
        List<Role> tempRoles = createTestRoles(3);
        testRoles.addAll(assignSkillsToRoles(tempRoles));
    }

    public static TestData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestData();
        }
        return INSTANCE;
    }

    public Role createTestRole(String roleName) {
        GraphQLResponse response = client.sendRequest(
                Mutation.ROLE_CREATE_ONE.getQuery(roleName)
        );
        return responseProcessor.assertAndReturn(response, Role.class);
    }

    public Skill createTestSkill(String skillName) {
        GraphQLResponse response = client.sendRequest(
                Mutation.SKILL_CREATE_ONE.getQuery(skillName)
        );
        return responseProcessor.assertAndReturn(response, Skill.class);
    }

    private List<Role> createTestRoles(int numberOfRoles) {
        List<Role> roles = new ArrayList<>();
        for (int i = 0; i < numberOfRoles; i++) {
            roles.add(createTestRole("Test Role " + UUID.randomUUID()));
        }
        return roles;
    }

    private List<Skill> createTestSkills(int NumberOfSkills) {
        List<Skill> skills = new ArrayList<>();
        for (int i = 0; i < NumberOfSkills; i++) {
            skills.add(createTestSkill("Test Skill " + UUID.randomUUID()));
        }
        return skills;
    }

    private List<Role> assignSkillsToRoles(List<Role> roles) {
        for (Role role : roles) {
            List<Asd> roleSkills = new ArrayList<>();
            if (testSkills.size() < 1) {
                break;
            }
            float weight = (float) (1.0 / testSkills.size());
            for (Skill skill : testSkills) {
                roleSkills.add(new Asd(weight, skill.getId()));
            }
            GraphQLResponse response = client.sendRequest(
                    Mutation.ROLE_SKILLS_OVERWRITE.getQuery(role.getId(), roleSkills)
            );
            Assert.assertNull(response.getError(), "Cannot assign skills to role");
        }
        return refreshRoles(roles);
    }

    private List<Role> refreshRoles(List<Role> roles) {
        List<Role> refreshedRoles = new ArrayList<>();
        for (Role role : roles) {
            GraphQLResponse response = client.sendRequest(
                    Query.ROLE_FIND_ONE.getQuery(role.getId())
            );
            refreshedRoles.add(responseProcessor.assertAndReturn(response, Role.class));
        }
        return refreshedRoles;
    }
}
