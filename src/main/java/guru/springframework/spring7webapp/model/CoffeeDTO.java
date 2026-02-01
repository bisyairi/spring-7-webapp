package guru.springframework.spring7webapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonDeserialize(builder = CoffeeDTO.CoffeeDTOBuilder.class)
@Builder
@Data
public class CoffeeDTO {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;

    @NotBlank
    @NotNull
    @Size(max = 50)
    @Column(length = 50)
    @JsonProperty("coffeeName")
    private String coffeeName;

    @JsonProperty("coffeeStyle")
    private CoffeeStyle coffeeStyle;

    @JsonProperty("coffeeDetail")
    private String coffeeDetail;

    @JsonProperty("upc")
    private String upc;

    @JsonProperty("quantityOnHand")
    private Integer quantityOnHand;

    @JsonProperty("price")
    private BigDecimal price;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}