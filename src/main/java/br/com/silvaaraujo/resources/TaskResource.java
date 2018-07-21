package br.com.silvaaraujo.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.silvaaraujo.model.Task;
import br.com.silvaaraujo.repository.TaskRepository;


@RestController
@RequestMapping(value="/tasks")
public class TaskResource {

	@Autowired
	private TaskRepository taskRepository;
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public List<Task> listarTodos() {
		return this.taskRepository.findAll();
	}
	
}
