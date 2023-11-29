package com.mikaioyamada.Gestao_Vagas.modules.company.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikaioyamada.Gestao_Vagas.exceptions.CompanyNotFoundException;
import com.mikaioyamada.Gestao_Vagas.modules.company.dto.CreateJobDTO;
import com.mikaioyamada.Gestao_Vagas.modules.company.entities.CompanyEntity;
import com.mikaioyamada.Gestao_Vagas.modules.company.repositories.CompanyRepository;
import com.mikaioyamada.Gestao_Vagas.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ActiveProfiles("test") //tem a função de utilizar o application-test.properties.
public class CreateJobControllerTest {
    @Autowired
    private MockMvc mvc; //é uma biblioteca que permite simular o servidor

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;
    @Before
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context).apply(SecurityMockMvcConfigurers.springSecurity()).build();
    }
    @Test
    public void should_be_able_to_create_a_new_job() throws Exception {
        var company = CompanyEntity.builder()
                .description("COMPANY_DESCRIPTION")
                .email("email@company.com")
                .password("1231231313")
                .username("COMPANY_USERNAME")
                .name("COMPANY_NAME").build();

            company = companyRepository.saveAndFlush(company);

        var createJob = CreateJobDTO.builder()
                        .benefits("BENEFITS_TEST").description("DESCRIPTION_TEST")
                        .level("LEVEL_TEST").build();

        var result = mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(createJob))
                        .header("Authorization", TestUtils.generateToken(company.getId(), "JAVAGAS_@123#")))
                .andExpect(MockMvcResultMatchers.status().isOk()); //recebe o conteúdo de CreateJob e realiza um teste esperando o status.ok
        System.out.println(result);
    }
    @Test
    public void should_not_be_able_create_a_new_job_if_company_not_found() throws Exception {
        var createJob = CreateJobDTO.builder()
                .benefits("BENEFITS_TEST").description("DESCRIPTION_TEST")
                .level("LEVEL_TEST").build();

            mvc.perform(MockMvcRequestBuilders.post("/company/job/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtils.objectToJson(createJob))
                    .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "JAVAGAS_@123#")))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest());


    }


}
