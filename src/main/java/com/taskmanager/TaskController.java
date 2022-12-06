package com.taskmanager;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
public class TaskController {

	private final TaskRepository taskRepository;
	
	public TaskController(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
	
	@GetMapping("/add")
	public String viewAddTask(Model model) {
		model.addAttribute("task", new Task());
		return "addTask";
	}
	
	@PostMapping("/add")
	public String submitAddTask(RedirectAttributes redirectAttributes, Task task) {
		taskRepository.save(task);
		redirectAttributes.addFlashAttribute("addTaskSuccessful", "Task added successfully");
		return "redirect:/tasks/add";
	}
	
	@GetMapping("/view-all")
	public String viewAllTasks(Model model) {
		List<Task> tasks = taskRepository.findAll();
		model.addAttribute("tasks", tasks);
		return "allTasks";
	}
	
	@Transactional
	@PostMapping("/{id}/complete")
	public String completeTask(RedirectAttributes redirectAttributes, @PathVariable("id") int id) {
		taskRepository.updateIsComplete(true, id);
		redirectAttributes.addFlashAttribute("updateSuccessful", "Task updated successfully");
		return "redirect:/tasks/view-all";
	}
	
	@PostMapping("/{id}/delete")
	public String deleteTask(RedirectAttributes redirectAttributes, @PathVariable("id") int id) {
		taskRepository.deleteById(id);
		redirectAttributes.addFlashAttribute("deleteSuccessful", "Task deleted successfully");
		return "redirect:/tasks/view-all";
	}
	
	@GetMapping("/filter-by-description")
	public String filterByDescription(Model model, @RequestParam("description") String description) {
		List<Task> filteredTasks = taskRepository.findAllByDescription(description);
		model.addAttribute("tasks", filteredTasks);
		return "allTasks";
	}
	
	@GetMapping("/filter-by-due-date")
	public String filterByDueDate(Model model, @RequestParam("due-date") String dueDate) {
		List<Task> filteredTasks = taskRepository.findAllByDueDate(dueDate);
		model.addAttribute("tasks", filteredTasks);
		return "allTasks";
	}
	
	@GetMapping("/filter-by-completion-status")
	public String filterByCompletionStatus(Model model, @RequestParam("completion-status") String completionStatus) {
		boolean isComplete = completionStatus.equalsIgnoreCase("yes") ? true : false;
		List<Task> filteredTasks = taskRepository.findAllByIsComplete(isComplete);
		model.addAttribute("tasks", filteredTasks);
		return "allTasks";
	}
	
	@GetMapping("/sort-by-due-date")
	public String sortByDueDate(Model model) {
		List<Task> sortedTasks = taskRepository.findByOrderByDueDateDesc();
		model.addAttribute("tasks", sortedTasks);
		return "allTasks";
	}
	
	@GetMapping("/sort-by-completion-status")
	public String sortByCompletionStatus(Model model) {
		List<Task> sortedTasks = taskRepository.findByOrderByIsCompleteDesc();
		model.addAttribute("tasks", sortedTasks);
		return "allTasks";
	}
	
}
