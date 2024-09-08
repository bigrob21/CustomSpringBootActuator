package com.bigrob.actuator;

import com.bigrob.java21.CustomBuildInfo;
import com.bigrob.java21.Model;
import com.bigrob.java21.ModeledResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.function.Supplier;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = {InfoEndpointTest.TestContextConfiguration.class}, properties = {"management.endpoints.web.exposure.include=info",
        "management.endpoint.info.enabled=true"
})
class InfoEndpointTest {

    @Autowired private MockMvc mvc;

    @Test
    void testInfoEndpoint() throws Exception {
        final Model expectation = new ModeledResult();
        MvcResult result = mvc.perform(get("/actuator/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['model.version']").value(expectation.getVersion()))
                .andDo(print())
                .andReturn();
        String json  = result.getResponse().getContentAsString();
        System.out.println(json);
    }

    @TestConfiguration
    static class TestContextConfiguration {

        @Bean
        Supplier<String> versionNumberSupplier() {
            return () -> {
                Model model = new ModeledResult();
                return model.getVersion();
            };
        }

        @Bean
        InfoContributor contributor(Supplier<String> versionNumberSupplier) {
            return new CustomBuildInfo(versionNumberSupplier);
        }
    }

}
