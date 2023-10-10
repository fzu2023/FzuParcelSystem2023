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
package me.zhengjie.fzu_logistics_info.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.fzu_logistics_info.domain.FzuLogisticsInfo;
import me.zhengjie.fzu_logistics_info.service.FzuLogisticsInfoService;
import me.zhengjie.fzu_logistics_info.service.dto.FzuLogisticsInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import me.zhengjie.utils.PageResult;
import me.zhengjie.fzu_logistics_info.service.dto.FzuLogisticsInfoDto;

/**
* @website https://eladmin.vip
* @author chenshan
* @date 2023-10-09
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "物流管理管理")
@RequestMapping("/api/fzuLogisticsInfo")
public class FzuLogisticsInfoController {

    private final FzuLogisticsInfoService fzuLogisticsInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('fzuLogisticsInfo:list')")
    public void exportFzuLogisticsInfo(HttpServletResponse response, FzuLogisticsInfoQueryCriteria criteria) throws IOException {
        fzuLogisticsInfoService.download(fzuLogisticsInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询物流管理")
    @ApiOperation("查询物流管理")
    @PreAuthorize("@el.check('fzuLogisticsInfo:list')")
    public ResponseEntity<PageResult<FzuLogisticsInfoDto>> queryFzuLogisticsInfo(FzuLogisticsInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(fzuLogisticsInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增物流管理")
    @ApiOperation("新增物流管理")
    @PreAuthorize("@el.check('fzuLogisticsInfo:add')")
    public ResponseEntity<Object> createFzuLogisticsInfo(@Validated @RequestBody FzuLogisticsInfo resources){
        fzuLogisticsInfoService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改物流管理")
    @ApiOperation("修改物流管理")
    @PreAuthorize("@el.check('fzuLogisticsInfo:edit')")
    public ResponseEntity<Object> updateFzuLogisticsInfo(@Validated @RequestBody FzuLogisticsInfo resources){
        fzuLogisticsInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除物流管理")
    @ApiOperation("删除物流管理")
    @PreAuthorize("@el.check('fzuLogisticsInfo:del')")
    public ResponseEntity<Object> deleteFzuLogisticsInfo(@RequestBody Integer[] ids) {
        fzuLogisticsInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}