package br.sp.gov.etesp.tasks.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.sp.gov.etesp.tasks.model.Tarefa;
import br.sp.gov.etesp.tasks.repository.TarefaRepository;
import br.sp.gov.etesp.tasks.utils.StatusTarefa;




@Controller
public class HomeController implements WebMvcConfigurer{
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
	}
	
	@Autowired
	TarefaRepository repository;	
	


	@GetMapping("/")
	public String abrirHome(Model model) {
		List<Tarefa> tarefas = repository.findAll();
		model.addAttribute(new Tarefa());
		model.addAttribute("tarefas", tarefas);
		return "home";
	}

	@PostMapping("/adicionar")
	public String adicionarTarefa(Tarefa tarefa, Model model) {	
				
		tarefa.setStatus(StatusTarefa.ABERTO.name());		
		tarefa.setDataInicio(LocalDate.now());	
		repository.save(tarefa);
		List<Tarefa> tarefas = repository.findAll();		
		model.addAttribute("tarefas", tarefas);		
		return "home";
	}
	
	@GetMapping("/encerrar/{id}")
	public String encerrarTarefa(Model model, @PathVariable Long id) {		
		Tarefa tarefa = repository.findById(id).get();
		tarefa.setStatus(StatusTarefa.FECHADO.name());
		tarefa.setDataFim(LocalDate.now());
		repository.save(tarefa);
		List<Tarefa> tarefas  = repository.findAll();
		model.addAttribute("tarefas", tarefas);
		return "home";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluirTarefa(Model model, @PathVariable Long id) {
		repository.deleteById(id);
		List<Tarefa> tarefas  = repository.findAll();
		model.addAttribute("tarefas", tarefas);
		return "home";
	}
	
	@GetMapping("/editar/{id}")
	public String editarTarefa(Model model, @PathVariable Long id) {		
		Tarefa tarefa = repository.findById(id).get();
		model.addAttribute("tarefa", tarefa);
		return "editar-tarefa";
	}

}
