package unitTests.ro.crownstudio.api.factory;

import org.testng.annotations.Test;
import ro.crownstudio.api.factory.RequestFactory;
import ro.crownstudio.api.factory.operations.RoleFindOne;

public class RequestFactoryTest {

    // TODO: Complete this test class
    @Test
    public void test() {
        String query = RequestFactory.builder().operation(new RoleFindOne()).withArgs(2).asJson();
        System.out.println(query);
    }

}