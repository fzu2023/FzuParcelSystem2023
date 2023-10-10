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
package me.zhengjie.fzu_warehouse_info.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author chenshan
* @date 2023-10-09
**/
@Data
public class FzuWarehouseInfoDto implements Serializable {

    private Integer id;

    /** 网点id */
    private Integer outletId;

    /** 当前容量 */
    private String currentCapacity;

    /** 总容量 */
    private String totalCapacity;

    /** 网点名字 */
    private String outName;
}