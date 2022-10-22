package kz.bitlab.Magazine.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KorzinaDto {
    private int amonutProducts;
    private double sum;
    private List<KorzinaDetailsDto> korzinaDetails = new ArrayList<>();

    public void aggregate () {
        this.amonutProducts=korzinaDetails.size();
        this.sum=korzinaDetails.stream()
                .map(KorzinaDetailsDto::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();
    }
}
