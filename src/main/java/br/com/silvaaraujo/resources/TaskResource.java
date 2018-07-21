package br.com.silvaaraujo.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")	
	public Task buscarPorId(@PathVariable("id") Long id) throws Exception {
		Optional<Task> optional = this.taskRepository.findById(id);
		return optional.isPresent() ? optional.get() : null;
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST)	
	public ResponseEntity<Void> incluir(@Valid @RequestBody Task task) {
		Task savedTask = this.taskRepository.save(task);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedTask.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	
}
