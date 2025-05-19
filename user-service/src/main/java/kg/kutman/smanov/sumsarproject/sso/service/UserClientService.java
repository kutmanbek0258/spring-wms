package kg.kutman.smanov.sumsarproject.sso.service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface UserClientService {

    void save(UUID userId, String clientId);

    /**
     * Пометить пользователя в таблице user_clients как удалённого.
     */
    void markAsDelete(UUID userId);

    /**
     * Получить ассоциативный список ключом которого является идентификатор OAuth2 клиента,
     * а значением список удалённых пользователей.
     */
    Map<String, Set<UUID>> getDeletedClientsAndUserIds();

    /**
     * Удалить записи в таблице user_clients, которые помечены как удалённые.
     */
    void deleteRowsMarkedDeleted();
}
