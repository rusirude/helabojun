package com.leaf.helabojun.user.service;

import com.leaf.helabojun.user.dto.LocationDTO;
import com.leaf.helabojun.user.dto.common.CommonListDTO;
import com.leaf.helabojun.user.dto.common.ListResponseDTO;
import com.leaf.helabojun.user.dto.common.ResponseDTO;
import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.enums.ChannelEnum;

import java.util.List;

public interface LocationService {

    ResponseDTO<String, LocationDTO> saveLocation(ChannelEnum channel, String username, LocationDTO locationDTO);

    ResponseDTO<String, LocationDTO> updateLocation(ChannelEnum channel, String uuid, LocationDTO locationDTO, String username);

    ResponseDTO<String, LocationDTO> deleteLocation(ChannelEnum channel, String uuid, String username);

    ResponseDTO<String, LocationDTO> findLocation(ChannelEnum channel, String uuid);

    ListResponseDTO<List<CommonListDTO<LocationDTO>>> findLocations(ChannelEnum channel, SearchRequestDTO searchRequestDTO);

}
