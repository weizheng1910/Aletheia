package core.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
public class Insider {
    @JsonProperty("Id")
    private String id;
    @JsonProperty("FromFiling")
    private String fromfiling;
    @JsonProperty("EntryType")
    private Integer entryType;
    @JsonProperty("QuantityOwnedFollowingTransaction")
    private BigDecimal quantityOwnedFollowingTransaction;
    @JsonProperty("DirectIndirect")
    private Integer directIndirect;
    @JsonProperty("SecurityTitle")
    private String securityTitle;
    @JsonProperty("SecurityType")
    private Integer securityType;
    @JsonProperty("AcquiredDisposed")
    private Integer acquiredDisposed;
    @JsonProperty("Quantity")
    private Integer quantity;
    @JsonProperty("PricePerSecurity")
    private Integer pricePerSecurity;
    @JsonProperty("TransactionDate")
    private LocalDate transactionDate;
    @JsonProperty("TransactionCode")
    private Integer transactionCode;
    @JsonProperty("ConversionOrExercisePrice")
    private BigDecimal conversionOrExercisePrice;
    @JsonProperty("ExercisableDate")
    private LocalDate exercisableDate;
    @JsonProperty("ExpirationDate")
    private LocalDate expirationDate;
    @JsonProperty("UnderlyingSecurityTitle")
    private String underlyingSecurityTitle;
    @JsonProperty("GetUnderlyingSecurityQuantity")
    private String getUnderlyingSecurityQuantity;

    @Override
    public String toString(){
        return "Id: " + id + ", \n" +
                "fromFiling: " + fromfiling + ", \n" +
                "quantity: " + quantity + ", \n" +
                "quantityOwnedFollowingTransaction: " + quantityOwnedFollowingTransaction + ", \n" +
                "securityTitle: " + securityTitle + ", \n" +
                "transactionDate: " + transactionDate + ", \n";
    }
}
