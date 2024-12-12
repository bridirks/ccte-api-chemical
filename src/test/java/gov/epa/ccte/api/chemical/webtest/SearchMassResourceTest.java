package gov.epa.ccte.api.chemical.webtest;

import gov.epa.ccte.api.chemical.domain.SearchMassAndFormula;
import gov.epa.ccte.api.chemical.repository.SearchMassAndFormulaRepository;
import gov.epa.ccte.api.chemical.web.rest.SearchMassResource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SearchMassResource.class)
public class SearchMassResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchMassAndFormulaRepository searchMassAndFormulaRepository;


    private Page<SearchMassAndFormula> mockPage;

    @BeforeEach
    void setup() {
        SearchMassAndFormula mockData = new SearchMassAndFormula();
        mockData.setDtxsid("DTXSID7020182");
        mockPage = new PageImpl<>(Collections.singletonList(mockData), PageRequest.of(0, 10), 1);
    }

    @Test
    public void testGetChemicalsForMassBetween() throws Exception {
        Mockito.when(searchMassAndFormulaRepository.getMassValues(anyDouble(), anyDouble(), anyDouble(), any(Pageable.class)))
                .thenReturn(mockPage);

        mockMvc.perform(get("/search/mass/50.0/100.0")
                        .param("mass", "75.0")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].dtxsid").value("DTXSID7020182"));
    }

    @Test
    public void testGetChemicalsForMassBetweenInvalidParams() throws Exception {
        mockMvc.perform(get("/search/mass/50.0/epa")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetCountForMassBetween() throws Exception {
        Mockito.when(searchMassAndFormulaRepository.countByMonoisotopicMassBetweenAndDtxsidIsNot(anyDouble(), anyDouble(), anyString()))
                .thenReturn(5L);

        mockMvc.perform(get("/search/mass/count/50.0/100.0")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    public void testGetCountForMassBetweenInvalidParams() throws Exception {
        mockMvc.perform(get("/search/mass/count/50.0/abc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetChemicalsForMassBetweenEmptyResult() throws Exception {
        Mockito.when(searchMassAndFormulaRepository.getMassValues(anyDouble(), anyDouble(), anyDouble(), any(Pageable.class)))
                .thenReturn(Page.empty());

        mockMvc.perform(get("/search/mass/50.0/100.0")
                        .param("mass", "75.0")
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isEmpty());
    }
}
