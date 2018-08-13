package com.jaagro.crm.api.dto.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @author tony
 */
@Data
public class PermissionDto implements Serializable {

    private Integer permissionID;
    private String permissionInfo;
}
