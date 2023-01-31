package gov.epa.ccte.api.chemical.dto.mapper;

import gov.epa.ccte.api.chemical.domain.Fate;
import gov.epa.ccte.api.chemical.dto.FateDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FateMapper {
    Fate toEntity(FateDto fateDto);

    FateDto toDto(Fate fate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Fate partialUpdate(FateDto fateDto, @MappingTarget Fate fate);
}