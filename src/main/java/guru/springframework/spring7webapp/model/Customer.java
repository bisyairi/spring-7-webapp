package guru.springframework.spring7webapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Builder
@Data
public class Customer {

    private Integer id;
    private String customerName;
    private Integer version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
