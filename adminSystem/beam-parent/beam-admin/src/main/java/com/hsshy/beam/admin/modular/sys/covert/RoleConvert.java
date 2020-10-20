package com.hsshy.beam.admin.modular.sys.covert;
import com.hsshy.beam.admin.modular.sys.dto.RoleExportDto;
import com.hsshy.beam.admin.modular.sys.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import java.util.List;
@Mapper(uses = RoleConvertStrategy.class,unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);

    @Mappings({})
    List<RoleExportDto> toDto(List<Role> role);


    @Mappings({
          @Mapping(source = "flag",target = "flag")
    })
    RoleExportDto toDto(Role role);




}
