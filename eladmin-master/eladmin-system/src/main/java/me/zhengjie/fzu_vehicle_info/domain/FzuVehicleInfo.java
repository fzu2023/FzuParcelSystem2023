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
package me.zhengjie.fzu_vehicle_info.domain;

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
* @date 2023-10-09
**/
@Entity
@Data
@Table(name="fzu_vehicle_info")
public class FzuVehicleInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "`outlet_id`")
    @ApiModelProperty(value = "所在网点id")
    private Integer outletId;

    @Column(name = "`is_free`")
    @ApiModelProperty(value = "是否空闲")
    private String isFree;

    @Column(name = "`driver_phone`")
    @ApiModelProperty(value = "驾驶员电话")
    private String driverPhone;

    @Column(name = "`license`")
    @ApiModelProperty(value = "车牌号")
    private String license;

    public void copy(FzuVehicleInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
