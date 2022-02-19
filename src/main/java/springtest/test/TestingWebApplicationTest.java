package springtest.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import springtest.ServingWebContent;


@SpringBootTest(classes = {ServingWebContent.class})
public class TestingWebApplicationTest {

    @Test
    public void contextLoads() {
    }

}