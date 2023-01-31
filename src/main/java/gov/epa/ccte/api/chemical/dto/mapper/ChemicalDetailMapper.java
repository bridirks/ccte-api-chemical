package gov.epa.ccte.api.chemical.dto.mapper;

import gov.epa.ccte.api.chemical.domain.ChemicalDetail;
import gov.epa.ccte.api.chemical.dto.ChemicalDetailDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ChemicalDetailMapper {
    ChemicalDetail toEntity(ChemicalDetailDto chemicalDetailDto);

    ChemicalDetailDto toDto(ChemicalDetail chemicalDetail);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChemicalDetail partialUpdate(ChemicalDetailDto chemicalDetailDto, @MappingTarget ChemicalDetail chemicalDetail);
}