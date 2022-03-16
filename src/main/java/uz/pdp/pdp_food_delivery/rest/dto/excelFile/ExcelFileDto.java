package uz.pdp.pdp_food_delivery.rest.dto.excelFile;


import lombok.Getter;
import lombok.Setter;
import uz.pdp.pdp_food_delivery.rest.dto.GenericDto;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExcelFileDto extends GenericDto {

    private String name;
    private String path;
    private String mimeType;
    private LocalDateTime dateTime;

}
