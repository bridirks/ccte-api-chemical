package gov.epa.ccte.api.chemical.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "api_keys", schema = "ms")
public class ApiKey {
    @Id
    @Column(name = "api_key", nullable = false)
    private UUID id;

    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Size(max = 200)
    @Column(name = "reference", length = 200)
    private String reference;

    @Column(name = "created_on")
    private LocalDate createdOn;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

}