
package controllers.administrator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ChapterService;
import services.InvoiceService;
import services.MovieService;
import services.OrderUserService;
import services.ReviewService;
import services.SeasonService;
import services.ShippingAddressService;
import services.TvShowService;
import controllers.AbstractController;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController extends AbstractController {

	// Supporting services -----------------------------------------------------------

	@Autowired
	private ChapterService			chapterService;

	@Autowired
	private InvoiceService			invoiceService;

	@Autowired
	private MovieService			movieService;

	@Autowired
	private OrderUserService		orderUserService;

	@Autowired
	private ReviewService			reviewService;

	@Autowired
	private SeasonService			seasonService;

	@Autowired
	private ShippingAddressService	shippingAddressService;

	@Autowired
	private TvShowService			tvShowService;


	// Constructors -----------------------------------------------------------

	public DashboardAdministratorController() {
		super();
	}

	// Dashboard -----------------------------------------------------------

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView dashboard() {

		final ModelAndView result;
		final Long minMoviesProducer;
		final Double avgMoviesProducer;
		final Long maxMoviesProducer;
		final List<Integer[]> numberOfMoviesPerType;
		final List<Integer[]> numberOfTvShowsPerType;
		final Double minTotalMoneyInvoices;
		final Double avgTotalMoneyInvoices;
		final Double maxTotalMoneyInvoices;
		final Double avgTotalMoneyOrdersFinished;
		final Double avgShippingAddressPerUser;
		final Long minOrderUserPerUser;
		final Double avgOrderUserPerUser;
		final Long maxOrderUserPerUser;
		final Double percentageOrdersInProgress;
		final Double percentageOrdersSent;
		final Double percentageOrdersCancelled;
		final Long minReviewCritic;
		final Double avgReviewCritic;
		final Long maxReviewCritic;
		final Double avgRatingMovies;
		final Double avgRatingTvShows;
		final Double avgChaptersPerSeason;
		final Long maxChaptersPerSeason;
		final Double avgSeasonsPerTvShow;
		final Long maxSeasonsPerTvShow;

		result = new ModelAndView("administrator/dashboard");
		minMoviesProducer = this.movieService.findMinMoviesProducer();
		avgMoviesProducer = this.movieService.findAvgMoviesProducer();
		maxMoviesProducer = this.movieService.findMaxMoviesProducer();
		numberOfMoviesPerType = this.movieService.findNumberOfMoviesPerType();
		numberOfTvShowsPerType = this.tvShowService.findNumberOfTvShowsPerType();
		minTotalMoneyInvoices = this.invoiceService.findMinTotalMoneyInvoices();
		avgTotalMoneyInvoices = this.invoiceService.findAvgTotalMoneyInvoices();
		maxTotalMoneyInvoices = this.invoiceService.findMaxTotalMoneyInvoices();
		avgTotalMoneyOrdersFinished = this.orderUserService.findAvgTotalMoneyOrdersFinished();
		avgShippingAddressPerUser = this.shippingAddressService.findAvgShippingAddressPerUser();
		minOrderUserPerUser = this.orderUserService.findMinOrderUserPerUser();
		avgOrderUserPerUser = this.orderUserService.findAvgOrderUserPerUser();
		maxOrderUserPerUser = this.orderUserService.findMaxOrderUserPerUser();
		percentageOrdersInProgress = this.orderUserService.findPercentageOrdersInProgress();
		percentageOrdersSent = this.orderUserService.findPercentageOrdersSent();
		percentageOrdersCancelled = this.orderUserService.findPercentageOrdersCancelled();
		minReviewCritic = this.reviewService.findMinReviewCritic();
		avgReviewCritic = this.reviewService.findAvgReviewCritic();
		maxReviewCritic = this.reviewService.findMaxReviewCritic();
		avgRatingMovies = this.reviewService.findAvgRatingMovies();
		avgRatingTvShows = this.reviewService.findAvgRatingTvShows();
		avgChaptersPerSeason = this.chapterService.findAvgChaptersPerSeason();
		maxChaptersPerSeason = this.chapterService.findMaxChaptersPerSeason();
		avgSeasonsPerTvShow = this.seasonService.findAvgSeasonsPerTvShow();
		maxSeasonsPerTvShow = this.seasonService.findMaxSeasonsPerTvShow();

		result.addObject("minMoviesProducer", minMoviesProducer);
		result.addObject("avgMoviesProducer", avgMoviesProducer);
		result.addObject("maxMoviesProducer", maxMoviesProducer);
		result.addObject("numberOfMoviesPerType", numberOfMoviesPerType);
		result.addObject("numberOfTvShowsPerType", numberOfTvShowsPerType);
		result.addObject("minTotalMoneyInvoices", minTotalMoneyInvoices);
		result.addObject("avgTotalMoneyInvoices", avgTotalMoneyInvoices);
		result.addObject("maxTotalMoneyInvoices", maxTotalMoneyInvoices);
		result.addObject("avgTotalMoneyOrdersFinished", avgTotalMoneyOrdersFinished);
		result.addObject("avgShippingAddressPerUser", avgShippingAddressPerUser);
		result.addObject("minOrderUserPerUser", minOrderUserPerUser);
		result.addObject("avgOrderUserPerUser", avgOrderUserPerUser);
		result.addObject("maxOrderUserPerUser", maxOrderUserPerUser);
		result.addObject("percentageOrdersInProgress", percentageOrdersInProgress);
		result.addObject("percentageOrdersSent", percentageOrdersSent);
		result.addObject("percentageOrdersCancelled", percentageOrdersCancelled);
		result.addObject("minReviewCritic", minReviewCritic);
		result.addObject("avgReviewCritic", avgReviewCritic);
		result.addObject("maxReviewCritic", maxReviewCritic);
		result.addObject("avgRatingMovies", avgRatingMovies);
		result.addObject("avgRatingTvShows", avgRatingTvShows);
		result.addObject("avgChaptersPerSeason", avgChaptersPerSeason);
		result.addObject("maxChaptersPerSeason", maxChaptersPerSeason);
		result.addObject("avgSeasonsPerTvShow", avgSeasonsPerTvShow);
		result.addObject("maxSeasonsPerTvShow", maxSeasonsPerTvShow);

		return result;

	}

}
