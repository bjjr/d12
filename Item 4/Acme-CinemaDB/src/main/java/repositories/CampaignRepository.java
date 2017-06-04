
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Campaign;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {

	@Query("select c from Invoice i join i.campaign c")
	Collection<Campaign> findCampaignsInvoices();

	@Query("select c from Invoice i join i.campaign c where i.paid = false")
	Collection<Campaign> findCampaignsUnpaidInvoices();

	@Query("select c from Campaign c where (c.end < Current_Date or c.max = c.timesDisplayed) and c.approved = true")
	Collection<Campaign> findAllCampaignsFinished();

	@Query("select c from Campaign c where ((c.start <= Current_Date and c.end >= Current_Date) and c.max > c.timesDisplayed) and c.approved = true")
	Collection<Campaign> findAllCampaignsUnfinished();

	@Query("select c from Campaign c where c.producer.id = ?1")
	Collection<Campaign> findCampaignsProducer(int producerId);

}
