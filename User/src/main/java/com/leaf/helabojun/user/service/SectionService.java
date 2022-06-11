package com.leaf.helabojun.user.service;

import com.leaf.helabojun.user.dto.SectionDTO;
import com.leaf.helabojun.user.dto.common.CommonListDTO;
import com.leaf.helabojun.user.dto.common.ListResponseDTO;
import com.leaf.helabojun.user.dto.common.ResponseDTO;
import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.enums.ChannelEnum;

import java.util.List;

public interface SectionService {

    ResponseDTO<String, SectionDTO> saveSection(ChannelEnum channel, String username, SectionDTO sectionDTO);

    ResponseDTO<String, SectionDTO> updateSection(ChannelEnum channel, String uuid, SectionDTO sectionDTO, String username);

    ResponseDTO<String, SectionDTO> deleteSection(ChannelEnum channel, String uuid, String username);

    ResponseDTO<String, SectionDTO> findSection(ChannelEnum channel, String uuid);

    ListResponseDTO<List<CommonListDTO<SectionDTO>>> findSections(ChannelEnum channel, SearchRequestDTO searchRequestDTO);

}
