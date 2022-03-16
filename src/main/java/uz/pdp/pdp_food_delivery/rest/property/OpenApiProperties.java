package uz.pdp.pdp_food_delivery.rest.property;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "api.info")
public class OpenApiProperties {
    private String title;
    private String description;
    private String version;
    private String termOfService;
    private String contactName;
    private String contactEmail;
    private String contactUrl;
    private String licenseName;
    private String licenseUrl;
}
