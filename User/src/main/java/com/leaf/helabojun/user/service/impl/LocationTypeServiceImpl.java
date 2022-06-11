package com.leaf.helabojun.user.service.impl;

import com.leaf.helabojun.user.dto.LocationTypeDTO;
import com.leaf.helabojun.user.dto.common.CommonListDTO;
import com.leaf.helabojun.user.dto.common.ListResponseDTO;
import com.leaf.helabojun.user.dto.common.ResponseDTO;
import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.entity.LocationType;
import com.leaf.helabojun.user.enums.ChannelEnum;
import com.leaf.helabojun.user.enums.ResponseEnum;
import com.leaf.helabojun.user.enums.StatusEnum;
import com.leaf.helabojun.user.exception.DataConflictException;
import com.leaf.helabojun.user.exception.DataNotFoundException;
import com.leaf.helabojun.user.repository.LocationTypeRepository;
import com.leaf.helabojun.user.repository.specification.LocationTypeSpecification;
import com.leaf.helabojun.user.service.LocationTypeService;
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
public class LocationTypeServiceImpl implements LocationTypeService {

    private ModelMapper modelMapper;
    private CommonFunction commonFunction;
    private MessageResource messageResource;

    private LocationTypeRepository locationTypeRepository;
    private LocationTypeSpecification locationTypeSpecification;


    @Override
    public ResponseDTO<String, LocationTypeDTO> saveLocationType(ChannelEnum channel, String username, LocationTypeDTO locationTypeDTO) {
        try {

            LocationType locationType = locationTypeRepository.findByDescriptionAndStatusNot(locationTypeDTO.getDescription(), StatusEnum.DELETE.getCode())
                    .orElse(new LocationType());
            if (Objects.nonNull(locationType.getId()))
                throw new DataConflictException(messageResource.getMessage(MessageConstant.LOCATION_TYPE_EXISTS, new Object[]{locationTypeDTO.getDescription()}));

            modelMapper.map(locationTypeDTO, locationType);
            locationType.setUuid(commonFunction.getUuid());
            commonFunction.populateInsert(locationType, username);
            locationTypeRepository.save(locationType);

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.LOCATION_TYPE_SAVE_SUCCESS, new Object[]{locationTypeDTO.getDescription()}),
                    locationType.getUuid(),
                    modelMapper.map(locationType, LocationTypeDTO.class));

        } catch (DataConflictException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String, LocationTypeDTO> updateLocationType(ChannelEnum channel, String uuid, LocationTypeDTO locationTypeDTO, String username) {
        try {

            LocationType locationType = locationTypeRepository.findByUuidAndStatusNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.LOCATION_TYPE_NOT_FOUND)));

            if (locationTypeRepository.findByDescriptionAndStatusNot(locationTypeDTO.getDescription(), StatusEnum.DELETE.getCode()).isPresent())
                throw new DataConflictException(messageResource.getMessage(MessageConstant.LOCATION_TYPE_EXISTS, new Object[]{locationTypeDTO.getDescription()}));

            String previousLocationTypeDescription = locationType.getDescription();
            locationType.setDescription(locationTypeDTO.getDescription());
            locationType.setStatus(locationTypeDTO.getStatus());

            commonFunction.populateUpdate(locationType, username);
            locationTypeRepository.save(locationType);

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.LOCATION_TYPE_UPDATE_SUCCESS, new Object[]{previousLocationTypeDescription, locationTypeDTO.getDescription()}),
                    locationType.getUuid(),
                    modelMapper.map(locationType, LocationTypeDTO.class));

        } catch (DataNotFoundException | DataConflictException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String, LocationTypeDTO> deleteLocationType(ChannelEnum channel, String uuid, String username) {
        try {

            LocationType locationType = locationTypeRepository.findByUuidAndStatusNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.LOCATION_TYPE_NOT_FOUND)));

            locationType.setStatus(StatusEnum.DELETE.getCode());
            commonFunction.populateUpdate(locationType, username);
            locationTypeRepository.save(locationType);

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.LOCATION_TYPE_DELETE_SUCCESS, new Object[]{locationType.getDescription()}),
                    locationType.getUuid(),
                    modelMapper.map(locationType, LocationTypeDTO.class));
        } catch (DataNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String, LocationTypeDTO> findLocationType(ChannelEnum channel, String uuid) {
        try {

            LocationType locationType = locationTypeRepository.findByUuidAndStatusNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.LOCATION_TYPE_NOT_FOUND)));

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.LOCATION_TYPE_FOUND, new Object[]{locationType.getDescription()}),
                    locationType.getUuid(),
                    modelMapper.map(locationType, LocationTypeDTO.class));

        } catch (DataNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ListResponseDTO<List<CommonListDTO<LocationTypeDTO>>> findLocationTypes(ChannelEnum channel, SearchRequestDTO searchRequestDTO) {
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


            List<CommonListDTO<LocationTypeDTO>> locationTypes = locationTypeRepository.findAll(locationTypeSpecification.generateSearchCriteria(searchRequestDTO), pageRequest).getContent()
                    .stream()
                    .map(locationType -> new CommonListDTO<>(locationType.getUuid(), modelMapper.map(locationType, LocationTypeDTO.class)))
                    .collect(Collectors.toList());

            Long fullCount = locationTypeRepository.count(locationTypeSpecification.generateSearchCriteria(searchRequestDTO));


            return new ListResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.LOCATION_TYPE_LIST_FOUND),
                    locationTypes,
                    fullCount);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
