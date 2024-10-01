package kg.kutman.smanov.sumsarproject.sso.service.impl;

import java.util.UUID;

import kg.kutman.smanov.sumsarproject.sso.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import kg.kutman.smanov.sumsarproject.sso.service.ResourceServerService;
import kg.kutman.smanov.sumsarproject.sso.service.UserService;
import kg.kutman.smanov.sumsarproject.sso.utils.HttpUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultResourceServerService implements ResourceServerService {

    private final UserService userService;

    @Override
    public ResponseEntity<byte[]> getUserAvatar(UUID userId) {
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
