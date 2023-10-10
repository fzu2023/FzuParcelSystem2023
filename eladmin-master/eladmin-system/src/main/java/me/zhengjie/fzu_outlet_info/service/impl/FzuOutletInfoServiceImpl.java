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
package me.zhengjie.fzu_outlet_info.service.impl;

import me.zhengjie.fzu_outlet_info.domain.FzuOutletInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.fzu_outlet_info.repository.FzuOutletInfoRepository;
import me.zhengjie.fzu_outlet_info.service.FzuOutletInfoService;
import me.zhengjie.fzu_outlet_info.service.dto.FzuOutletInfoDto;
import me.zhengjie.fzu_outlet_info.service.dto.FzuOutletInfoQueryCriteria;
import me.zhengjie.fzu_outlet_info.service.mapstruct.FzuOutletInfoMapper;
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
* @author ChenShan
* @date 2023-10-09
**/
@Service
@RequiredArgsConstructor
public class FzuOutletInfoServiceImpl implements FzuOutletInfoService {

    private final FzuOutletInfoRepository fzuOutletInfoRepository;
    private final FzuOutletInfoMapper fzuOutletInfoMapper;

    @Override
    public PageResult<FzuOutletInfoDto> queryAll(FzuOutletInfoQueryCriteria criteria, Pageable pageable){
        Page<FzuOutletInfo> page = fzuOutletInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(fzuOutletInfoMapper::toDto));
    }

    @Override
    public List<FzuOutletInfoDto> queryAll(FzuOutletInfoQueryCriteria criteria){
        return fzuOutletInfoMapper.toDto(fzuOutletInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public FzuOutletInfoDto findById(Integer id) {
        FzuOutletInfo fzuOutletInfo = fzuOutletInfoRepository.findById(id).orElseGet(FzuOutletInfo::new);
        ValidationUtil.isNull(fzuOutletInfo.getId(),"FzuOutletInfo","id",id);
        return fzuOutletInfoMapper.toDto(fzuOutletInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(FzuOutletInfo resources) {
        fzuOutletInfoRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FzuOutletInfo resources) {
        FzuOutletInfo fzuOutletInfo = fzuOutletInfoRepository.findById(resources.getId()).orElseGet(FzuOutletInfo::new);
        ValidationUtil.isNull( fzuOutletInfo.getId(),"FzuOutletInfo","id",resources.getId());
        fzuOutletInfo.copy(resources);
        fzuOutletInfoRepository.save(fzuOutletInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            fzuOutletInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<FzuOutletInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FzuOutletInfoDto fzuOutletInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("网点名字", fzuOutletInfo.getNickname());
            map.put("负责人电话", fzuOutletInfo.getAdminPhone());
            map.put("负责人邮箱", fzuOutletInfo.getAdminEmail());
            map.put("现有容量/仓房间数", fzuOutletInfo.getCurrentCapacity());
            map.put("总容量/仓库房间数", fzuOutletInfo.getTotalCapacity());
            map.put("网点id", fzuOutletInfo.getNickid());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}