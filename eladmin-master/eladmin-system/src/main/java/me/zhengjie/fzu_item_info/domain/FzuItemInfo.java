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
package me.zhengjie.fzu_item_info.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author ChenShan
* @date 2023-10-09
**/
@Entity
@Data
@Table(name="fzu_item_info")
public class FzuItemInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`")
    @ApiModelProperty(value = "id")
    private Integer id;

    @Column(name = "`sender`")
    @ApiModelProperty(value = "寄件人")
    private String sender;

    @Column(name = "`recipient`")
    @ApiModelProperty(value = "收件人")
    private String recipient;

    @Column(name = "`sender_address`")
    @ApiModelProperty(value = "寄出地址")
    private String senderAddress;

    @Column(name = "`recipient_address`")
    @ApiModelProperty(value = "发往地址")
    private String recipientAddress;

    @Column(name = "`sender_phone`",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "寄件人电话")
    private String senderPhone;

    @Column(name = "`recipient_phone`")
    @ApiModelProperty(value = "收件人电话")
    private String recipientPhone;

    @Column(name = "`item_status`")
    @ApiModelProperty(value = "订单状态")
    private String itemStatus;

    @Column(name = "`delivery_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "派件员id")
    private Integer deliveryId;

    @Column(name = "`sender_outlet_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "寄件网点id")
    private Integer senderOutletId;

    @Column(name = "`recipient_outlet_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "收件网点id")
    private Integer recipientOutletId;

    @Column(name = "`item_id`",nullable = false)
    @NotNull
    @ApiModelProperty(value = "订单id")
    private Integer itemId;

    @Column(name = "`item_start_time`")
    @CreationTimestamp
    @ApiModelProperty(value = "订单生成时间")
    private Timestamp itemStartTime;

    @Column(name = "`item_end_time`")
    @UpdateTimestamp
    @ApiModelProperty(value = "订单完成时间")
    private Timestamp itemEndTime;

    public void copy(FzuItemInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
