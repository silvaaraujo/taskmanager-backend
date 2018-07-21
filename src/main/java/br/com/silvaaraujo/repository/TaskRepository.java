package br.com.silvaaraujo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.silvaaraujo.model.Task;
import br.com.silvaaraujo.model.enuns.StatusTask;

public interface TaskRepository extends JpaRepository<Task, Long> {

	Task findByStatus(StatusTask status);
	
}
