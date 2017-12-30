package com.packtpub.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.packtpub.contract.Reservation;
import com.packtpub.model.Enum.CustomerType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Customers")
public class Customer {

    @Id
    private String customerId;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    private CustomerType customerType;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @ApiModelProperty(hidden = true)
    private List<Reservation> reservations;
}