package springtest.test;


import com.jayway.jsonpath.JsonPath;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import springtest.ServingWebContent;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {ServingWebContent.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AddressBookTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getBook() throws Exception {
        this.mockMvc.perform(get("/addressbook/1")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void createBook() throws Exception {
        MvcResult result = this.mockMvc.perform(post("/addressbook").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                "\t\"bookOwner\":\"http://localhost:8080/buddyinfo/2\"\n" +
                "}")).andDo(print()).andExpect(status().isCreated()).andReturn();

        String url = result.getResponse().getRedirectedUrl();

//        this.mockMvc.perform(get(url)).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("\"href\" : \"http://localhost/addressbook/6/bookOwner\"")));


    }
}
