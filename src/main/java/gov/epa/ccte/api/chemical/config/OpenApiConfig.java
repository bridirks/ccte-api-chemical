package gov.epa.ccte.api.chemical.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


//@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "CCTE Chemical APIs",
                description = "These are part of CCTE Public APIs and provide a variety of chemical information through a set of API endpoints. These include the ability to search for chemicals, retrieve chemical structure files in various formats, access both experimental and predictive chemical properties, obtain fate data, and access other chemical-related data. These APIs allow users to easily access and utilize a wide range of chemical information.",
                contact = @Contact(
                        name = "Asif Rashid",
                        url = "",
                        email = "rashid.asif@epa.gov"
                ),
                license = @License(
                        name = "No Licence",
                        url = "")),
        servers = @Server(url = "https://api-ccte.epa.gov")
)
@SecurityScheme(
        type = SecuritySchemeType.APIKEY,
        name = "api_key",
        in = SecuritySchemeIn.HEADER,
        description = "Each API call should have api_key, Contact author for getting the new api_key. ",
        paramName = "x-api-key"
)
public class OpenApiConfig {
}
