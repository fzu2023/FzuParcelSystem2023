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
package me.zhengjie.fzu_user_address.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.fzu_user_address.domain.FzuUserAddress;
import me.zhengjie.fzu_user_address.service.FzuUserAddressService;
import me.zhengjie.fzu_user_address.service.dto.FzuUserAddressQueryCriteria;
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
import me.zhengjie.fzu_user_address.service.dto.FzuUserAddressDto;

/**
* @website https://eladmin.vip
* @author chenshan
* @date 2023-10-31
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "用户地址管理")
@RequestMapping("/api/fzuUserAddress")
public class FzuUserAddressController {

    private final FzuUserAddressService fzuUserAddressService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('fzuUserAddress:list')")
    public void exportFzuUserAddress(HttpServletResponse response, FzuUserAddressQueryCriteria criteria) throws IOException {
        fzuUserAddressService.download(fzuUserAddressService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询用户地址")
    @ApiOperation("查询用户地址")
    @PreAuthorize("@el.check('fzuUserAddress:list')")
    public ResponseEntity<PageResult<FzuUserAddressDto>> queryFzuUserAddress(FzuUserAddressQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(fzuUserAddressService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增用户地址")
    @ApiOperation("新增用户地址")
    @PreAuthorize("@el.check('fzuUserAddress:add')")
    public ResponseEntity<Object> createFzuUserAddress(@Validated @RequestBody FzuUserAddress resources){

        fzuUserAddressService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改用户地址")
    @ApiOperation("修改用户地址")
    @PreAuthorize("@el.check('fzuUserAddress:edit')")
    public ResponseEntity<Object> updateFzuUserAddress(@Validated @RequestBody FzuUserAddress resources){
        fzuUserAddressService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除用户地址")
    @ApiOperation("删除用户地址")
    @PreAuthorize("@el.check('fzuUserAddress:del')")
    public ResponseEntity<Object> deleteFzuUserAddress(@RequestBody Integer[] ids) {
        fzuUserAddressService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
