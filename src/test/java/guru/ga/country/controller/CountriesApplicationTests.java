package guru.ga.country.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.ga.country.domain.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class CountriesApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Sql(scripts = "/addedUsersToDb.sql")
    void findAllCountries() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/country/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].countryName").value("Laos"))
                .andExpect(jsonPath("$[0].code").value("LA"))
                .andExpect(jsonPath("$[0].totalArea").value(236800));
    }

    @Test
    void addCountry() throws Exception {
        Country country = new Country("Laos", "LA", 236800);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/country/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(country)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.countryName").value("Laos"))
                .andExpect(jsonPath("$.code").value("LA"))
                .andExpect(jsonPath("$.totalArea").value(236800));
    }

    @Test
    @Sql(scripts = "/addedUsersToDb.sql")
    void editCountry() throws Exception {
        Country country = new Country("Laos", "RU", 17125191);
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/country/edit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(country)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.countryName").value("Laos"))
                .andExpect(jsonPath("$.code").value("RU"))
                .andExpect(jsonPath("$.totalArea").value(17125191));
    }
}
