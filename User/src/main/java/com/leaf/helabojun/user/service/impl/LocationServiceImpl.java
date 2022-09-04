package com.leaf.helabojun.user.service.impl;

import com.leaf.helabojun.user.dto.LocationDTO;
import com.leaf.helabojun.user.dto.common.CommonListDTO;
import com.leaf.helabojun.user.dto.common.ListResponseDTO;
import com.leaf.helabojun.user.dto.common.ResponseDTO;
import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.entity.Location;
import com.leaf.helabojun.user.entity.LocationType;
import com.leaf.helabojun.user.enums.ChannelEnum;
import com.leaf.helabojun.user.enums.ResponseEnum;
import com.leaf.helabojun.user.enums.StatusEnum;
import com.leaf.helabojun.user.exception.DataConflictException;
import com.leaf.helabojun.user.exception.DataNotFoundException;
import com.leaf.helabojun.user.repository.LocationRepository;
import com.leaf.helabojun.user.repository.LocationTypeRepository;
import com.leaf.helabojun.user.repository.specification.LocationSpecification;
import com.leaf.helabojun.user.service.LocationService;
import com.leaf.helabojun.user.utility.CommonFunction;
import com.leaf.helabojun.user.utility.MessageConstant;
import com.leaf.helabojun.user.utility.MessageResource;
import com.leaf.helabojun.user.utility.StatusUtil;
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
public class LocationServiceImpl implements LocationService {

    private ModelMapper modelMapper;
    private CommonFunction commonFunction;
    private MessageResource messageResource;
    private StatusUtil  statusUtil;

    private LocationRepository locationRepository;
    private LocationTypeRepository locationTypeRepository;
    private LocationSpecification locationSpecification;


    @Override
    public ResponseDTO<String, LocationDTO> saveLocation(ChannelEnum channel, String username, LocationDTO locationDTO) {
        try {

            Location location = locationRepository.findByDescriptionAndStatusCodeNot(locationDTO.getDescription(), StatusEnum.DELETE.getCode())
                    .orElse(new Location());
            if (Objects.nonNull(location.getId()))
                throw new DataConflictException(messageResource.getMessage(MessageConstant.LOCATION_EXISTS, new Object[]{locationDTO.getDescription()}));

            LocationType locationType = locationTypeRepository.findByUuidAndStatusCodeNot(locationDTO.getLocationType(), StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.LOCATION_TYPE_NOT_FOUND)));


            modelMapper.map(locationDTO, location);
            location.setUuid(commonFunction.getUuid());
            location.setLocationType(locationType);
            commonFunction.populateInsert(location, username);
            locationRepository.save(location);

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.LOCATION_SAVE_SUCCESS, new Object[]{locationDTO.getDescription()}),
                    location.getUuid(),
                    modelMapper.map(location, LocationDTO.class));

        } catch (DataConflictException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String, LocationDTO> updateLocation(ChannelEnum channel, String uuid, LocationDTO locationDTO, String username) {
        try {

            Location location = locationRepository.findByUuidAndStatusCodeNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.LOCATION_NOT_FOUND)));

            if (locationRepository.findByDescriptionAndStatusCodeNot(locationDTO.getDescription(), StatusEnum.DELETE.getCode()).isPresent())
                throw new DataConflictException(messageResource.getMessage(MessageConstant.LOCATION_EXISTS, new Object[]{locationDTO.getDescription()}));

            LocationType locationType = locationTypeRepository.findByUuidAndStatusCodeNot(locationDTO.getLocationType(), StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.LOCATION_TYPE_NOT_FOUND)));


            String previousLocationDescription = location.getDescription();
            location.setDescription(locationDTO.getDescription());
            location.setLocationType(locationType);
            location.setStatus(statusUtil.getStatus(locationDTO.getStatus()));

            commonFunction.populateUpdate(location, username);
            locationRepository.save(location);

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.LOCATION_UPDATE_SUCCESS, new Object[]{previousLocationDescription, locationDTO.getDescription()}),
                    location.getUuid(),
                    modelMapper.map(location, LocationDTO.class));

        } catch (DataNotFoundException | DataConflictException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String, LocationDTO> deleteLocation(ChannelEnum channel, String uuid, String username) {
        try {

            Location location = locationRepository.findByUuidAndStatusCodeNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.LOCATION_NOT_FOUND)));

            location.setStatus(statusUtil.getStatus(StatusEnum.DELETE.getCode()));
            commonFunction.populateUpdate(location, username);
            locationRepository.save(location);

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.LOCATION_DELETE_SUCCESS, new Object[]{location.getDescription()}),
                    location.getUuid(),
                    modelMapper.map(location, LocationDTO.class));
        } catch (DataNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String, LocationDTO> findLocation(ChannelEnum channel, String uuid) {
        try {

            Location location = locationRepository.findByUuidAndStatusCodeNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.LOCATION_NOT_FOUND)));

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.LOCATION_FOUND, new Object[]{location.getDescription()}),
                    location.getUuid(),
                    modelMapper.map(location, LocationDTO.class));

        } catch (DataNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ListResponseDTO<List<CommonListDTO<LocationDTO>>> findLocations(ChannelEnum channel, SearchRequestDTO searchRequestDTO) {
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


            List<CommonListDTO<LocationDTO>> locations = locationRepository.findAll(locationSpecification.generateSearchCriteria(searchRequestDTO), pageRequest).getContent()
                    .stream()
                    .map(location -> new CommonListDTO<>(location.getUuid(), modelMapper.map(location, LocationDTO.class)))
                    .collect(Collectors.toList());

            Long fullCount = locationRepository.count(locationSpecification.generateSearchCriteria(searchRequestDTO));


            return new ListResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.LOCATION_LIST_FOUND),
                    locations,
                    fullCount);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
