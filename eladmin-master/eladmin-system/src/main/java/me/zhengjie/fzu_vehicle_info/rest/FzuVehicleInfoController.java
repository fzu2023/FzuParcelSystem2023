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
package me.zhengjie.fzu_vehicle_info.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.fzu_vehicle_info.domain.FzuVehicleInfo;
import me.zhengjie.fzu_vehicle_info.service.FzuVehicleInfoService;
import me.zhengjie.fzu_vehicle_info.service.dto.FzuVehicleInfoQueryCriteria;
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
import me.zhengjie.fzu_vehicle_info.service.dto.FzuVehicleInfoDto;

/**
* @website https://eladmin.vip
* @author chenshan
* @date 2023-10-09
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "车辆管理管理")
@RequestMapping("/api/fzuVehicleInfo")
public class FzuVehicleInfoController {

    private final FzuVehicleInfoService fzuVehicleInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('fzuVehicleInfo:list')")
    public void exportFzuVehicleInfo(HttpServletResponse response, FzuVehicleInfoQueryCriteria criteria) throws IOException {
        fzuVehicleInfoService.download(fzuVehicleInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询车辆管理")
    @ApiOperation("查询车辆管理")
    @PreAuthorize("@el.check('fzuVehicleInfo:list')")
    public ResponseEntity<PageResult<FzuVehicleInfoDto>> queryFzuVehicleInfo(FzuVehicleInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(fzuVehicleInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增车辆管理")
    @ApiOperation("新增车辆管理")
    @PreAuthorize("@el.check('fzuVehicleInfo:add')")
    public ResponseEntity<Object> createFzuVehicleInfo(@Validated @RequestBody FzuVehicleInfo resources){
        fzuVehicleInfoService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改车辆管理")
    @ApiOperation("修改车辆管理")
    @PreAuthorize("@el.check('fzuVehicleInfo:edit')")
    public ResponseEntity<Object> updateFzuVehicleInfo(@Validated @RequestBody FzuVehicleInfo resources){
        fzuVehicleInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除车辆管理")
    @ApiOperation("删除车辆管理")
    @PreAuthorize("@el.check('fzuVehicleInfo:del')")
    public ResponseEntity<Object> deleteFzuVehicleInfo(@RequestBody Integer[] ids) {
        fzuVehicleInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}