package gov.epa.ccte.api.chemical.datatest;

import gov.epa.ccte.api.chemical.projection.chemicallist.ChemicalListDetailAll;
import gov.epa.ccte.api.chemical.repository.ChemicalListDetailRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class ChemicalListDetailsRepositoryTest {

    @Autowired
    private DataSource dataSource;
    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private TestEntityManager entityManager;
    @Autowired private ChemicalListDetailRepository repository;

    @Test
    void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(repository).isNotNull();
    }
    @Test
    void testDataLoaded(){
        assertThat(repository.findAll().size()).isEqualTo(10);
    }

    @Test
    void testFindByDtxsidInOrderByDtxsidAsc(){
        assertThat(repository.findByDtxsid("DTXSID00880086").size()).isEqualTo(1);
    }

    @Test
    void testFindByListName(){
        // checking the number of chemicals in the given list name
        assertThat(repository.findByListNameIgnoreCaseOrderByDtxsid("MICROCYSTIN", ChemicalListDetailAll.class).size()).isEqualTo(7);
    }

    @Test
    void testFindByDtxsid(){
        // checking the number of lists in which the given chemical is present
        assertThat(repository.findByDtxsid("DTXSID00880086").size()).isEqualTo(1);
    }

}
