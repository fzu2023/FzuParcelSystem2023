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
package me.zhengjie.fzu_outlet_info.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.fzu_outlet_info.domain.FzuOutletInfo;
import me.zhengjie.fzu_outlet_info.service.FzuOutletInfoService;
import me.zhengjie.fzu_outlet_info.service.dto.FzuOutletInfoQueryCriteria;
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
import me.zhengjie.fzu_outlet_info.service.dto.FzuOutletInfoDto;

/**
* @website https://eladmin.vip
* @author ChenShan
* @date 2023-10-09
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "网点信息表管理")
@RequestMapping("/api/fzuOutletInfo")
public class FzuOutletInfoController {

    private final FzuOutletInfoService fzuOutletInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('fzuOutletInfo:list')")
    public void exportFzuOutletInfo(HttpServletResponse response, FzuOutletInfoQueryCriteria criteria) throws IOException {
        fzuOutletInfoService.download(fzuOutletInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询网点信息表")
    @ApiOperation("查询网点信息表")
    @PreAuthorize("@el.check('fzuOutletInfo:list')")
    public ResponseEntity<PageResult<FzuOutletInfoDto>> queryFzuOutletInfo(FzuOutletInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(fzuOutletInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增网点信息表")
    @ApiOperation("新增网点信息表")
    @PreAuthorize("@el.check('fzuOutletInfo:add')")
    public ResponseEntity<Object> createFzuOutletInfo(@Validated @RequestBody FzuOutletInfo resources){
        fzuOutletInfoService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改网点信息表")
    @ApiOperation("修改网点信息表")
    @PreAuthorize("@el.check('fzuOutletInfo:edit')")
    public ResponseEntity<Object> updateFzuOutletInfo(@Validated @RequestBody FzuOutletInfo resources){
        fzuOutletInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除网点信息表")
    @ApiOperation("删除网点信息表")
    @PreAuthorize("@el.check('fzuOutletInfo:del')")
    public ResponseEntity<Object> deleteFzuOutletInfo(@RequestBody Integer[] ids) {
        fzuOutletInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}