package com.ktaksv.ekb.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ktaksv.ekb.model.UserModel;
import com.ktaksv.ekb.model.dto.UserModelAddDto;
import com.ktaksv.ekb.model.dto.UserModelFilterDto;
import com.ktaksv.ekb.model.dto.UserModelResponseDto;
import com.ktaksv.ekb.model.history.EntityType;
import com.ktaksv.ekb.model.history.HistoryAction;
import com.ktaksv.ekb.model.history.HistoryLog;
import com.ktaksv.ekb.model.mapper.CustomUserModelMapper;
import com.ktaksv.ekb.model.mapper.UserModelMapper;
import com.ktaksv.ekb.repository.CustomUserModelRepository;
import com.ktaksv.ekb.repository.HistoryLogRepository;
import com.ktaksv.ekb.repository.UserModelRepository;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserModelService {

    private final UserModelRepository repository;
    private final HistoryLogRepository historyLogRepository;
    private final CustomUserModelMapper mapper;
    private final UserModelMapper baseMapper;
    private final ObjectMapper objectMapper;
    private final CustomUserModelRepository customRepository;

    private final IAuthenticationFacade facade;

    public UserModelResponseDto getOne(Long id) throws NotFoundException {
        return baseMapper.mapToResponse(repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found.")));
    }

    public Page<UserModelResponseDto> getAll(int page, int pageSize) {
        return repository
                .findAll(PageRequest.of(page < 1 ? 0 : page - 1, pageSize <= 0 ? 50 : pageSize,
                        Sort.by(Sort.Direction.ASC, "fio"))).map(baseMapper::mapToResponse);
    }

    @Transactional(rollbackFor = {Exception.class})
    public UserModelResponseDto save(UserModelAddDto dto) {
        UserModel save = repository.saveAndFlush(mapper.mapToEntity(dto));
        historyLogRepository.save(HistoryLog.builder()
                .date(LocalDateTime.now())
                .entityId(save.getId())
                .fields(objectMapper.convertValue(save, Map.class))
                .historyAction(HistoryAction.SAVE)
                .entityType(EntityType.USER_MODEL)
                .initiator(facade.getAuthentication().getName())
                .build());
        return baseMapper.mapToResponse(save);
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean delete(Long id) throws NotFoundException {
        try {
            repository.deleteById(id);
            historyLogRepository.save(HistoryLog.builder()
                    .date(LocalDateTime.now())
                    .entityId(id)
                    .fields(null)
                    .historyAction(HistoryAction.DELETE)
                    .entityType(EntityType.USER_MODEL)
                    .initiator(facade.getAuthentication().getName())
                    .build());
        } catch (Exception e) {
            throw new NotFoundException("User with id: " + id + " not found.");
        }
        return true;
    }

    @Transactional(rollbackFor = {Exception.class})
    public boolean enable(Long id, boolean enable) throws NotFoundException {
            UserModel model = repository.findById(id).orElseThrow(
                    () -> new NotFoundException("User with id: " + id + " not found.")
            );
        model.setEnabled(enable);
        repository.flush();
        Map<Object, Object> fields = new HashMap<>();
        fields.put("enabled", enable);
        historyLogRepository.save(HistoryLog.builder()
                .date(LocalDateTime.now())
                .entityId(id)
                .fields(fields)
                .historyAction(HistoryAction.ENABLE)
                .entityType(EntityType.USER_MODEL)
                .initiator(facade.getAuthentication().getName())
                .build());
        return enable;
    }

    @Transactional(rollbackFor = {Exception.class})
    public UserModelResponseDto update(Long id, Object entity) throws Exception {
        UserModel userModel = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id: " + id + " not found."));
        String fieldsUpd = objectMapper.writeValueAsString(entity);
        objectMapper.readerForUpdating(userModel).readValue(fieldsUpd);
        repository.saveAndFlush(userModel);

        historyLogRepository.save(HistoryLog.builder()
                .date(LocalDateTime.now())
                .entityId(id)
                .fields(entity)
                .historyAction(HistoryAction.UPDATE)
                .entityType(EntityType.USER_MODEL)
                .initiator(facade.getAuthentication().getName())
                .build());
        return baseMapper.mapToResponse(userModel);
    }

    @Transactional(rollbackFor = {Exception.class})
    public Page<UserModelResponseDto> findAllFiltered(int page, int pageSize, UserModelFilterDto dto) throws Exception {
        return customRepository.findAllFiltered(PageRequest.of(page < 1 ? 0 : page - 1, pageSize <= 0 ? 50 : pageSize), dto)
                .map(baseMapper::mapToResponse);
    }

    public UserModelResponseDto getMe() {
        Authentication authentication = facade.getAuthentication();
        UserModel user = (UserModel) authentication.getPrincipal();
        return baseMapper.mapToResponse(user);
    }
}