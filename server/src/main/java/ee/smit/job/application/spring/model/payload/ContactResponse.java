package ee.smit.job.application.spring.model.payload;

import ee.smit.job.application.spring.model.entity.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {

    private boolean success;
    private Contact contact;

}
