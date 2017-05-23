
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.AdministratorService;
import services.MessageEntityService;
import domain.Administrator;
import domain.MessageEntity;

@Controller
@RequestMapping("/messageEntity")
public class MessageEntityController extends AbstractController {

	private int						producerId;

	// Services -----------------------------------------------

	@Autowired
	private MessageEntityService	messageEntityService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private ActorService			actorService;


	// Constructors -------------------------------------------

	public MessageEntityController() {
		super();
	}

	// Creating -----------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int producerId) {
		ModelAndView result;
		MessageEntity messageEntity;
		final Boolean isAdmin;

		messageEntity = this.messageEntityService.create(producerId);
		this.setProducerId(producerId);
		isAdmin = true;

		result = this.createEditModelAndView(messageEntity);
		result.addObject("isAdmin", isAdmin);

		return result;
	}

	@RequestMapping(value = "/sendToAdmin", method = RequestMethod.GET)
	public ModelAndView sendToAdmin() {
		ModelAndView result;
		MessageEntity messageEntity;
		final int adminId;
		Boolean isAdmin;

		adminId = this.administratorService.findAdministrator().getId();
		messageEntity = this.messageEntityService.create(adminId);
		isAdmin = false;

		result = this.createEditModelAndView(messageEntity);
		result.addObject("isAdmin", isAdmin);

		return result;
	}

	@RequestMapping(value = "/send", method = RequestMethod.POST, params = "send")
	public ModelAndView send(final MessageEntity messageEntity, final BindingResult binding) {
		ModelAndView result;
		MessageEntity reconstructed, saved;
		int adminId;
		Boolean isAdmin;

		isAdmin = false;
		adminId = this.administratorService.findAdministrator().getId();

		if (this.actorService.findByPrincipal() instanceof Administrator)
			isAdmin = true;

		if (this.actorService.findByPrincipal() instanceof Administrator)
			reconstructed = this.messageEntityService.reconstruct(messageEntity, binding, this.getProducerId());
		else
			reconstructed = this.messageEntityService.reconstruct(messageEntity, binding, adminId);

		if (binding.hasErrors())
			result = this.createEditModelAndView(messageEntity);
		else
			try {
				saved = this.messageEntityService.send(reconstructed);
				this.messageEntityService.saveCopy(saved);
				result = new ModelAndView("redirect:/messageEntity/listSent.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(messageEntity, "misc.commit.error");
			}
		result.addObject("isAdmin", isAdmin);

		return result;
	}

	// Listing ------------------------------------------------

	@RequestMapping(value = "/listSent", method = RequestMethod.GET)
	public ModelAndView sentList() {
		final ModelAndView result;
		Collection<MessageEntity> messages;
		Boolean isSent;
		Boolean isAdmin;

		messages = this.messageEntityService.findSentMessages();
		isSent = true;
		isAdmin = false;
		if (this.actorService.findByPrincipal() instanceof Administrator)
			isAdmin = true;

		result = new ModelAndView("messageEntity/list");
		result.addObject("messages", messages);
		result.addObject("isSent", isSent);
		result.addObject("isAdmin", isAdmin);
		result.addObject("requestURI", "messageEntity/listSent.do");

		return result;
	}

	@RequestMapping(value = "/listReceived", method = RequestMethod.GET)
	public ModelAndView listReceived() {
		final ModelAndView result;
		Collection<MessageEntity> messages;
		Boolean isSent;
		Boolean isAdmin;

		messages = this.messageEntityService.findReceivedMessages();
		isSent = false;
		isAdmin = false;
		if (this.actorService.findByPrincipal() instanceof Administrator)
			isAdmin = true;

		result = new ModelAndView("messageEntity/list");
		result.addObject("messages", messages);
		result.addObject("isSent", isSent);
		result.addObject("isAdmin", isAdmin);
		result.addObject("requestURI", "messageEntity/listReceived.do");

		return result;
	}

	// Display -----------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int messageEntityId) {
		ModelAndView result;
		MessageEntity messageEntity;
		Boolean isAdmin, isSent;

		messageEntity = this.messageEntityService.findOne(messageEntityId);
		isAdmin = false;
		isSent = false;

		if (this.actorService.findByPrincipal() instanceof Administrator)
			isAdmin = true;
		if (!messageEntity.getCopy())
			isSent = true;

		result = new ModelAndView("messageEntity/display");
		result.addObject("messageEntity", messageEntity);
		result.addObject("isSent", isSent);
		result.addObject("isAdmin", isAdmin);
		result.addObject("requestURI", "messageEntity/display.do");

		return result;
	}

	// Ancillary methods -------------------------------------

	protected ModelAndView createEditModelAndView(final MessageEntity messageEntity) {
		ModelAndView result;

		result = this.createEditModelAndView(messageEntity, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final MessageEntity messageEntity, final String message) {
		ModelAndView result;

		result = new ModelAndView("messageEntity/create");
		result.addObject("messageEntity", messageEntity);
		result.addObject("message", message);

		return result;
	}

	public int getProducerId() {
		return this.producerId;
	}

	public void setProducerId(final int producerId) {
		this.producerId = producerId;
	}
}
