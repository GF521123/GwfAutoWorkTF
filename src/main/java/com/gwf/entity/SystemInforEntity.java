package com.gwf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.stereotype.Repository;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/16 14:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class SystemInforEntity {
    private Boolean status;//状态
    private String cookie;//cookie值
    private String CoreTime;//核心监控时间版本4暂停使用
    private String kefuTime;//客服监控时间间隔
    private String gwfTime;//商品店铺监控时间间隔
    private int syncNum;//参数初始化完成状态
}
