package ee.smit.job.application.spring.model.service;

import ee.smit.job.application.spring.model.entity.Contact;
import ee.smit.job.application.spring.model.entity.User;
import ee.smit.job.application.spring.model.payload.PagedResponse;
import ee.smit.job.application.spring.model.repository.ContactRepository;
import ee.smit.job.application.spring.model.repository.UserRepository;
import ee.smit.job.application.spring.security.exception.BadRequestException;
import ee.smit.job.application.spring.security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Primary
@Service
@Transactional
public class ContactService {

    private static final int MAX_PAGE_SIZE = 40;

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private UserRepository userRepository;

    public Contact deleteContact(Long contactId) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("contact", "id", contactId));
        contactRepository.delete(contact);
        return contact;
    }

    public Contact editContact(Long contactId, String name, String codename, String phoneNumber) {
        Contact contact = contactRepository.findById(contactId)
                .orElseThrow(() -> new ResourceNotFoundException("contact", "id", contactId));
        contact.setName(name);
        contact.setCodename(codename);
        contact.setPhoneNumber(phoneNumber);
        return contactRepository.save(contact);
    }

    public Contact createContact(Long userId, String name, String codename, String phoneNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
        return contactRepository.save(new Contact(name, codename, phoneNumber, user));
    }

    public Page<Contact> getUserContacts(Long userId, int page, int size, Sort.Direction direction, String... properties) {
        validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, direction, properties);
        return contactRepository.findAllByUserId(userId, pageable);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if(page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }
        if(size > MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + MAX_PAGE_SIZE);
        }
    }
}
