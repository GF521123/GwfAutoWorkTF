package com.gwf.utils;

import com.gwf.entity.ToEmailEntity;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 *
 */
public interface EmailUtils {

    public String commonEmail(ToEmailEntity toEmailEntity);
    public String htmlEmail(ToEmailEntity toEmailEntity) ;
}
