package kg.kutman.smanov.sumsarproject.sso.mapper;

import lombok.experimental.UtilityClass;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.UserEntity;
import kg.kutman.smanov.sumsarproject.sso.dto.AdminUserDto;
import kg.kutman.smanov.sumsarproject.sso.dto.UserDto;

@UtilityClass
public class UserDtoMapper {

    public UserDto map(UserEntity entity) {
        return UserDto.builder()
            .id(entity.getId())
            .email(entity.getEmail())
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .middleName(entity.getMiddleName())
            .birthday(entity.getBirthday())
            .avatarFileId(entity.getAvatarFileId())
            .registrationDate(entity.getCreationDate().toLocalDate())
            .build();
    }

    public AdminUserDto mapAdmin(UserEntity entity) {
        return AdminUserDto.builder()
            .id(entity.getId())
            .email(entity.getEmail())
            .firstName(entity.getFirstName())
            .lastName(entity.getLastName())
            .middleName(entity.getMiddleName())
            .birthday(entity.getBirthday())
            .registrationDate(entity.getCreationDate().toLocalDate())
            .superuser(Boolean.TRUE.equals(entity.getSuperuser()))
            .build();
    }
}
