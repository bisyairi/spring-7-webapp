package guru.springframework.spring7webapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

@JsonDeserialize(builder = Customer.CustomerBuilder.class)
@Builder
@Data
public class Customer {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("customerName")
    private String customerName;

    @JsonProperty("version")
    private Integer version;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
