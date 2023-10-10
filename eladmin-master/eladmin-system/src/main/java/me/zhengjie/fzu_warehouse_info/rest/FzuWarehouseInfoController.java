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
package me.zhengjie.fzu_warehouse_info.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.fzu_warehouse_info.domain.FzuWarehouseInfo;
import me.zhengjie.fzu_warehouse_info.service.FzuWarehouseInfoService;
import me.zhengjie.fzu_warehouse_info.service.dto.FzuWarehouseInfoQueryCriteria;
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
import me.zhengjie.fzu_warehouse_info.service.dto.FzuWarehouseInfoDto;

/**
* @website https://eladmin.vip
* @author chenshan
* @date 2023-10-09
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "仓库管理管理")
@RequestMapping("/api/fzuWarehouseInfo")
public class FzuWarehouseInfoController {

    private final FzuWarehouseInfoService fzuWarehouseInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('fzuWarehouseInfo:list')")
    public void exportFzuWarehouseInfo(HttpServletResponse response, FzuWarehouseInfoQueryCriteria criteria) throws IOException {
        fzuWarehouseInfoService.download(fzuWarehouseInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询仓库管理")
    @ApiOperation("查询仓库管理")
    @PreAuthorize("@el.check('fzuWarehouseInfo:list')")
    public ResponseEntity<PageResult<FzuWarehouseInfoDto>> queryFzuWarehouseInfo(FzuWarehouseInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(fzuWarehouseInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增仓库管理")
    @ApiOperation("新增仓库管理")
    @PreAuthorize("@el.check('fzuWarehouseInfo:add')")
    public ResponseEntity<Object> createFzuWarehouseInfo(@Validated @RequestBody FzuWarehouseInfo resources){
        fzuWarehouseInfoService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改仓库管理")
    @ApiOperation("修改仓库管理")
    @PreAuthorize("@el.check('fzuWarehouseInfo:edit')")
    public ResponseEntity<Object> updateFzuWarehouseInfo(@Validated @RequestBody FzuWarehouseInfo resources){
        fzuWarehouseInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除仓库管理")
    @ApiOperation("删除仓库管理")
    @PreAuthorize("@el.check('fzuWarehouseInfo:del')")
    public ResponseEntity<Object> deleteFzuWarehouseInfo(@RequestBody Integer[] ids) {
        fzuWarehouseInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}