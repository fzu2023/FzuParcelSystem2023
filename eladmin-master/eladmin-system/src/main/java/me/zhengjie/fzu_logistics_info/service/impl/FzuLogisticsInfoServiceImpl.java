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
package me.zhengjie.fzu_logistics_info.service.impl;

import me.zhengjie.fzu_logistics_info.domain.FzuLogisticsInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.fzu_logistics_info.repository.FzuLogisticsInfoRepository;
import me.zhengjie.fzu_logistics_info.service.FzuLogisticsInfoService;
import me.zhengjie.fzu_logistics_info.service.dto.FzuLogisticsInfoDto;
import me.zhengjie.fzu_logistics_info.service.dto.FzuLogisticsInfoQueryCriteria;
import me.zhengjie.fzu_logistics_info.service.mapstruct.FzuLogisticsInfoMapper;
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
public class FzuLogisticsInfoServiceImpl implements FzuLogisticsInfoService {

    private final FzuLogisticsInfoRepository fzuLogisticsInfoRepository;
    private final FzuLogisticsInfoMapper fzuLogisticsInfoMapper;

    @Override
    public PageResult<FzuLogisticsInfoDto> queryAll(FzuLogisticsInfoQueryCriteria criteria, Pageable pageable){
        Page<FzuLogisticsInfo> page = fzuLogisticsInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(fzuLogisticsInfoMapper::toDto));
    }

    @Override
    public List<FzuLogisticsInfoDto> queryAll(FzuLogisticsInfoQueryCriteria criteria){
        return fzuLogisticsInfoMapper.toDto(fzuLogisticsInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public FzuLogisticsInfoDto findById(Integer id) {
        FzuLogisticsInfo fzuLogisticsInfo = fzuLogisticsInfoRepository.findById(id).orElseGet(FzuLogisticsInfo::new);
        ValidationUtil.isNull(fzuLogisticsInfo.getId(),"FzuLogisticsInfo","id",id);
        return fzuLogisticsInfoMapper.toDto(fzuLogisticsInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(FzuLogisticsInfo resources) {
        fzuLogisticsInfoRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FzuLogisticsInfo resources) {
        FzuLogisticsInfo fzuLogisticsInfo = fzuLogisticsInfoRepository.findById(resources.getId()).orElseGet(FzuLogisticsInfo::new);
        ValidationUtil.isNull( fzuLogisticsInfo.getId(),"FzuLogisticsInfo","id",resources.getId());
        fzuLogisticsInfo.copy(resources);
        fzuLogisticsInfoRepository.save(fzuLogisticsInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            fzuLogisticsInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<FzuLogisticsInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FzuLogisticsInfoDto fzuLogisticsInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("订单id", fzuLogisticsInfo.getItemId());
            map.put("发出城市", fzuLogisticsInfo.getSenderCity());
            map.put("当前所在城市", fzuLogisticsInfo.getCurrentCity());
            map.put("接收城市", fzuLogisticsInfo.getRecipientCity());
            map.put("司机名字", fzuLogisticsInfo.getDriverName());
            map.put("发车开始时间", fzuLogisticsInfo.getStartDate());
            map.put("卸货结束时间", fzuLogisticsInfo.getEndDate());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}