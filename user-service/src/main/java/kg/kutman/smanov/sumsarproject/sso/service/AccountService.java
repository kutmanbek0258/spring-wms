package kg.kutman.smanov.sumsarproject.sso.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import kg.kutman.smanov.sumsarproject.sso.dto.UserDto;

public interface AccountService {

    UserDto getCurrentUser();

    UserDto save(UserDto dto, MultipartFile avatarFile, HttpServletRequest request, HttpServletResponse response);

    void deleteCurrentUser(HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<byte[]> getAvatarCurrentUser();
}
