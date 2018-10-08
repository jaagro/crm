package com.jaagro.crm.api.dto.response.selectValue;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回的客户收发货地址 帮助下拉框 赋值
 *
 * @author liqiangping
 */
@Data
@Accessors(chain = true)
public class ReturnSelectSiteDto implements Serializable {
    /**
     * 客户地址ID
     */
    private Integer id;

    /**
     * 货物类型
     */
    private Integer productType;

    /**
     * 装货地名称
     */
    private String siteName;

    /**
     * 地址类型
     * 1-装货点，2-卸货点
     */
    private Integer siteType;

    /**
     * 联系人姓名
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String county;

    /**
     * 详细地址
     */
    private String address;

}
