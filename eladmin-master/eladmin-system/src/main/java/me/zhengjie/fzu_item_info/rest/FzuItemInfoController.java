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
package me.zhengjie.fzu_item_info.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.fzu_item_info.domain.FzuItemInfo;
import me.zhengjie.fzu_item_info.service.FzuItemInfoService;
import me.zhengjie.fzu_item_info.service.dto.FzuItemInfoQueryCriteria;
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
import me.zhengjie.fzu_item_info.service.dto.FzuItemInfoDto;

/**
* @website https://eladmin.vip
* @author ChenShan
* @date 2023-10-09
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "订单列表管理")
@RequestMapping("/api/fzuItemInfo")
public class FzuItemInfoController {

    private final FzuItemInfoService fzuItemInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('fzuItemInfo:list')")
    public void exportFzuItemInfo(HttpServletResponse response, FzuItemInfoQueryCriteria criteria) throws IOException {
        fzuItemInfoService.download(fzuItemInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询订单列表")
    @ApiOperation("查询订单列表")
    @PreAuthorize("@el.check('fzuItemInfo:list')")
    public ResponseEntity<PageResult<FzuItemInfoDto>> queryFzuItemInfo(FzuItemInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(fzuItemInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增订单列表")
    @ApiOperation("新增订单列表")
    @PreAuthorize("@el.check('fzuItemInfo:add')")
    public ResponseEntity<Object> createFzuItemInfo(@Validated @RequestBody FzuItemInfo resources){
        fzuItemInfoService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改订单列表")
    @ApiOperation("修改订单列表")
    @PreAuthorize("@el.check('fzuItemInfo:edit')")
    public ResponseEntity<Object> updateFzuItemInfo(@Validated @RequestBody FzuItemInfo resources){
        fzuItemInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除订单列表")
    @ApiOperation("删除订单列表")
    @PreAuthorize("@el.check('fzuItemInfo:del')")
    public ResponseEntity<Object> deleteFzuItemInfo(@RequestBody Integer[] ids) {
        fzuItemInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
