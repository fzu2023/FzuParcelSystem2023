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
package me.zhengjie.fzu_notices_info.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.fzu_notices_info.domain.FzuNoticesInfo;
import me.zhengjie.fzu_notices_info.service.FzuNoticesInfoService;
import me.zhengjie.fzu_notices_info.service.dto.FzuNoticesInfoQueryCriteria;
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
import me.zhengjie.fzu_notices_info.service.dto.FzuNoticesInfoDto;

/**
* @website https://eladmin.vip
* @author ChenShan
* @date 2023-11-06
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "通知列表管理")
@RequestMapping("/api/fzuNoticesInfo")
public class FzuNoticesInfoController {

    private final FzuNoticesInfoService fzuNoticesInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('fzuNoticesInfo:list')")
    public void exportFzuNoticesInfo(HttpServletResponse response, FzuNoticesInfoQueryCriteria criteria) throws IOException {
        fzuNoticesInfoService.download(fzuNoticesInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询通知列表")
    @ApiOperation("查询通知列表")
    @PreAuthorize("@el.check('fzuNoticesInfo:list')")
    public ResponseEntity<PageResult<FzuNoticesInfoDto>> queryFzuNoticesInfo(FzuNoticesInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(fzuNoticesInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增通知列表")
    @ApiOperation("新增通知列表")
    @PreAuthorize("@el.check('fzuNoticesInfo:add')")
    public ResponseEntity<Object> createFzuNoticesInfo(@Validated @RequestBody FzuNoticesInfo resources){
        fzuNoticesInfoService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改通知列表")
    @ApiOperation("修改通知列表")
    @PreAuthorize("@el.check('fzuNoticesInfo:edit')")
    public ResponseEntity<Object> updateFzuNoticesInfo(@Validated @RequestBody FzuNoticesInfo resources){
        fzuNoticesInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除通知列表")
    @ApiOperation("删除通知列表")
    @PreAuthorize("@el.check('fzuNoticesInfo:del')")
    public ResponseEntity<Object> deleteFzuNoticesInfo(@RequestBody Integer[] ids) {
        fzuNoticesInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}