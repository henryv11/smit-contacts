package ee.smit.job.application.spring.controller;

import ee.smit.job.application.spring.model.entity.Contact;
import ee.smit.job.application.spring.model.payload.*;
import ee.smit.job.application.spring.model.service.ContactService;
import ee.smit.job.application.spring.security.annotation.CurrentUser;
import ee.smit.job.application.spring.security.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createContact(@CurrentUser UserPrincipal currentUser,
                                           @Valid @RequestBody ContactRequest contactRequest) {
        Contact contact = contactService.createContact(
                currentUser.getId(), contactRequest.getName(),
                contactRequest.getCodename(), contactRequest.getPhoneNumber()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{contactId}")
                .buildAndExpand(contact.getId())
                .toUri();

        return ResponseEntity.created(location).body(new ContactResponse(true, contact)
        );
    }

    @PostMapping("/edit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> editContact(@Valid @RequestBody ContactRequest contactRequest) {
        return ResponseEntity.ok(
                new ContactResponse(true,
                        contactService.editContact(
                            contactRequest.getId(), contactRequest.getName(),
                            contactRequest.getCodename(), contactRequest.getPhoneNumber())
                )
        );
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteContact(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ContactResponse(true,
                        contactService.deleteContact(id))
        );
    }

    @GetMapping("/{page}/{size}/{direction}/{field}")
    @PreAuthorize("hasRole('USER')")
    public PagedResponse<Contact> getContacts(@CurrentUser UserPrincipal currentUser,
                                              @PathVariable int page, @PathVariable int size,
                                              @PathVariable String direction, @PathVariable String field) {
        return new PagedResponse<>(contactService.getUserContacts(
                currentUser.getId(), page, size,
                Sort.Direction.fromString(direction), field
        ));
    }

    @GetMapping("/{page}/{size}")
    @PreAuthorize("hasRole('USER')")
    public PagedResponse<Contact> getContacts(@CurrentUser UserPrincipal currentUser,
                                              @PathVariable int page, @PathVariable int size) {

        return new PagedResponse<>(contactService.getUserContacts(
                currentUser.getId(), page, size,
                Sort.Direction.DESC, "createdAt"
        ));
    }
}
