package com.example.restreword.service;

import com.example.restreword.dto.SettingDTO;
import com.example.restreword.entity.Setting;
import com.example.restreword.repo.SettingRepository;
import com.example.restreword.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class SettingService {
    private final SettingRepository settingRepository;
    private final UserRepository userRepository;

    public List<SettingDTO> findAll(){
        return settingRepository.findAll()
                .stream()
                .map(setting -> mapToDTO(setting, new SettingDTO()))
                .collect(Collectors.toList());
    }

    private SettingDTO mapToDTO(final Setting setting, final SettingDTO settingDTO) {
        settingDTO.setId(setting.getId());
        settingDTO.setKey(setting.getKey());
        return settingDTO;
    }
}
