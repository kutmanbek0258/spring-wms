package kg.kutman.smanov.sumsarproject.sso.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.UserClient;
import kg.kutman.smanov.sumsarproject.sso.dao.repository.UserClientRepository;
import kg.kutman.smanov.sumsarproject.sso.service.UserClientService;

@Service
@RequiredArgsConstructor
public class DefaultUserClientService implements UserClientService {

    private final UserClientRepository userClientRepository;

    @Override
    @Transactional
    public void save(UUID userId, String clientId) {
        if (userId == null || clientId == null) {
            return;
        }
        UserClient userClient = userClientRepository.findByUserIdAndClientId(userId, clientId);
        if (userClient == null) {
            userClient = new UserClient();
            userClient.setUserId(userId);
            userClient.setClientId(clientId);
            userClient.setDeleted(false);
        }
        userClientRepository.save(userClient);
    }

    @Override
    @Transactional
    public void markAsDelete(UUID userId) {
        List<UserClient> userClients = userClientRepository.findByUserId(userId);
        for (UserClient entity : userClients) {
            entity.setDeleted(true);
        }
        userClientRepository.saveAll(userClients);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Set<UUID>> getDeletedClientsAndUserIds() {
        List<UserClient> userClients = userClientRepository.findAllByDeletedIsTrue();
        if (userClients == null || userClients.isEmpty()) {
            return Map.of();
        }
        return userClients.stream()
            .collect(Collectors.groupingBy(
                UserClient::getClientId,
                Collectors.mapping(UserClient::getUserId, Collectors.toSet())
            ));
    }

    @Override
    @Transactional
    public void deleteRowsMarkedDeleted() {
        userClientRepository.deleteAllByDeletedIsTrue();
    }
}
