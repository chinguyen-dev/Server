package com.yody.Server.components;

import com.yody.Server.dto.user.RoleDTO;
import com.yody.Server.dto.user.UserDTO;
import com.yody.Server.entities.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
@Component
@RequiredArgsConstructor
public class MapperComponent {
    private final ModelMapper modelMapper;

    public UserDTO toDto(User user) {
        Set<RoleDTO> roleDTOS = user.getRoles().stream()
                .map(RoleEntity -> modelMapper.map(RoleEntity, RoleDTO.class))
                .collect(Collectors.toSet());
        UserDTO userDto = this.modelMapper.map(user, UserDTO.class);
        userDto.setRole(roleDTOS);
        return userDto;
    }
}
