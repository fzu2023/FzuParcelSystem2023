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
package me.zhengjie.fzu_outlet_info.domain;

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
* @author ChenShan
* @date 2023-10-09
**/
@Entity
@Data
@Table(name="fzu_outlet_info")
public class FzuOutletInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "`nickname`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "网点名字")
    private String nickname;

    @Column(name = "`admin_phone`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "负责人电话")
    private String adminPhone;

    @Column(name = "`admin_email`")
    @ApiModelProperty(value = "负责人邮箱")
    private String adminEmail;

    @Column(name = "`current_capacity`")
    @ApiModelProperty(value = "现有容量/仓房间数")
    private String currentCapacity;

    @Column(name = "`total_capacity`")
    @ApiModelProperty(value = "总容量/仓库房间数")
    private String totalCapacity;

    @Column(name = "`nickid`")
    @ApiModelProperty(value = "网点id")
    private Integer nickid;

    public void copy(FzuOutletInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
