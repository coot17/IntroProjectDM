package edu.northwestu.intc3283.datasourcestarter.repository;

import edu.northwestu.intc3283.datasourcestarter.entity.Donor;
import edu.northwestu.intc3283.datasourcestarter.reports.*;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface DonorsRepository extends CrudRepository<Donor, Long> {

    List<Donor>  findByFirstNameContainingOrLastNameContaining(String firstName, String lastName);

    List<Donor> findTop10ByOrderByCreatedAtDesc();

    @Query("""
            
                        SELECT
                              d.first_name AS firstName,
                              d.last_name AS lastName,
                              d.email AS email,
                              YEAR(dn.created_at) AS year,
                              MONTH(dn.created_at) AS month,
                              SUM(dn.amount) AS totalDonationAmount
                          FROM
                              donors d
                          JOIN
                              donations dn ON d.id = dn.donor_id
                          GROUP BY
                              d.id,
                              YEAR(dn.created_at),
                              MONTH(dn.created_At)
                          ORDER BY
                              YEAR(dn.created_at) DESC,
                              MONTH(dn.created_at) DESC,
                              SUM(dn.amount) DESC
                        LIMIT :limit
            """)
    List<TopDonationReportDTO> findTopDonors(@Param("limit") Integer limit);

    @Query("""
            SELECT donor.id AS donor_id,
                   donor.first_name,
                   donor.last_name,
                   donor.address1,
                   donor.address2,
                   donor.city,
                   donor.state,
                   donor.zip_code,
                   donation.amount
            FROM donors donor
                    LEFT JOIN (SELECT donor_id,
                                      MIN(id) AS first_donation_id
                               FROM donations
                               GROUP BY donor_id) fd
                                ON donor.id = fd.donor_id
                    LEFT JOIN donations donation
                                ON fd.first_donation_id = donation.id
            WHERE donation.amount > :limitAmount
    """)
    List<JaniceReportDTO> JaniceReport(@Param("limitAmount") Integer limitAmount);

    @Query("""
                SELECT donor.id AS donor_id,
               donor.first_name,
               donor.last_name,
               donor.address1,
               donor.address2,
               donor.city,
               donor.state,
               donor.zip_code,
               donor.phone,
               donor.email
        FROM donors donor
        WHERE donor.address1 = ''
                  OR donor.city = ''
                  OR donor.state = ''
        AND (donor.email is not null or donor.phone is not null)
    """)
    List<LarryMissingAddressDTO> LarryAddresses();

    @Query("""
                SELECT donor.id AS donor_id,
               donor.first_name,
               donor.last_name,
               count(donation.donor_id) as num_donations,
               SUM(donation.amount) as total_donation
        FROM donors donor
            INNER JOIN nu.donations donation
            ON donor.id = donation.donor_id
            WHERE YEAR(donation.created_at) = :year
        GROUP BY donor.id
    """)
    List<LarryYearlyDonationDTO> LarryYearlyDonations(@Param("year") Integer year);

    @Query ("""
            SELECT month(donation.created_at) as month,
           year(donation.created_at) as year,
           sum(donation.amount) as total
    FROM donations donation
    GROUP BY year, month
    """)
    List<JenniferMonthlyDonorsDTO> JenniferMonthlyDonor();

    @Query ("""
            SELECT year(donation.created_at) as year,
           sum(donation.amount) as total
    FROM donations donation
    GROUP BY year
    """)
    List<JenniferYearlyReportDTO> JenniferYearlyReport();

    @Query ("""
            SELECT week(donation.created_at) as week,
           sum(donation.amount) as total
    FROM donations donation
    GROUP BY week
    """)
    List<JenniferWeeklyReportDTO> JenniferWeeklyReport();

    @Query("""
            SELECT d2.first_name,
           d2.last_name,
           year(d.created_at) as year,
           month(d.created_at) as month,
           sum(d.amount) as total
    FROM donations d
    INNER JOIN nu.donors d2 on d.donor_id = d2.id
    WHERE year(d.created_at) = :year and
          month(d.created_at) = :month
    GROUP BY year(d.created_at), month(d.created_at), d.donor_id
    ORDER BY total DESC
    limit 5
    """)
    List<JenniferTop5DTO> JenniferTop5(@Param("year") Integer year, @Param("month") Integer month);
}
