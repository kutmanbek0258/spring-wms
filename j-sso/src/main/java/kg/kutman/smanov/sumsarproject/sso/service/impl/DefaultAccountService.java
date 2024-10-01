package kg.kutman.smanov.sumsarproject.sso.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

import kg.kutman.smanov.sumsarproject.sso.exception.ServiceException;
import kg.kutman.smanov.sumsarproject.sso.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.UserEntity;
import kg.kutman.smanov.sumsarproject.sso.dao.repository.UserRepository;
import kg.kutman.smanov.sumsarproject.sso.dao.type.StoreType;
import kg.kutman.smanov.sumsarproject.sso.dao.type.UserEventType;
import kg.kutman.smanov.sumsarproject.sso.dto.security.AuthorizedUser;
import kg.kutman.smanov.sumsarproject.sso.dto.FileStoreDto;
import kg.kutman.smanov.sumsarproject.sso.dto.UserDto;
import kg.kutman.smanov.sumsarproject.sso.mapper.AuthorizedUserMapper;
import kg.kutman.smanov.sumsarproject.sso.mapper.UserDtoMapper;
import kg.kutman.smanov.sumsarproject.sso.service.AccountService;
import kg.kutman.smanov.sumsarproject.sso.service.FileStoreService;
import kg.kutman.smanov.sumsarproject.sso.service.UserClientService;
import kg.kutman.smanov.sumsarproject.sso.service.UserEventService;
import kg.kutman.smanov.sumsarproject.sso.service.UserService;
import kg.kutman.smanov.sumsarproject.sso.service.UserTokenService;
import kg.kutman.smanov.sumsarproject.sso.utils.HttpUtils;
import kg.kutman.smanov.sumsarproject.sso.utils.SecurityUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultAccountService implements AccountService {

    private final UserRepository userRepository;
    private final FileStoreService fileStoreService;
    private final SecurityService securityService;
    private final UserTokenService userTokenService;
    private final SecurityContextLogoutHandler securityContextLogoutHandler;
    private final UserClientService userClientService;
    private final UserEventService eventService;
    private final UserService userService;


    @Override
    @Transactional(readOnly = true)
    public UserDto getCurrentUser() {
        AuthorizedUser authorizedUser = SecurityUtils.getAuthUser();
        UserEntity entity = userRepository.getReferenceById(authorizedUser.getId());
        return UserDtoMapper.map(entity);
    }

    @Override
    @Transactional
    public UserDto save(
        UserDto dto,
        MultipartFile avatarFile,
        HttpServletRequest request,
        HttpServletResponse response
    ) {
        Optional<UserEntity> entityWrapper = userRepository.findById(dto.getId());
        if (entityWrapper.isEmpty()) {
            throw ServiceException.builder("Entity not found").build();
        }
        UserEntity entity = entityWrapper.get();

        if (avatarFile != null && !avatarFile.isEmpty()) {
            FileStoreDto fileStoreDto = fileStoreService.saveOrReplace(
                avatarFile,
                StoreType.AVATAR,
                entity.getAvatarFileId()
            );
            entity.setAvatarFileId(fileStoreDto != null ? fileStoreDto.getId() : null);
        }

        entity.setLastName(dto.getLastName());
        entity.setFirstName(dto.getFirstName());
        entity.setMiddleName(dto.getMiddleName());
        entity.setBirthday(dto.getBirthday());
        entity = userRepository.save(entity);

        AuthorizedUser updatedAuthorizedUser = AuthorizedUserMapper.reload(SecurityUtils.getAuthUser(), entity);
        securityService.reloadSecurityContext(updatedAuthorizedUser, request, response);
        return UserDtoMapper.map(entity);
    }

    @Override
    @Transactional
    public void deleteCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        AuthorizedUser authorizedUser = SecurityUtils.getAuthUser();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userTokenService.recallAllCurrentUserTokens();
        userClientService.markAsDelete(authorizedUser.getId());
        fileStoreService.delete(authorizedUser.getAvatarFileId());
        userRepository.deleteById(authorizedUser.getId());
        eventService.createEvent(UserEventType.USER_DELETE, null, request);
        securityContextLogoutHandler.logout(request, response, authentication);
    }

    @Override
    public ResponseEntity<byte[]> getAvatarCurrentUser() {
        AuthorizedUser authorizedUser = SecurityUtils.getAuthUser();
        try {
            UserService.UserAvatar userAvatar = userService.getUserAvatar(authorizedUser.getId());
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
