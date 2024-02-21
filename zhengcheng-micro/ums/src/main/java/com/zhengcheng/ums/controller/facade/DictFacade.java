package com.zhengcheng.ums.controller.facade;

import com.zhengcheng.common.utils.PageInfo;
import com.zhengcheng.ums.dto.DictDataDTO;
import com.zhengcheng.ums.dto.DictTypeDTO;
import com.zhengcheng.ums.dto.command.*;

import java.util.List;


/**
 * DictTypeFacade
 *
 * @author quansheng1.zhang
 * @since 2022/4/28 20:31
 */
public interface DictFacade {

    /**
     * 分页查询字典类型
     */
    PageInfo<DictTypeDTO> typePage(DictTypePageCommand pageCommand);

    /**
     * 分页查询字典
     */
    PageInfo<DictDataDTO> dataPage(DictDataPageCommand dictDataPageCommand);

    /**
     * 查询所有字典类型
     */
    List<DictTypeDTO> typeList();

    /**
     * 新增字典数据
     * @param dictDataCommand DictDataCommand
     * @return 是/否
     */
    boolean addData(DictDataCommand dictDataCommand);

    /**
     * 更新字典数据
     * @param dictDataCommand DictDataCommand
     * @return 是/否
     */
    boolean updateData(DictDataCommand dictDataCommand);

    /**
     * 删除字典数据
     * @param id ID
     * @return 是/否
     */
    boolean removeData(Long id);

    /**
     * 批量删除字典数据
     * @param ids ID
     * @return 是/否
     */
    boolean batchRemoveData(List<Long> ids);

    /**
     * 启用/禁用字典数据
     * @param enableCommand EnableCommand
     * @return 是/否
     */
    boolean enableData(EnableCommand enableCommand);

    /**
     * 根据ID查询数据字典
     * @param id ID
     * @return DictDataDTO
     */
    DictDataDTO findDataById(Long id);

    /**
     * 保存字典类型
     * @param dictTypeCommand DictTypeCommand
     * @return 是/否
     */
    boolean saveType(DictTypeCommand dictTypeCommand);

    /**
     * 删除字典类型
     * @param id ID
     * @return 是/否
     */
    boolean removeType(Long id);

    /**
     * 根据ID查询数据字典类型
     * @param id ID
     * @return DictDataDTO
     */
    DictTypeDTO findTypeById(Long id);

    /**
     * 更新字典数据类型
     * @param dictTypeCommand DictTypeCommand
     * @return 是/否
     */
    boolean updateType(DictTypeCommand dictTypeCommand);

    /**
     * 启用/禁用字典数据类型
     * @param enableCommand EnableCommand
     * @return 是/否
     */
    boolean enableType(EnableCommand enableCommand);

    /**
     * 批量删除字典数据类型
     * @param ids ID
     * @return 是/否
     */
    boolean batchRemoveType(List<Long> ids);
}
