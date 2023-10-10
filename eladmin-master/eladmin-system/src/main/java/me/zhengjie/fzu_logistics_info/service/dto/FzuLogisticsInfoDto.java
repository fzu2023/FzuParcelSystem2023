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
package me.zhengjie.fzu_logistics_info.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author chenshan
* @date 2023-10-09
**/
@Data
public class FzuLogisticsInfoDto implements Serializable {

    private Integer id;

    /** 订单id */
    private Integer itemId;

    /** 发出城市 */
    private String senderCity;

    /** 当前所在城市 */
    private String currentCity;

    /** 接收城市 */
    private String recipientCity;

    /** 司机名字 */
    private String driverName;

    /** 发车开始时间 */
    private Timestamp startDate;

    /** 卸货结束时间 */
    private Timestamp endDate;
}