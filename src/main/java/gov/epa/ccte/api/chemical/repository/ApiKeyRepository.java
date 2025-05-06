package gov.epa.ccte.api.chemical.repository;

import gov.epa.ccte.api.chemical.domain.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface ApiKeyRepository extends JpaRepository<ApiKey, UUID> {
}