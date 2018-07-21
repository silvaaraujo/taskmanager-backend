package br.com.silvaaraujo.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.silvaaraujo.repository.TaskRepository;


@RestController
@RequestMapping(value="/tasks")
public class TaskResource {

	@Autowired
	private TaskRepository taskRepository;
	
}
