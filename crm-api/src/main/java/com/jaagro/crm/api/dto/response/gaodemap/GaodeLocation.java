package com.jaagro.crm.api.dto.response.gaodemap;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 输入地址返回解析结果的类
 *
 * @author Gavin
 */

@Data
public class GaodeLocation implements Serializable {
    /**
     * 结果状态0,表示失败,1:表示成功
     */
    private String status;

    /**
     * 返回结果的数目
     */
    private String count;

    /**
     * 返回状态说明
     */
    private String info;


    private String infocode;
    /**
     * 识别的结果列表
     */
    private List<Geocodes> geocodes;

}
