package kg.kutman.smanov.sumsarproject.sso.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import kg.kutman.smanov.sumsarproject.sso.dao.entity.SystemOauth2Client;

public interface SystemOAuth2ClientRepository
    extends JpaRepository<SystemOauth2Client, String>, JpaSpecificationExecutor<SystemOauth2Client> {

    SystemOauth2Client getByClientId(String clientId);
}
