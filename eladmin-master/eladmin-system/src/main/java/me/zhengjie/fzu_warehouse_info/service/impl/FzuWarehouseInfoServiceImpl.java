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
package me.zhengjie.fzu_warehouse_info.service.impl;

import me.zhengjie.fzu_warehouse_info.domain.FzuWarehouseInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.fzu_warehouse_info.repository.FzuWarehouseInfoRepository;
import me.zhengjie.fzu_warehouse_info.service.FzuWarehouseInfoService;
import me.zhengjie.fzu_warehouse_info.service.dto.FzuWarehouseInfoDto;
import me.zhengjie.fzu_warehouse_info.service.dto.FzuWarehouseInfoQueryCriteria;
import me.zhengjie.fzu_warehouse_info.service.mapstruct.FzuWarehouseInfoMapper;
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
* @date 2023-10-09
**/
@Service
@RequiredArgsConstructor
public class FzuWarehouseInfoServiceImpl implements FzuWarehouseInfoService {

    private final FzuWarehouseInfoRepository fzuWarehouseInfoRepository;
    private final FzuWarehouseInfoMapper fzuWarehouseInfoMapper;

    @Override
    public PageResult<FzuWarehouseInfoDto> queryAll(FzuWarehouseInfoQueryCriteria criteria, Pageable pageable){
        Page<FzuWarehouseInfo> page = fzuWarehouseInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(fzuWarehouseInfoMapper::toDto));
    }

    @Override
    public List<FzuWarehouseInfoDto> queryAll(FzuWarehouseInfoQueryCriteria criteria){
        return fzuWarehouseInfoMapper.toDto(fzuWarehouseInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public FzuWarehouseInfoDto findById(Integer id) {
        FzuWarehouseInfo fzuWarehouseInfo = fzuWarehouseInfoRepository.findById(id).orElseGet(FzuWarehouseInfo::new);
        ValidationUtil.isNull(fzuWarehouseInfo.getId(),"FzuWarehouseInfo","id",id);
        return fzuWarehouseInfoMapper.toDto(fzuWarehouseInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(FzuWarehouseInfo resources) {
        fzuWarehouseInfoRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FzuWarehouseInfo resources) {
        FzuWarehouseInfo fzuWarehouseInfo = fzuWarehouseInfoRepository.findById(resources.getId()).orElseGet(FzuWarehouseInfo::new);
        ValidationUtil.isNull( fzuWarehouseInfo.getId(),"FzuWarehouseInfo","id",resources.getId());
        fzuWarehouseInfo.copy(resources);
        fzuWarehouseInfoRepository.save(fzuWarehouseInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            fzuWarehouseInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<FzuWarehouseInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FzuWarehouseInfoDto fzuWarehouseInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("网点id", fzuWarehouseInfo.getOutletId());
            map.put("当前容量", fzuWarehouseInfo.getCurrentCapacity());
            map.put("总容量", fzuWarehouseInfo.getTotalCapacity());
            map.put("网点名字", fzuWarehouseInfo.getOutName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}