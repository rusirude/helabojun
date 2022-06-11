package com.leaf.helabojun.user.service.impl;

import com.leaf.helabojun.user.dto.UserRoleDTO;
import com.leaf.helabojun.user.dto.common.CommonListDTO;
import com.leaf.helabojun.user.dto.common.ListResponseDTO;
import com.leaf.helabojun.user.dto.common.ResponseDTO;
import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.entity.UserRole;
import com.leaf.helabojun.user.enums.ChannelEnum;
import com.leaf.helabojun.user.enums.ResponseEnum;
import com.leaf.helabojun.user.enums.StatusEnum;
import com.leaf.helabojun.user.exception.DataConflictException;
import com.leaf.helabojun.user.exception.DataNotFoundException;
import com.leaf.helabojun.user.repository.UserRepository;
import com.leaf.helabojun.user.repository.UserRoleRepository;
import com.leaf.helabojun.user.repository.specification.UserRoleSpecification;
import com.leaf.helabojun.user.service.UserRoleService;
import com.leaf.helabojun.user.utility.CommonFunction;
import com.leaf.helabojun.user.utility.MessageConstant;
import com.leaf.helabojun.user.utility.MessageResource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserRoleServiceImpl implements UserRoleService {

    private ModelMapper modelMapper;
    private CommonFunction commonFunction;
    private MessageResource messageResource;

    private UserRoleRepository userRoleRepository;
    private UserRoleSpecification userRoleSpecification;
    private UserRepository userRepository;


    @Override
    public ResponseDTO<String, UserRoleDTO> saveUserRole(ChannelEnum channel, String username, UserRoleDTO userRoleDTO) {
        try {

            UserRole userRole = userRoleRepository.findByDescriptionAndStatusNot(userRoleDTO.getDescription(), StatusEnum.DELETE.getCode())
                    .orElse(new UserRole());
            if (Objects.nonNull(userRole.getId()))
                throw new DataConflictException(messageResource.getMessage(MessageConstant.USER_ROLE_EXISTS, new Object[]{userRoleDTO.getDescription()}));

            modelMapper.map(userRoleDTO, userRole);
            userRole.setUuid(commonFunction.getUuid());
            commonFunction.populateInsert(userRole, username);
            userRoleRepository.save(userRole);

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.USER_ROLE_SAVE_SUCCESS, new Object[]{userRoleDTO.getDescription()}),
                    userRole.getUuid(),
                    modelMapper.map(userRole, UserRoleDTO.class));

        } catch (DataConflictException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String, UserRoleDTO> updateUserRole(ChannelEnum channel, String uuid, UserRoleDTO userRoleDTO, String username) {
        try {

            UserRole userRole = userRoleRepository.findByUuidAndStatusNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.USER_ROLE_NOT_FOUND)));

            if (userRoleRepository.findByDescriptionAndStatusNot(userRoleDTO.getDescription(), StatusEnum.DELETE.getCode()).isPresent())
                throw new DataConflictException(messageResource.getMessage(MessageConstant.USER_ROLE_EXISTS, new Object[]{userRoleDTO.getDescription()}));

            String previousUserRoleDescription = userRole.getDescription();
            userRole.setDescription(userRoleDTO.getDescription());
            userRole.setStatus(userRoleDTO.getStatus());

            commonFunction.populateUpdate(userRole, username);
            userRoleRepository.save(userRole);

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.USER_ROLE_UPDATE_SUCCESS, new Object[]{previousUserRoleDescription, userRoleDTO.getDescription()}),
                    userRole.getUuid(),
                    modelMapper.map(userRole, UserRoleDTO.class));

        } catch (DataNotFoundException | DataConflictException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String, UserRoleDTO> deleteUserRole(ChannelEnum channel, String uuid, String username) {
        try {

            UserRole userRole = userRoleRepository.findByUuidAndStatusNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.USER_ROLE_NOT_FOUND)));

            if (userRepository.countByStatusNot(StatusEnum.DELETE.getCode()) > 0)
                throw new DataConflictException(messageResource.getMessage(MessageConstant.USER_ROLE_USED_BY_USER, new Object[]{userRole.getDescription()}));

            userRole.setStatus(StatusEnum.DELETE.getCode());
            commonFunction.populateUpdate(userRole, username);
            userRoleRepository.save(userRole);

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.USER_ROLE_DELETE_SUCCESS, new Object[]{userRole.getDescription()}),
                    userRole.getUuid(),
                    modelMapper.map(userRole, UserRoleDTO.class));
        } catch (DataNotFoundException | DataConflictException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String, UserRoleDTO> findUserRole(ChannelEnum channel, String uuid) {
        try {

            UserRole userRole = userRoleRepository.findByUuidAndStatusNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.USER_ROLE_NOT_FOUND)));

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.USER_ROLE_FOUND, new Object[]{userRole.getDescription()}),
                    userRole.getUuid(),
                    modelMapper.map(userRole, UserRoleDTO.class));

        } catch (DataNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ListResponseDTO<List<CommonListDTO<UserRoleDTO>>> findUserRoles(ChannelEnum channel, SearchRequestDTO searchRequestDTO) {
        try {
            PageRequest pageRequest;

            if (Objects.nonNull(Optional.ofNullable(searchRequestDTO.getOrderBy()).orElse(null)) &&
                    Objects.nonNull(Optional.ofNullable(searchRequestDTO.getOrder()).orElse(null)))
                pageRequest = PageRequest.of(
                        Optional.ofNullable(searchRequestDTO.getStart()).orElse(0), Optional.ofNullable(searchRequestDTO.getLimit()).orElse(Integer.MAX_VALUE),
                        Sort.by(Sort.Direction.valueOf(searchRequestDTO.getOrder()), searchRequestDTO.getOrderBy())
                );
            else
                pageRequest = PageRequest.of(Optional.ofNullable(searchRequestDTO.getStart()).orElse(0), Optional.ofNullable(searchRequestDTO.getLimit()).orElse(Integer.MAX_VALUE));


            List<CommonListDTO<UserRoleDTO>> userRoles = userRoleRepository.findAll(userRoleSpecification.generateSearchCriteria(searchRequestDTO), pageRequest).getContent()
                    .stream()
                    .map(userRole -> new CommonListDTO<>(userRole.getUuid(), modelMapper.map(userRole, UserRoleDTO.class)))
                    .collect(Collectors.toList());

            Long fullCount = userRoleRepository.count(userRoleSpecification.generateSearchCriteria(searchRequestDTO));


            return new ListResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.USER_ROLE_LIST_FOUND),
                    userRoles,
                    fullCount);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
