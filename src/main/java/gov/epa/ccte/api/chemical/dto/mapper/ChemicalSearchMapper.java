package gov.epa.ccte.api.chemical.dto.mapper;

import gov.epa.ccte.api.chemical.domain.ChemicalSearch;
import gov.epa.ccte.api.chemical.dto.ChemicalSearchDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ChemicalSearchMapper {
    ChemicalSearch toEntity(ChemicalSearchDto chemicalSearchDto);

    ChemicalSearchDto toDto(ChemicalSearch chemicalSearch);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChemicalSearch partialUpdate(ChemicalSearchDto chemicalSearchDto, @MappingTarget ChemicalSearch chemicalSearch);
}