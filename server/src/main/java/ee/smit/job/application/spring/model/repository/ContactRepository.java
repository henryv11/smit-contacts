package ee.smit.job.application.spring.model.repository;

import ee.smit.job.application.spring.model.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    Page<Contact> findAllByUserId(Long userId, Pageable pageable);

}
