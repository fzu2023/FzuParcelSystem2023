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
package me.zhengjie.fzu_logistics_info.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author chenshan
* @date 2023-10-09
**/
@Entity
@Data
@Table(name="fzu_logistics_info")
public class FzuLogisticsInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "`item_id`")
    @ApiModelProperty(value = "订单id")
    private Integer itemId;

    @Column(name = "`sender_city`")
    @ApiModelProperty(value = "发出城市")
    private String senderCity;

    @Column(name = "`current_city`")
    @ApiModelProperty(value = "当前所在城市")
    private String currentCity;

    @Column(name = "`recipient_city`")
    @ApiModelProperty(value = "接收城市")
    private String recipientCity;

    @Column(name = "`driver_name`")
    @ApiModelProperty(value = "司机名字")
    private String driverName;

    @Column(name = "`start_date`")
    @ApiModelProperty(value = "发车开始时间")
    private Timestamp startDate;

    @Column(name = "`end_date`")
    @ApiModelProperty(value = "卸货结束时间")
    private Timestamp endDate;

    public void copy(FzuLogisticsInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
