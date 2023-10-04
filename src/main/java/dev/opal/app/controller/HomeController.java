package dev.opal.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import dev.opal.app.repository.GroupRepository;
import dev.opal.app.repository.ResourceRepository;
import dev.opal.app.repository.UserRepository;

@Controller
public class HomeController {

	private final ResourceRepository resourceRepository;
	private final UserRepository userRepository;
	private final GroupRepository groupRepository;

	public HomeController(ResourceRepository resourceRepository, UserRepository userRepository,
			GroupRepository groupRepository) {
		this.resourceRepository = resourceRepository;
		this.userRepository = userRepository;
		this.groupRepository = groupRepository;
	}

	// TODO Use service and refactor DTOs:
	// UserDTO: Should only contain basic user details. Remove any references to
	// other entities (like Group or Resource).
	// GroupDetailDTO: Contains basic group details, and lists of UserDTO and
	// ResourceDTO. Ensure that these DTOs don't reference the Group.
	// ResourceDTO: Contains basic resource details, and a list of UserDTO. Make
	// sure that these DTOs don't reference the Resource.
	@GetMapping("/")
	public String showUsersResources(Model model) {
		model.addAttribute("users", userRepository.findAll());
		model.addAttribute("resources", resourceRepository.findAll());
		model.addAttribute("groups", groupRepository.findAll());

		return "usersGroupsAndResources";
	}

	@GetMapping("/swagger-ui")
	public String index() {
		return "redirect:swagger-ui.html";
	}
}