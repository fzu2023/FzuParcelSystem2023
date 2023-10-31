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
package me.zhengjie.fzu_item_info.service.impl;

import me.zhengjie.fzu_item_info.domain.FzuItemInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.fzu_item_info.repository.FzuItemInfoRepository;
import me.zhengjie.fzu_item_info.service.FzuItemInfoService;
import me.zhengjie.fzu_item_info.service.dto.FzuItemInfoDto;
import me.zhengjie.fzu_item_info.service.dto.FzuItemInfoQueryCriteria;
import me.zhengjie.fzu_item_info.service.mapstruct.FzuItemInfoMapper;
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
* @date 2023-10-31
**/
@Service
@RequiredArgsConstructor
public class FzuItemInfoServiceImpl implements FzuItemInfoService {

    private final FzuItemInfoRepository fzuItemInfoRepository;
    private final FzuItemInfoMapper fzuItemInfoMapper;

    @Override
    public PageResult<FzuItemInfoDto> queryAll(FzuItemInfoQueryCriteria criteria, Pageable pageable){
        Page<FzuItemInfo> page = fzuItemInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(fzuItemInfoMapper::toDto));
    }

    @Override
    public List<FzuItemInfoDto> queryAll(FzuItemInfoQueryCriteria criteria){
        return fzuItemInfoMapper.toDto(fzuItemInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public FzuItemInfoDto findById(Integer id) {
        FzuItemInfo fzuItemInfo = fzuItemInfoRepository.findById(id).orElseGet(FzuItemInfo::new);
        ValidationUtil.isNull(fzuItemInfo.getId(),"FzuItemInfo","id",id);
        return fzuItemInfoMapper.toDto(fzuItemInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(FzuItemInfo resources) {
        fzuItemInfoRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(FzuItemInfo resources) {
        FzuItemInfo fzuItemInfo = fzuItemInfoRepository.findById(resources.getId()).orElseGet(FzuItemInfo::new);
        ValidationUtil.isNull( fzuItemInfo.getId(),"FzuItemInfo","id",resources.getId());
        fzuItemInfo.copy(resources);
        fzuItemInfoRepository.save(fzuItemInfo);
    }

    @Override
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            fzuItemInfoRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<FzuItemInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (FzuItemInfoDto fzuItemInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("寄件人", fzuItemInfo.getSender());
            map.put("收件人", fzuItemInfo.getRecipient());
            map.put("寄出地址", fzuItemInfo.getSenderAddress());
            map.put("发往地址", fzuItemInfo.getRecipientAddress());
            map.put("寄件人电话", fzuItemInfo.getSenderPhone());
            map.put("收件人电话", fzuItemInfo.getRecipientPhone());
            map.put("订单状态", fzuItemInfo.getItemStatus());
            map.put("派件员id", fzuItemInfo.getDeliveryId());
            map.put("订单id", fzuItemInfo.getItemId());
            map.put("订单生成时间", fzuItemInfo.getItemStartTime());
            map.put("订单完成时间", fzuItemInfo.getItemEndTime());
            map.put("寄件网点", fzuItemInfo.getSenderOutlet());
            map.put("收件网点", fzuItemInfo.getRecipientOutlet());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}