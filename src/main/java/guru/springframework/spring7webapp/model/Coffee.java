package guru.springframework.spring7webapp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonDeserialize(builder = Coffee.CoffeeBuilder.class)
@Builder
@Data
public class Coffee {

    @JsonProperty("id")
    private UUID id;

    @JsonProperty("version")
    private Integer version;

    @JsonProperty("coffeeName")
    private String coffeeName;

    @JsonProperty("coffeeStyle")
    private CoffeeStyle coffeeStyle;

    @JsonProperty("upc")
    private String upc;

    @JsonProperty("quantityOnHand")
    private Integer quantityOnHand;

    @JsonProperty("price")
    private BigDecimal price;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}