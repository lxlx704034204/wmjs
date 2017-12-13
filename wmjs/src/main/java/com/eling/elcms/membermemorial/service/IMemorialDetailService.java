package com.eling.elcms.membermemorial.service;

import com.eling.elcms.core.service.IGenericManager;
import com.eling.elcms.membermemorial.model.MemorialDetail;
import com.eling.elcms.system.model.CommonUser;

public interface IMemorialDetailService extends IGenericManager<MemorialDetail, Long> {
    MemorialDetail saveDetail(MemorialDetail cond);
}
