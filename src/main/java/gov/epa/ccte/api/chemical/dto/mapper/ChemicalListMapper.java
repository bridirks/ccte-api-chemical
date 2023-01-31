package gov.epa.ccte.api.chemical.dto.mapper;

import gov.epa.ccte.api.chemical.domain.ChemicalList;
import gov.epa.ccte.api.chemical.dto.ChemicalListDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ChemicalListMapper {
    ChemicalList toEntity(ChemicalListDto chemicalListDto);

    ChemicalListDto toDto(ChemicalList chemicalList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChemicalList partialUpdate(ChemicalListDto chemicalListDto, @MappingTarget ChemicalList chemicalList);
}