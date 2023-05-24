package gov.epa.ccte.api.chemical.config.converter;

import gov.epa.ccte.api.chemical.domain.PropertyType;
import gov.epa.ccte.api.chemical.web.rest.errors.TypeValueNotFoundProblem;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToPropertyTypeConverter implements Converter<String, PropertyType> {
    @Override
    public PropertyType convert(String source) {
//            return PropertyType.valueOf(source.toLowerCase());

        try{
            return PropertyType.valueOf(source.toLowerCase());

        } catch (IllegalArgumentException e){
            throw new TypeValueNotFoundProblem("Property type", source);
//            return null;
        }
    }
}
