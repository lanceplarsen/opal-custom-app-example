package dev.opal.example.custom.integration.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import dev.opal.example.custom.integration.repository.GroupRepository;
import dev.opal.example.custom.integration.repository.ResourceRepository;
import dev.opal.example.custom.integration.repository.UserRepository;

@Controller
public class HomeController {

	private final ResourceRepository resourceRepository;
	private final UserRepository userRepository;
	private final GroupRepository groupRepository;

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	public HomeController(ResourceRepository resourceRepository, UserRepository userRepository,
			GroupRepository groupRepository) {
		this.resourceRepository = resourceRepository;
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
	}

	@GetMapping("/")
	public String showUsersResources(Model model) {
		try {
			model.addAttribute("users", userRepository.findAll());
			model.addAttribute("resources", resourceRepository.findAll());
			model.addAttribute("groups", groupRepository.findAll());
		} catch (Exception e) {
			logger.error("Error fetching data from repositories", e);
			throw e;
		}
		return "access";
	}

	@GetMapping("/swagger-ui")
	public String index() {
		return "redirect:swagger-ui.html";
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleException(Exception e, Model model) {
		logger.error("An error occurred", e);
		model.addAttribute("errorMessage", "An internal error occurred. Please try again later.");
		return "error";
	}

}