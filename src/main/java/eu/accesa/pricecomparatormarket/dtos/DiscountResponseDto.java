package eu.accesa.pricecomparatormarket.dtos;

import java.time.LocalDate;

public class DiscountResponseDto {
    private Integer percentageOfDiscount = 0;
    private LocalDate fromDate;
    private LocalDate toDate;

    public void setPercentageOfDiscount(Integer percentageOfDiscount) {
        this.percentageOfDiscount = percentageOfDiscount;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Integer getPercentageOfDiscount() {
        return percentageOfDiscount;
    }

    public LocalDate getRomDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }
}
