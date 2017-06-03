
package services;

import javax.transaction.Transactional;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CampaignServiceTest extends AbstractTest {

	// System under test ----------------------------

	@Autowired
	private CampaignService	campaignService;

	// Tests ----------------------------------------

	/*
	 * Use case: A registered producer tries to register a new campaign.
	 * Functional Requirement: Register to the system as a chorbi.
	 */

}
