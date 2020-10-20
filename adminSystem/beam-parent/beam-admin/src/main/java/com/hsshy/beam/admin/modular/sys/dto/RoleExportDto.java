package com.hsshy.beam.admin.modular.sys.dto;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import java.io.Serializable;
@Data
public class RoleExportDto  implements Serializable {

    // 角色名称
    @ExcelProperty(value = "角色名称",index = 0)
    private String roleName;
    // 备注
    @ExcelProperty(value = "备注",index = 1)
    private String remark;

    @ExcelProperty(value = "标志",index = 2)
    private String flag;

    @ExcelProperty(value = "创建时间",index = 3)
    private String createTime;
}
