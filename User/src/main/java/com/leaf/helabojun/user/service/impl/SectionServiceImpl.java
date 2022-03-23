package com.leaf.helabojun.user.service.impl;

import com.leaf.helabojun.user.dto.SectionDTO;
import com.leaf.helabojun.user.dto.common.CommonListDTO;
import com.leaf.helabojun.user.dto.common.ResponseDTO;
import com.leaf.helabojun.user.entity.Section;
import com.leaf.helabojun.user.enums.StatusEnum;
import com.leaf.helabojun.user.exception.DataConflictException;
import com.leaf.helabojun.user.exception.DataNotFoundException;
import com.leaf.helabojun.user.repository.SectionRepository;
import com.leaf.helabojun.user.service.SectionService;
import com.leaf.helabojun.user.utility.CommonFunction;
import com.leaf.helabojun.user.utility.MessageConstant;
import com.leaf.helabojun.user.utility.MessageResource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SectionServiceImpl implements SectionService {

    private ModelMapper modelMapper;
    private CommonFunction commonFunction;
    private MessageResource messageResource;

    private SectionRepository sectionRepository;


    @Override
    public ResponseDTO<String,SectionDTO> saveSection(SectionDTO sectionDTO, String username) {
        try{

            Section section = sectionRepository.findByDescriptionAndStatusNot(sectionDTO.getDescription(), StatusEnum.DELETE.getCode())
                    .orElse(new Section());
            if(Objects.nonNull(section.getId()))
                throw new DataConflictException(messageResource.getMessage(MessageConstant.SECTION_EXISTS,new Object[]{sectionDTO.getDescription()}));

            modelMapper.map(sectionDTO,section);
            section.setUuid(commonFunction.getUuid());
            commonFunction.populateInsert(section,username);
            sectionRepository.save(section);

            return new ResponseDTO<>(
                    "",
                    "",
                    messageResource.getMessage(MessageConstant.SECTION_SAVE_SUCCESS,new Object[]{sectionDTO.getDescription()}),
                    section.getUuid(),
                    modelMapper.map(section, SectionDTO.class));

        }
        catch (DataConflictException e){
            log.info(e.getMessage());
            throw e;
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String,SectionDTO> updateSection(String uuid, SectionDTO sectionDTO, String username) {
        try{

            Section section = sectionRepository.findByUuidAndStatusNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(()->new DataNotFoundException(messageResource.getMessage(MessageConstant.SECTION_NOT_FOUND)));

            if(sectionRepository.findByDescriptionAndStatusNot(sectionDTO.getDescription(), StatusEnum.DELETE.getCode()).isPresent())
                throw new DataConflictException(messageResource.getMessage(MessageConstant.SECTION_EXISTS,new Object[]{sectionDTO.getDescription()}));


            modelMapper.map(section,section);
            commonFunction.populateUpdate(section,username);
            sectionRepository.save(section);

            return new ResponseDTO<>(
                    "",
                    "",
                    messageResource.getMessage(MessageConstant.SECTION_UPDATE_SUCCESS,new Object[]{sectionDTO.getDescription()}),
                    section.getUuid(),
                    modelMapper.map(section, SectionDTO.class));

        }
        catch (DataNotFoundException| DataConflictException e){
            log.info(e.getMessage());
            throw e;
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String,SectionDTO> deleteSection(String uuid, String username) {
        try{

            Section section = sectionRepository.findByUuidAndStatusNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(()->new DataNotFoundException(messageResource.getMessage(MessageConstant.SECTION_NOT_FOUND)));

            section.setStatus(StatusEnum.DELETE.getCode());
            commonFunction.populateUpdate(section,username);
            sectionRepository.save(section);

            return new ResponseDTO<>(
                    "",
                    "",
                    messageResource.getMessage(MessageConstant.SECTION_DELETE_SUCCESS,new Object[]{section.getDescription()}),
                    section.getUuid(),
                    modelMapper.map(section, SectionDTO.class));
        }
        catch (DataNotFoundException e){
            log.info(e.getMessage());
            throw e;
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<String,SectionDTO> findSection(String uuid) {
        try{

            Section section = sectionRepository.findByUuidAndStatusNot(uuid, StatusEnum.DELETE.getCode())
                    .orElseThrow(()->new DataNotFoundException(messageResource.getMessage(MessageConstant.SECTION_NOT_FOUND)));

            return new ResponseDTO<>(
                    "",
                    "",
                    messageResource.getMessage(MessageConstant.SECTION_FOUND,new Object[]{section.getDescription()}),
                    section.getUuid(),
                    modelMapper.map(section, SectionDTO.class));

        }
        catch (DataNotFoundException e){
            log.info(e.getMessage());
            throw e;
        }
        catch(Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    @Override
    public ResponseDTO<Long,List<CommonListDTO<SectionDTO>>> finSections() {
        return null;
    }
}
