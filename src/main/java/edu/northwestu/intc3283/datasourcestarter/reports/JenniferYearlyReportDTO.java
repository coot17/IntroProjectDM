package edu.northwestu.intc3283.datasourcestarter.reports;

import java.math.BigInteger;

public class JenniferYearlyReportDTO {
    private int year;
    private BigInteger total;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }
}
