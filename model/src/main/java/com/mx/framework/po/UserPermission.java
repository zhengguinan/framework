package com.mx.framework.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author : ShangGuanMingPeng
 * Description : 用户权限表
 * Date :Create in 2019/2/15 22:42
 * Modified By :
 */
@Table(name = "permission")
@Data
public class UserPermission {

    @Id
    private Integer pid;

    private String name;

    private String url;


}
