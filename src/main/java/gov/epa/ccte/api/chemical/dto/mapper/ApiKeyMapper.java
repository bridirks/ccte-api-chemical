package gov.epa.ccte.api.chemical.dto.mapper;

import gov.epa.ccte.api.chemical.domain.ApiKey;
import gov.epa.ccte.api.chemical.dto.ApiKeyDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ApiKeyMapper {
    ApiKey toEntity(ApiKeyDto apiKeyDto);

    ApiKeyDto toDto(ApiKey apiKey);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ApiKey partialUpdate(ApiKeyDto apiKeyDto, @MappingTarget ApiKey apiKey);
}