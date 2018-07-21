package br.com.silvaaraujo.resources;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Date;
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

import br.com.silvaaraujo.exception.ObjectNotFoundException;
import br.com.silvaaraujo.model.Task;
import br.com.silvaaraujo.model.enuns.StatusTask;
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
		return optional.orElseThrow(() -> 
			new ObjectNotFoundException(MessageFormat.format("{0} nao encontrada com {1} : {2}", "Task", "id", id)));
	}
	
	@Transactional
	@RequestMapping(method=RequestMethod.POST)	
	public ResponseEntity<Void> incluir(@Valid @RequestBody Task task) {
		Task savedTask = this.taskRepository.save(task);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedTask.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")	
	public ResponseEntity<Void> remover(@PathVariable("id") Long id) throws Exception {
		Optional<Task> optional = this.taskRepository.findById(id);
		if (!optional.isPresent()) 
			throw new ObjectNotFoundException(MessageFormat.format("{0} nao encontrada com {1} : {2}", "Task", "id", id));
		
		this.taskRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method=RequestMethod.PUT, value = "/{id}")	
	public ResponseEntity<Void> atualizar(@PathVariable("id") Long id, @Valid @RequestBody Task task) {
		Optional<Task> optional = this.taskRepository.findById(id);
		if (!optional.isPresent()) 
			throw new ObjectNotFoundException(MessageFormat.format("{0} nao encontrada com {1} : {2}", "Task", "id", id));
			
		task = this.mergeFromView(task, optional.get());
		this.taskRepository.save(task);
		return ResponseEntity.noContent().build();
	}
	
	
	@RequestMapping(method=RequestMethod.PUT, value = "/concluida/{id}")	
	public ResponseEntity<Void> concluir(@PathVariable("id") Long id) {
		Optional<Task> optional = this.taskRepository.findById(id);
		if (!optional.isPresent()) 
			throw new ObjectNotFoundException(MessageFormat.format("{0} nao encontrada com {1} : {2}", "Task", "id", id));
			
		Task task = optional.get();
		task.setDataEdicao(new Date());
		task.setStatus(StatusTask.CONCLUIDA);
		
		this.taskRepository.save(task);
		return ResponseEntity.noContent().build();
	}
	
	private Task mergeFromView(Task taskView, Task taskBd) {
		taskBd.setDataEdicao(new Date());
		taskBd.setDescricao(taskView.getDescricao());
		taskBd.setTitulo(taskView.getTitulo());
		return taskBd;
	}
	
}
