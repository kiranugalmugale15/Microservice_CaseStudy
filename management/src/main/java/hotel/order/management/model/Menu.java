package hotel.order.management.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table
public class Menu {
    @Id
    private Long id;
    private String name;
    private Boolean isVeg;
    private Double price;
    private String description;
}

