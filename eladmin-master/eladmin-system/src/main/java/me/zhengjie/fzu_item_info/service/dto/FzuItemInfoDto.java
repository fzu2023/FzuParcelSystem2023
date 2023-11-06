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
package me.zhengjie.fzu_item_info.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author ChenShan
* @date 2023-11-06
**/
@Data
public class FzuItemInfoDto implements Serializable {

    /** id */
    private Integer id;

    /** 寄件人 */
    private String sender;

    /** 收件人 */
    private String recipient;

    /** 寄出地址 */
    private String senderAddress;

    /** 发往地址 */
    private String recipientAddress;

    /** 寄件人电话 */
    private String senderPhone;

    /** 收件人电话 */
    private String recipientPhone;

    /** 订单状态 */
    private String itemStatus;

    /** 订单id */
    private Integer itemId;

    /** 订单生成时间 */
    private Timestamp itemStartTime;

    /** 订单完成时间 */
    private Timestamp itemEndTime;

    /** 寄件网点 */
    private String senderOutlet;

    /** 收件网点 */
    private String recipientOutlet;

    /** 派件员用户 */
    private String deliveryUsername;
}