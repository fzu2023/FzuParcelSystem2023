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
package me.zhengjie.fzu_notices_info.service.dto;

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
public class FzuNoticesInfoDto implements Serializable {

    /** id */
    private Integer id;

    /** 发布人 */
    private String author;

    /** 所属部门 */
    private String dept;

    /** 内容 */
    private String content;

    /** 更新时间 */
    private Timestamp updateTime;
}