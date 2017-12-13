package com.eling.elcms.membermemorial.service;

import com.eling.elcms.core.service.IGenericManager;
import com.eling.elcms.membermemorial.model.CareMemorial;

/**
 * Created by Liuyp on 2017/9/11.
 */
public interface ICareMemorialService extends IGenericManager<CareMemorial,Long> {


    void cancel(CareMemorial careMemorial);

}
