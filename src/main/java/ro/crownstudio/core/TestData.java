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

    @Getter
    private final TestDataHelper helper;

    @Getter
    private final List<Skill> testSkills;
    @Getter
    private final List<Role> testRoles;

    private TestData() {
        Gson gson = new Gson();
        ApiClient client = new ApiClient(new OkHttpClient(), gson);
        ResponseProcessor responseProcessor = new ResponseProcessor(gson);

        helper = new TestDataHelper(client, responseProcessor);

        testSkills = new ArrayList<>();
        testSkills.addAll(helper.createTestSkills(4));
        testRoles = new ArrayList<>();
        List<Role> tempRoles = helper.createTestRoles(3);
        testRoles.addAll(helper.assignSkillsToRoles(tempRoles, testSkills));
    }

    public static TestData getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TestData();
        }
        return INSTANCE;
    }
}
