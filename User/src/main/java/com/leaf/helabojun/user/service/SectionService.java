package com.leaf.helabojun.user.service;

import com.leaf.helabojun.user.dto.SectionDTO;
import com.leaf.helabojun.user.dto.common.CommonListDTO;
import com.leaf.helabojun.user.dto.common.ResponseDTO;
import com.leaf.helabojun.user.entity.Section;

import java.util.List;

public interface SectionService {

    ResponseDTO<String,SectionDTO> saveSection(SectionDTO sectionDTO ,String username);

    ResponseDTO<String,SectionDTO> updateSection(String uuid, SectionDTO sectionDTO ,String username);

    ResponseDTO<String,SectionDTO> deleteSection(String uuid ,String username);

    ResponseDTO<String,SectionDTO> findSection(String uuid);

    ResponseDTO<Long,List<CommonListDTO<SectionDTO>>> finSections();

}
