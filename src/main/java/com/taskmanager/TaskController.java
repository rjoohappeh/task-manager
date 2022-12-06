package com.taskmanager;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
		tasks.forEach(System.out::println);
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
}
