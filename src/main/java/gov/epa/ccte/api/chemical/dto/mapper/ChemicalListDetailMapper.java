package gov.epa.ccte.api.chemical.dto.mapper;

import gov.epa.ccte.api.chemical.domain.ChemicalListDetail;
import gov.epa.ccte.api.chemical.dto.ChemicalListDetailDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ChemicalListDetailMapper {
    ChemicalListDetail toEntity(ChemicalListDetailDto chemicalListDetailDto);

    ChemicalListDetailDto toDto(ChemicalListDetail chemicalListDetail);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChemicalListDetail partialUpdate(ChemicalListDetailDto chemicalListDetailDto, @MappingTarget ChemicalListDetail chemicalListDetail);
}