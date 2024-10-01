package kg.kutman.smanov.sumsarproject.sso.service;

import java.util.UUID;
import org.springframework.http.ResponseEntity;
import kg.kutman.smanov.sumsarproject.sso.dto.AdminUserDto;
import kg.kutman.smanov.sumsarproject.sso.dto.PageableResponseDto;

public interface AdminUserService {

    PageableResponseDto<AdminUserDto> searchUsers(int page, int pageSize, String email);

    void assignAdmin(String email);

    void dismissAdmin(UUID userId);

    ResponseEntity<byte[]> getAvatar(UUID avatarFileId);
}
