package com.zhengcheng.common.dto;

import lombok.Data;
import org.nutz.lang.Strings;

import java.io.Serializable;

/**
 * PageHelperDto
 *
 * @author :    quansheng.zhang
 * @Package :     com.zhengcheng.common.dto
 * @Description :
 * @date :    2019/2/2 13:27
 */
@Data
public class PageHelperDto implements Serializable {

    private Integer pageNo = 0;
    private Integer pageSize = 10;
    private String sortField;
    private String sortType = "ASC";

    /**
     * 防止SQL注入
     *
     * @return
     */
    public String getSortField() {
        this.sortField = Strings.trim(Strings.sBlank(this.sortField));
        if (this.sortField.contains("(") ||
                this.sortField.contains(")")
                || this.sortField.contains("=") || this.sortField.contains("'")
                || this.sortField.contains(" ")) {
            return "";
        }
        return this.sortField;
    }

    /**
     * 防止SQL注入
     *
     * @return
     */
    public String getSortType() {
        this.sortType = Strings.trim(Strings.sBlank(this.sortField));
        if (!Strings.equalsIgnoreCase("asc", this.sortType) &&
                !Strings.equalsIgnoreCase("desc", this.sortType)) {
            return "ASC";
        }
        return this.sortType;
    }

}
