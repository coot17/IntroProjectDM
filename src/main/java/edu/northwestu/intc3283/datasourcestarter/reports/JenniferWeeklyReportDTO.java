package edu.northwestu.intc3283.datasourcestarter.reports;

import java.math.BigInteger;

public class JenniferWeeklyReportDTO {
    private int week;
    private BigInteger total;

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public BigInteger getTotal() {
        return total;
    }

    public void setTotal(BigInteger total) {
        this.total = total;
    }
}
