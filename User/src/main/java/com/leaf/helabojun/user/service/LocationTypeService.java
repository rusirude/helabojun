package com.leaf.helabojun.user.service;

import com.leaf.helabojun.user.dto.LocationTypeDTO;
import com.leaf.helabojun.user.dto.common.CommonListDTO;
import com.leaf.helabojun.user.dto.common.ListResponseDTO;
import com.leaf.helabojun.user.dto.common.ResponseDTO;
import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.enums.ChannelEnum;

import java.util.List;

public interface LocationTypeService {

    ResponseDTO<String, LocationTypeDTO> saveLocationType(ChannelEnum channel, String username, LocationTypeDTO locationTypeDTO);

    ResponseDTO<String, LocationTypeDTO> updateLocationType(ChannelEnum channel, String uuid, LocationTypeDTO locationTypeDTO, String username);

    ResponseDTO<String, LocationTypeDTO> deleteLocationType(ChannelEnum channel, String uuid, String username);

    ResponseDTO<String, LocationTypeDTO> findLocationType(ChannelEnum channel, String uuid);

    ListResponseDTO<List<CommonListDTO<LocationTypeDTO>>> findLocationTypes(ChannelEnum channel, SearchRequestDTO searchRequestDTO);

}
