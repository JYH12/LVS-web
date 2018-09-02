package com.unnet.triangle.lvs.master.service.workgroup;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.unnet.triangle.lvs.master.entity.GidAUUID;
import com.unnet.triangle.lvs.master.model.Workgroup;

public interface WorkgroupService {
	 /**
     * 添加工作組
     * 
     * @param workgroup
     * @param request
     * @return
     */
    GidAUUID insert(Workgroup workgroup, HttpServletRequest request);

    /**
     * 刪除工作組
     * @param workgroupId
     * @return
     */
    Boolean delete(Long workgroupId);

    /**
     * 查詢工作組
     * @param workgroup
     * @param request
     * @return
     */
    List<Workgroup> select(HttpServletRequest request);

    /**
     * 更新工作组
     * 
     * @param workgroup
     * @return
     */
    Boolean update(Workgroup workgroup);

    /**
     * 根据主键找工作组
     * 
     * @param workgroupId
     * @return
     */
    Workgroup select(Long workgroupId);
}

