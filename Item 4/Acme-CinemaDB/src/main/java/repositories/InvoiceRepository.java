
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Invoice;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {

	@Query("select i from Invoice i join i.campaign c where c.producer.id = ?1")
	Collection<Invoice> findInvoicesProducer(int ProducerId);

	@Query("select i from Invoice i join i.campaign c where c.id = ?1")
	Invoice findInvoiceCampaign(int campaignId);

	@Query("select min(i.total) from Invoice i where i.paid = true")
	Double findMinTotalMoneyInvoices();

	@Query("select avg(i.total) from Invoice i where i.paid = true")
	Double findAvgTotalMoneyInvoices();

	@Query("select max(i.total) from Invoice i where i.paid = true")
	Double findMaxTotalMoneyInvoices();

}
