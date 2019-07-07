package ee.smit.job.application.spring.model.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {

    private Long id;
    private String name;
    private String codename;
    private String phoneNumber;

}
