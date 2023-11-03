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
package me.zhengjie.fzu_user_address.service.impl;

import me.zhengjie.fzu_user_address.domain.FzuUserAddress;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.fzu_user_address.repository.FzuUserAddressRepository;
import me.zhengjie.fzu_user_address.service.FzuUserAddressService;
import me.zhengjie.fzu_user_address.service.dto.FzuUserAddressDto;
import me.zhengjie.fzu_user_address.service.dto.FzuUserAddressQueryCriteria;
import me.zhengjie.fzu_user_address.service.mapstruct.FzuUserAddressMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import me.zhengjie.utils.PageResult;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author chenshan
* @date 2023-10-31
**/
@Service
@RequiredArgsConstructor
public class FzuUserAddressServiceImpl implements FzuUserAddressService {

    private final FzuUserAddressRepository fzuUserAddressRepository;
    private final FzuUserAddressMapper fzuUserAddressMapper;

    @Override
    public PageResult<FzuUserAddressDto> queryAll(FzuUserAddressQueryCriteria criteria, Pageable pageable){
        Page<FzuUserAddress> page = fzuUserAddressRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(fzuUserAddressMapper::toDto));
    }

    @Override
    public List<FzuUserAddressDto> queryAll(FzuUserAddressQueryCriteria criteria){
        return fzuUserAddressMapper.toDto(fzuUserAddressRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public FzuUserAddressDto findById(Integer id) {
        FzuUserAddress fzuUserAddress = fzuUserAddressRepository.findById(id).orElseGet(FzuUserAddress::new);
        ValidationUtil.isNull(fzuUserAddress.getId(),"FzuUserAddress","id",id);
        return fzuUserAddressMapper.toDto(fzuUserAddress);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(FzuUserAddress resources) {
        fzuUserAddressRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FzuUserAddress resources) {
        FzuUserAddress fzuUserAddress = fzuUserAddressRepository.findById(resources.getId()).orElseGet(FzuUserAddress::new);
        ValidationUtil.isNull( fzuUserAddress.getId(),"FzuUserAddress","id",resources.getId());
        fzuUserAddress.copy(resources);
        fzuUserAddressRepository.save(fzuUserAddress);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            fzuUserAddressRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<FzuUserAddressDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FzuUserAddressDto fzuUserAddress : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户id", fzuUserAddress.getUserId());
            map.put("用户地址", fzuUserAddress.getUserAddress());
            map.put("用户电话", fzuUserAddress.getUserPhone());
            map.put("用户账户", fzuUserAddress.getUsername());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}