package uz.pdp.pdp_food_delivery.rest.repository.exccelFile;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.pdp_food_delivery.rest.entity.excelFile.ExcelFile;
import uz.pdp.pdp_food_delivery.rest.repository.BaseRepository;

public interface ExcelFileRepository extends JpaRepository<ExcelFile, Long>, BaseRepository {



}
