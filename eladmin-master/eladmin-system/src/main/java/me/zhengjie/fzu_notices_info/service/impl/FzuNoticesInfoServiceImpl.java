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
package me.zhengjie.fzu_notices_info.service.impl;

import me.zhengjie.fzu_notices_info.domain.FzuNoticesInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.fzu_notices_info.repository.FzuNoticesInfoRepository;
import me.zhengjie.fzu_notices_info.service.FzuNoticesInfoService;
import me.zhengjie.fzu_notices_info.service.dto.FzuNoticesInfoDto;
import me.zhengjie.fzu_notices_info.service.dto.FzuNoticesInfoQueryCriteria;
import me.zhengjie.fzu_notices_info.service.mapstruct.FzuNoticesInfoMapper;
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
* @date 2023-11-06
**/
@Service
@RequiredArgsConstructor
public class FzuNoticesInfoServiceImpl implements FzuNoticesInfoService {

    private final FzuNoticesInfoRepository fzuNoticesInfoRepository;
    private final FzuNoticesInfoMapper fzuNoticesInfoMapper;

    @Override
    public PageResult<FzuNoticesInfoDto> queryAll(FzuNoticesInfoQueryCriteria criteria, Pageable pageable){
        Page<FzuNoticesInfo> page = fzuNoticesInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(fzuNoticesInfoMapper::toDto));
    }

    @Override
    public List<FzuNoticesInfoDto> queryAll(FzuNoticesInfoQueryCriteria criteria){
        return fzuNoticesInfoMapper.toDto(fzuNoticesInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public FzuNoticesInfoDto findById(Integer id) {
        FzuNoticesInfo fzuNoticesInfo = fzuNoticesInfoRepository.findById(id).orElseGet(FzuNoticesInfo::new);
        ValidationUtil.isNull(fzuNoticesInfo.getId(),"FzuNoticesInfo","id",id);
        return fzuNoticesInfoMapper.toDto(fzuNoticesInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(FzuNoticesInfo resources) {
        fzuNoticesInfoRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FzuNoticesInfo resources) {
        FzuNoticesInfo fzuNoticesInfo = fzuNoticesInfoRepository.findById(resources.getId()).orElseGet(FzuNoticesInfo::new);
        ValidationUtil.isNull( fzuNoticesInfo.getId(),"FzuNoticesInfo","id",resources.getId());
        fzuNoticesInfo.copy(resources);
        fzuNoticesInfoRepository.save(fzuNoticesInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            fzuNoticesInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<FzuNoticesInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FzuNoticesInfoDto fzuNoticesInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("发布人", fzuNoticesInfo.getAuthor());
            map.put("所属部门", fzuNoticesInfo.getDept());
            map.put("内容", fzuNoticesInfo.getContent());
            map.put("更新时间", fzuNoticesInfo.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}