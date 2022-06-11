package com.leaf.helabojun.user.service.impl;

import com.leaf.helabojun.user.dto.SectionDTO;
import com.leaf.helabojun.user.dto.common.CommonListDTO;
import com.leaf.helabojun.user.dto.common.ListResponseDTO;
import com.leaf.helabojun.user.dto.common.ResponseDTO;
import com.leaf.helabojun.user.dto.common.SearchRequestDTO;
import com.leaf.helabojun.user.entity.Section;
import com.leaf.helabojun.user.enums.ChannelEnum;
import com.leaf.helabojun.user.enums.ResponseEnum;
import com.leaf.helabojun.user.enums.StatusEnum;
import com.leaf.helabojun.user.exception.DataConflictException;
import com.leaf.helabojun.user.exception.DataNotFoundException;
import com.leaf.helabojun.user.repository.SectionRepository;
import com.leaf.helabojun.user.repository.specification.SectionSpecification;
import com.leaf.helabojun.user.service.SectionService;
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
public class SectionServiceImpl implements SectionService {

    private ModelMapper modelMapper;
    private CommonFunction commonFunction;
    private MessageResource messageResource;

    private SectionRepository sectionRepository;
    private SectionSpecification sectionSpecification;


    @Override
    public ResponseDTO<String, SectionDTO> saveSection(ChannelEnum channel, String username, SectionDTO sectionDTO) {
        try {

            Section section = sectionRepository.findByDescriptionAndStatusNot(sectionDTO.getDescription(), StatusEnum.DELETE.getCode())
                    .orElse(new Section());
            if (Objects.nonNull(section.getId()))
                throw new DataConflictException(messageResource.getMessage(MessageConstant.SECTION_EXISTS, new Object[]{sectionDTO.getDescription()}));

            modelMapper.map(sectionDTO, section);
            section.setUuid(commonFunction.getUuid());
            commonFunction.populateInsert(section, username);
            sectionRepository.save(section);

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.SECTION_SAVE_SUCCESS, new Object[]{sectionDTO.getDescription()}),
                    section.getUuid(),
                    modelMapper.map(section, SectionDTO.class));

        } catch (DataConflictException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String, SectionDTO> updateSection(ChannelEnum channel, String uuid, SectionDTO sectionDTO, String username) {
        try {

            Section section = sectionRepository.findByUuidAndStatusNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.SECTION_NOT_FOUND)));

            if (sectionRepository.findByDescriptionAndStatusNot(sectionDTO.getDescription(), StatusEnum.DELETE.getCode()).isPresent())
                throw new DataConflictException(messageResource.getMessage(MessageConstant.SECTION_EXISTS, new Object[]{sectionDTO.getDescription()}));

            String previousSectionDescription = section.getDescription();
            section.setDescription(sectionDTO.getDescription());
            section.setStatus(sectionDTO.getStatus());

            commonFunction.populateUpdate(section, username);
            sectionRepository.save(section);

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.SECTION_UPDATE_SUCCESS, new Object[]{previousSectionDescription,sectionDTO.getDescription()}),
                    section.getUuid(),
                    modelMapper.map(section, SectionDTO.class));

        } catch (DataNotFoundException | DataConflictException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String, SectionDTO> deleteSection(ChannelEnum channel, String uuid, String username) {
        try {

            Section section = sectionRepository.findByUuidAndStatusNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.SECTION_NOT_FOUND)));

            section.setStatus(StatusEnum.DELETE.getCode());
            commonFunction.populateUpdate(section, username);
            sectionRepository.save(section);

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.SECTION_DELETE_SUCCESS, new Object[]{section.getDescription()}),
                    section.getUuid(),
                    modelMapper.map(section, SectionDTO.class));
        } catch (DataNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String, SectionDTO> findSection(ChannelEnum channel, String uuid) {
        try {

            Section section = sectionRepository.findByUuidAndStatusNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(() -> new DataNotFoundException(messageResource.getMessage(MessageConstant.SECTION_NOT_FOUND)));

            return new ResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.SECTION_FOUND, new Object[]{section.getDescription()}),
                    section.getUuid(),
                    modelMapper.map(section, SectionDTO.class));

        } catch (DataNotFoundException e) {
            log.info(e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ListResponseDTO<List<CommonListDTO<SectionDTO>>> findSections(ChannelEnum channel, SearchRequestDTO searchRequestDTO) {
        try {
            PageRequest pageRequest;

            if (Objects.nonNull(Optional.ofNullable(searchRequestDTO.getOrderBy()).orElse(null)) &&
                    Objects.nonNull(Optional.ofNullable(searchRequestDTO.getOrder()).orElse(null)))
                pageRequest = PageRequest.of(
                        Optional.ofNullable(searchRequestDTO.getStart()).orElse(Integer.MIN_VALUE), Optional.ofNullable(searchRequestDTO.getLimit()).orElse(Integer.MAX_VALUE),
                        Sort.by(Sort.Direction.valueOf(searchRequestDTO.getOrder()), searchRequestDTO.getOrderBy())
                );
            else
                pageRequest = PageRequest.of(Optional.ofNullable(searchRequestDTO.getStart()).orElse(Integer.MIN_VALUE), Optional.ofNullable(searchRequestDTO.getLimit()).orElse(Integer.MAX_VALUE));


            List<CommonListDTO<SectionDTO>> sections = sectionRepository.findAll(sectionSpecification.generateSearchCriteria(searchRequestDTO), pageRequest).getContent()
                    .stream()
                    .map(section -> new CommonListDTO<>(section.getUuid(), modelMapper.map(section, SectionDTO.class)))
                    .collect(Collectors.toList());

            Long fullCount = sectionRepository.count(sectionSpecification.generateSearchCriteria(searchRequestDTO));


            return new ListResponseDTO<>(
                    channel,
                    ResponseEnum.SUCCESS,
                    messageResource.getMessage(MessageConstant.SECTION_LIST_FOUND),
                    sections,
                    fullCount);

        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw ex;
        }
    }
}
