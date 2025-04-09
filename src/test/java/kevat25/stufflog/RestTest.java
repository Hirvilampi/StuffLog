package kevat25.stufflog;

//mm. mockMvc:n get- ja post-metodit
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


@SpringBootTest
public class RestTest {

        @Autowired
    private WebApplicationContext webAppContext;

    private MockMvc mockMvc;

    @BeforeEach // JUnitS
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void statusOk() throws Exception {
        mockMvc.perform(get("items")).andExpect(status().isOk());
        
    }

    @Test
    public void responseTypeApplicationJson() throws Exception {
        mockMvc.perform(get("/items"))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        // .andExpect(content().contentType(MediaType.APPLICATION_ATOM_XML_VALUE))
        .andExpect(status().isOk());
    }    

    
    @Test
    public void apiStatusOk() throws Exception {
        mockMvc.perform(get("/api/items")).andExpect(status().isOk());
    }

    
    
}
