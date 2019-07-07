package ee.smit.job.application.spring.model.repository;

import ee.smit.job.application.spring.model.enums.RoleName;
import ee.smit.job.application.spring.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);

}
