package ee.aktors.task.currency.rates.app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CurrencyDTO {

    private Long id;

    @NotNull(message = "Can't be null")
    @Size(min = 1, max = 10, message = "Size min 1, max 10")
    private String code;

    @NotNull(message = "Can't be null")
    @Size(min = 1, max = 25, message = "Size min 1, max 25")
    private String name;

    @NotNull(message = "Can't be null")
    @DecimalMin(value = "0", message = "Can't be less than zero")
    private BigDecimal rate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateDate;

}
