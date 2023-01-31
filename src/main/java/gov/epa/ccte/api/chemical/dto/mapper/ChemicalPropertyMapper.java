package gov.epa.ccte.api.chemical.dto.mapper;

import gov.epa.ccte.api.chemical.domain.ChemicalProperty;
import gov.epa.ccte.api.chemical.dto.ChemicalPropertyDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ChemicalPropertyMapper {
    ChemicalProperty toEntity(ChemicalPropertyDto chemicalPropertyDto);

    ChemicalPropertyDto toDto(ChemicalProperty chemicalProperty);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChemicalProperty partialUpdate(ChemicalPropertyDto chemicalPropertyDto, @MappingTarget ChemicalProperty chemicalProperty);
}