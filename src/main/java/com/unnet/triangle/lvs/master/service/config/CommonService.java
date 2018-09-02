package com.unnet.triangle.lvs.master.service.config;

import com.unnet.triangle.lvs.master.entity.BooleanMessage;


public interface CommonService {

  BooleanMessage async2sync(Long taskId);

}
