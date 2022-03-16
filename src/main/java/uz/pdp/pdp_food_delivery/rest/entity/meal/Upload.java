package uz.pdp.pdp_food_delivery.rest.entity.meal;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.Resource;

import java.util.UUID;

@Data
@Builder
public class Upload {
    private UUID id;
    private String originalName;
    private String generatedName;
    private String contentType;
    private long size;
    private String path;
    private Resource resource;

}