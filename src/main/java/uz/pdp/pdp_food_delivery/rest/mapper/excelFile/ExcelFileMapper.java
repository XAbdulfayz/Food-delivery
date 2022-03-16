package uz.pdp.pdp_food_delivery.rest.mapper.excelFile;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import uz.pdp.pdp_food_delivery.rest.dto.BaseDto;
import uz.pdp.pdp_food_delivery.rest.dto.GenericDto;
import uz.pdp.pdp_food_delivery.rest.dto.excelFile.ExcelFileDto;
import uz.pdp.pdp_food_delivery.rest.entity.excelFile.ExcelFile;
import uz.pdp.pdp_food_delivery.rest.mapper.BaseMapper;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ExcelFileMapper extends BaseMapper<
        ExcelFile,
        ExcelFileDto,
        BaseDto,
        GenericDto> {

    ExcelFileDto toDto(ExcelFile excelFile);

    List<ExcelFileDto> toDto(List<ExcelFile> excelFiles);

    ExcelFile toEntity(ExcelFileDto excelFileDto);

    List<ExcelFile> toEntity(List<ExcelFileDto> excelFileDots);


}
