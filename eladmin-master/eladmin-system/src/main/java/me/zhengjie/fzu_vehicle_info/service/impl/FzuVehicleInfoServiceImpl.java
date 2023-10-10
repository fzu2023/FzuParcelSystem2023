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
package me.zhengjie.fzu_vehicle_info.service.impl;

import me.zhengjie.fzu_vehicle_info.domain.FzuVehicleInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.fzu_vehicle_info.repository.FzuVehicleInfoRepository;
import me.zhengjie.fzu_vehicle_info.service.FzuVehicleInfoService;
import me.zhengjie.fzu_vehicle_info.service.dto.FzuVehicleInfoDto;
import me.zhengjie.fzu_vehicle_info.service.dto.FzuVehicleInfoQueryCriteria;
import me.zhengjie.fzu_vehicle_info.service.mapstruct.FzuVehicleInfoMapper;
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
public class FzuVehicleInfoServiceImpl implements FzuVehicleInfoService {

    private final FzuVehicleInfoRepository fzuVehicleInfoRepository;
    private final FzuVehicleInfoMapper fzuVehicleInfoMapper;

    @Override
    public PageResult<FzuVehicleInfoDto> queryAll(FzuVehicleInfoQueryCriteria criteria, Pageable pageable){
        Page<FzuVehicleInfo> page = fzuVehicleInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(fzuVehicleInfoMapper::toDto));
    }

    @Override
    public List<FzuVehicleInfoDto> queryAll(FzuVehicleInfoQueryCriteria criteria){
        return fzuVehicleInfoMapper.toDto(fzuVehicleInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public FzuVehicleInfoDto findById(Integer id) {
        FzuVehicleInfo fzuVehicleInfo = fzuVehicleInfoRepository.findById(id).orElseGet(FzuVehicleInfo::new);
        ValidationUtil.isNull(fzuVehicleInfo.getId(),"FzuVehicleInfo","id",id);
        return fzuVehicleInfoMapper.toDto(fzuVehicleInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(FzuVehicleInfo resources) {
        fzuVehicleInfoRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FzuVehicleInfo resources) {
        FzuVehicleInfo fzuVehicleInfo = fzuVehicleInfoRepository.findById(resources.getId()).orElseGet(FzuVehicleInfo::new);
        ValidationUtil.isNull( fzuVehicleInfo.getId(),"FzuVehicleInfo","id",resources.getId());
        fzuVehicleInfo.copy(resources);
        fzuVehicleInfoRepository.save(fzuVehicleInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            fzuVehicleInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<FzuVehicleInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FzuVehicleInfoDto fzuVehicleInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("所在网点id", fzuVehicleInfo.getOutletId());
            map.put("是否空闲", fzuVehicleInfo.getIsFree());
            map.put("驾驶员电话", fzuVehicleInfo.getDriverPhone());
            map.put("车牌号", fzuVehicleInfo.getLicense());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}