package kg.kutman.smanov.sumsarproject.sso.service.impl;

import jakarta.persistence.criteria.Predicate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import kg.kutman.smanov.sumsarproject.sso.exception.InformationException;
import kg.kutman.smanov.sumsarproject.sso.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.RoleEntity;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.UserEntity;
import kg.kutman.smanov.sumsarproject.sso.dao.repository.RoleRepository;
import kg.kutman.smanov.sumsarproject.sso.dao.repository.UserRepository;
import kg.kutman.smanov.sumsarproject.sso.dto.AdminUserDto;
import kg.kutman.smanov.sumsarproject.sso.dto.PageableResponseDto;
import kg.kutman.smanov.sumsarproject.sso.mapper.UserDtoMapper;
import kg.kutman.smanov.sumsarproject.sso.service.AdminUserService;
import kg.kutman.smanov.sumsarproject.sso.service.UserService;
import kg.kutman.smanov.sumsarproject.sso.utils.HttpUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAdminUserService implements AdminUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserService userService;

    @Override
    @Transactional(readOnly = true)
    public PageableResponseDto<AdminUserDto> searchUsers(int page, int pageSize, String email) {
        PageRequest pageRequest = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.DESC, "creationDate"));
        Specification<UserEntity> specification = this.getSearchSpecification(email);
        Page<UserEntity> entitiesPage = userRepository.findAll(specification, pageRequest);
        List<AdminUserDto> dtoList = entitiesPage.get().map(UserDtoMapper::mapAdmin).toList();

        return PageableResponseDto.build(
            dtoList,
            page < entitiesPage.getTotalPages(),
            entitiesPage.getTotalElements()
        );
    }

    private Specification<UserEntity> getSearchSpecification(String email) {
        return (root, query, builder) -> {
            Predicate predicate = builder.isTrue(root.get("admin"));

            if (StringUtils.isNotEmpty(email)) {
                predicate = builder.and(builder.like(
                    builder.lower(root.get("email")),
                    "%" + email.toLowerCase() + "%"
                ));
            }
            return predicate;
        };
    }

    @Override
    @Transactional
    public void assignAdmin(String email) {
        UserEntity entity = userRepository.findByEmail(email);
        if (entity == null) {
            throw InformationException.builder("$user.not.found").build();
        }
        if (entity.getAdmin()) {
            throw InformationException.builder("$user.already.admin").build();
        }

        RoleEntity adminRole = roleRepository.getAdminRole();
        entity.getRoles().add(adminRole);
        entity.setAdmin(true);
        userRepository.save(entity);
    }

    @Override
    @Transactional
    public void dismissAdmin(UUID userId) {
        UserEntity entity = userRepository.getReferenceById(userId);
        if (entity.getSuperuser()) {
            throw InformationException.builder("$forbid.dismiss.superuser").build();
        }
        entity.setAdmin(false);
        entity.setRoles(
            entity.getRoles().stream()
                .filter(item -> !item.getCode().equals("ADMIN_SSO"))
                .collect(Collectors.toList())
        );
        userRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<byte[]> getAvatar(UUID userId) {
        try {
            UserService.UserAvatar userAvatar = userService.getUserAvatar(userId);
            return HttpUtils.appendFileToResponse(
                userAvatar.storeDto().getId().toString(),
                userAvatar.storeDto().getContentType(),
                userAvatar.avatar()
            );
        } catch (ServiceException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
