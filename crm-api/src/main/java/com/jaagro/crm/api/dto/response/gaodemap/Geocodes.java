package com.jaagro.crm.api.dto.response.gaodemap;

import lombok.Data;

import java.io.Serializable;


/**
 * 高德地图返回地址的详细信息
 *
 * @author Gavin
 */
@Data
public class Geocodes implements Serializable {

    /**
     * 结构化地址信息
     */
    private String formatted_address;
    /**
     * 国家
     */
    private String country;
    /**
     * 所在省
     */
    private String province;
    /**
     * 城市
     */
    private String city;

    /**
     * 城市编码
     */
    private String citycode;
    /**
     * 地址所在的区
     */
    private String district;
    /**
     * 地址所在的乡镇
     */
    private String township;

    /**
     * 街道
     */
    private String street;

    /**
     * 门牌
     * 区域编码
     */
    private String adcode;
    /**
     * 坐标点
     */
    private String location;
    /**
     * 匹配级别
     */
    private String level;

}