package com.leaf.helabojun.user.service;

import com.leaf.helabojun.user.dto.UserRoleDTO;
import com.leaf.helabojun.user.dto.common.CommonListDTO;
import com.leaf.helabojun.user.dto.common.ListResponseDTO;
import com.leaf.helabojun.user.dto.common.ResponseDTO;
import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.enums.ChannelEnum;

import java.util.List;

public interface UserRoleService {

    ResponseDTO<String, UserRoleDTO> saveUserRole(ChannelEnum channel, String username, UserRoleDTO userRoleDTO);

    ResponseDTO<String, UserRoleDTO> updateUserRole(ChannelEnum channel, String uuid, UserRoleDTO userRoleDTO, String username);

    ResponseDTO<String, UserRoleDTO> deleteUserRole(ChannelEnum channel, String uuid, String username);

    ResponseDTO<String, UserRoleDTO> findUserRole(ChannelEnum channel, String uuid);

    ListResponseDTO<List<CommonListDTO<UserRoleDTO>>> findUserRoles(ChannelEnum channel, SearchRequestDTO searchRequestDTO);

}
