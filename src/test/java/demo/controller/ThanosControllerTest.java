package demo.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.entity.Thanos;
import demo.exception.ResourceNotFoundException;
import demo.model.request.ThanosRequest;
import demo.service.thanosServiceInterface;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ThanosController.class})
@ExtendWith(SpringExtension.class)
class ThanosControllerTest {
    @Autowired
    private ThanosController thanosController;

    @MockBean
    private thanosServiceInterface thanosServiceInterface;

    /**
     * Method under test: {@link ThanosController#addThanos(ThanosRequest)}
     */
    @Test
    void testAddThanos() throws Exception {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");
        when(this.thanosServiceInterface.saveMember((ThanosRequest) any())).thenReturn(thanos);

        ThanosRequest thanosRequest = new ThanosRequest();
        thanosRequest.setDepartment("Department");
        thanosRequest.setFirstName("Jane");
        thanosRequest.setLastName("Doe");
        thanosRequest.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(thanosRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/thanos/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"department\":\"Department\",\"email\":\"jane.doe@example"
                                        + ".org\"}"));
    }

    /**
     * Method under test: {@link ThanosController#addThanos(ThanosRequest)}
     */
    @Test
    void testAddThanos2() throws Exception {
        when(this.thanosServiceInterface.saveMember((ThanosRequest) any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        ThanosRequest thanosRequest = new ThanosRequest();
        thanosRequest.setDepartment("Department");
        thanosRequest.setFirstName("Jane");
        thanosRequest.setLastName("Doe");
        thanosRequest.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(thanosRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/thanos/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ThanosController#deleteThanos(int)}
     */
    @Test
    void testDeleteThanos() throws Exception {
        doNothing().when(this.thanosServiceInterface).deleteMember(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/thanos/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    /**
     * Method under test: {@link ThanosController#deleteThanos(int)}
     */
    @Test
    void testDeleteThanos2() throws Exception {
        doNothing().when(this.thanosServiceInterface).deleteMember(anyInt());
        MockHttpServletRequestBuilder deleteResult = MockMvcRequestBuilders.delete("/thanos/{id}", 1);
        deleteResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(deleteResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isAccepted());
    }

    /**
     * Method under test: {@link ThanosController#deleteThanos(int)}
     */
    @Test
    void testDeleteThanos3() throws Exception {
        doThrow(new ResourceNotFoundException("An error occurred")).when(this.thanosServiceInterface)
                .deleteMember(anyInt());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/thanos/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ThanosController#getAllThanos(String)}
     */
    @Test
    void testGetAllThanos() throws Exception {
        when(this.thanosServiceInterface.getAllMembers((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/thanos/all").param("sort", "foo");
        MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ThanosController#getAllThanos(String)}
     */
    @Test
    void testGetAllThanos2() throws Exception {
        Thanos thanos = new Thanos();
        thanos.setDepartment("?");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");

        ArrayList<Thanos> thanosList = new ArrayList<>();
        thanosList.add(thanos);
        when(this.thanosServiceInterface.getAllMembers((String) any())).thenReturn(thanosList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/thanos/all").param("sort", "foo");
        MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"department\":\"?\",\"email\":\"jane.doe@example.org\"}]"));
    }

    /**
     * Method under test: {@link ThanosController#getAllThanos(String)}
     */
    @Test
    void testGetAllThanos3() throws Exception {
        when(this.thanosServiceInterface.getAllMembers((String) any())).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/thanos/all");
        getResult.contentType("https://example.org/example");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("sort", "foo");
        MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ThanosController#getAllThanos(String)}
     */
    @Test
    void testGetAllThanos4() throws Exception {
        when(this.thanosServiceInterface.getAllMembers((String) any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/thanos/all").param("sort", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ThanosController#getAllThanos(String)}
     */
    @Test
    void testGetAllThanos5() throws Exception {
        Thanos thanos = new Thanos();
        thanos.setDepartment("?");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");

        Thanos thanos1 = new Thanos();
        thanos1.setDepartment("?");
        thanos1.setEmail("jane.doe@example.org");
        thanos1.setFirstName("Jane");
        thanos1.setId(1);
        thanos1.setLastName("Doe");
        thanos1.setPassword("iloveyou");

        ArrayList<Thanos> thanosList = new ArrayList<>();
        thanosList.add(thanos1);
        thanosList.add(thanos);
        when(this.thanosServiceInterface.getAllMembers((String) any())).thenReturn(thanosList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/thanos/all").param("sort", "foo");
        MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"department\":\"?\",\"email\":\"jane.doe@example.org\"},{\"id\":1"
                                        + ",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"department\":\"?\",\"email\":\"jane.doe@example.org\"}]"));
    }

    /**
     * Method under test: {@link ThanosController#getThanosByID(int)}
     */
    @Test
    void testGetThanosByID() throws Exception {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");
        when(this.thanosServiceInterface.getMemberById(anyInt())).thenReturn(thanos);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/thanos/{id}", 1);
        MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"department\":\"Department\",\"email\":\"jane.doe@example"
                                        + ".org\"}"));
    }

    /**
     * Method under test: {@link ThanosController#getThanosByID(int)}
     */
    @Test
    void testGetThanosByID2() throws Exception {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");
        when(this.thanosServiceInterface.getMemberById(anyInt())).thenReturn(thanos);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/thanos/{id}", 1);
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"department\":\"Department\",\"email\":\"jane.doe@example"
                                        + ".org\"}"));
    }

    /**
     * Method under test: {@link ThanosController#getThanosByID(int)}
     */
    @Test
    void testGetThanosByID3() throws Exception {
        when(this.thanosServiceInterface.getMemberById(anyInt()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/thanos/{id}", 1);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ThanosController#getThanosInPage(int, int, String)}
     */
    @Test
    void testGetThanosInPage() throws Exception {
        when(this.thanosServiceInterface.getPagingMembers(anyInt(), anyInt(), (String) any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/thanos/get");
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1)).param("sort", "foo");
        MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ThanosController#getThanosInPage(int, int, String)}
     */
    @Test
    void testGetThanosInPage2() throws Exception {
        Thanos thanos = new Thanos();
        thanos.setDepartment("?");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");

        ArrayList<Thanos> thanosList = new ArrayList<>();
        thanosList.add(thanos);
        when(this.thanosServiceInterface.getPagingMembers(anyInt(), anyInt(), (String) any())).thenReturn(thanosList);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/thanos/get");
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1)).param("sort", "foo");
        MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"department\":\"?\",\"email\":\"jane.doe@example.org\"}]"));
    }

    /**
     * Method under test: {@link ThanosController#getThanosInPage(int, int, String)}
     */
    @Test
    void testGetThanosInPage3() throws Exception {
        when(this.thanosServiceInterface.getPagingMembers(anyInt(), anyInt(), (String) any()))
                .thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/thanos/get");
        getResult.contentType("https://example.org/example");
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1)).param("sort", "foo");
        MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link ThanosController#getThanosInPage(int, int, String)}
     */
    @Test
    void testGetThanosInPage4() throws Exception {
        when(this.thanosServiceInterface.getPagingMembers(anyInt(), anyInt(), (String) any()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/thanos/get");
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1)).param("sort", "foo");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Method under test: {@link ThanosController#getThanosInPage(int, int, String)}
     */
    @Test
    void testGetThanosInPage5() throws Exception {
        Thanos thanos = new Thanos();
        thanos.setDepartment("?");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");

        Thanos thanos1 = new Thanos();
        thanos1.setDepartment("?");
        thanos1.setEmail("jane.doe@example.org");
        thanos1.setFirstName("Jane");
        thanos1.setId(1);
        thanos1.setLastName("Doe");
        thanos1.setPassword("iloveyou");

        ArrayList<Thanos> thanosList = new ArrayList<>();
        thanosList.add(thanos1);
        thanosList.add(thanos);
        when(this.thanosServiceInterface.getPagingMembers(anyInt(), anyInt(), (String) any())).thenReturn(thanosList);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/thanos/get");
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1)).param("sort", "foo");
        MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "[{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"department\":\"?\",\"email\":\"jane.doe@example.org\"},{\"id\":1"
                                        + ",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"department\":\"?\",\"email\":\"jane.doe@example.org\"}]"));
    }

    /**
     * Method under test: {@link ThanosController#updateThanos(int, ThanosRequest)}
     */
    @Test
    void testUpdateThanos() throws Exception {
        Thanos thanos = new Thanos();
        thanos.setDepartment("Department");
        thanos.setEmail("jane.doe@example.org");
        thanos.setFirstName("Jane");
        thanos.setId(1);
        thanos.setLastName("Doe");
        thanos.setPassword("iloveyou");
        when(this.thanosServiceInterface.updateMember((ThanosRequest) any(), anyInt())).thenReturn(thanos);

        ThanosRequest thanosRequest = new ThanosRequest();
        thanosRequest.setDepartment("Department");
        thanosRequest.setFirstName("Jane");
        thanosRequest.setLastName("Doe");
        thanosRequest.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(thanosRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/thanos/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"department\":\"Department\",\"email\":\"jane.doe@example"
                                        + ".org\"}"));
    }

    /**
     * Method under test: {@link ThanosController#updateThanos(int, ThanosRequest)}
     */
    @Test
    void testUpdateThanos2() throws Exception {
        when(this.thanosServiceInterface.updateMember((ThanosRequest) any(), anyInt()))
                .thenThrow(new ResourceNotFoundException("An error occurred"));

        ThanosRequest thanosRequest = new ThanosRequest();
        thanosRequest.setDepartment("Department");
        thanosRequest.setFirstName("Jane");
        thanosRequest.setLastName("Doe");
        thanosRequest.setPassword("iloveyou");
        String content = (new ObjectMapper()).writeValueAsString(thanosRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/thanos/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.thanosController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

