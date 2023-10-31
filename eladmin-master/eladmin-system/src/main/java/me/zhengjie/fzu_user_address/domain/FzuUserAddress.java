/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.fzu_user_address.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author chenshan
* @date 2023-10-31
**/
@Entity
@Data
@Table(name="fzu_user_address")
public class FzuUserAddress implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "`user_id`")
    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @Column(name = "`user_address`")
    @ApiModelProperty(value = "用户地址")
    private String userAddress;

    @Column(name = "`user_phone`")
    @ApiModelProperty(value = "用户电话")
    private String userPhone;

    @Column(name = "`username`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "用户账户")
    private String username;

    public void copy(FzuUserAddress source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
