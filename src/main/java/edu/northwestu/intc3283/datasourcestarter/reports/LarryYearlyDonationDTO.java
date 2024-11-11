package edu.northwestu.intc3283.datasourcestarter.reports;

import java.math.BigDecimal;

public class LarryYearlyDonationDTO {
    private int donor_id;
    private String first_name;
    private String last_name;
    private int num_donations;
    private BigDecimal total_donation;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public int getDonor_id() {
        return donor_id;
    }

    public void setDonor_id(int donor_id) {
        this.donor_id = donor_id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getNum_donations() {
        return num_donations;
    }

    public void setNum_donations(int num_donations) {
        this.num_donations = num_donations;
    }

    public BigDecimal getTotal_donation() {
        return total_donation;
    }

    public void setTotal_donation(BigDecimal total_donation) {
        this.total_donation = total_donation;
    }
}
