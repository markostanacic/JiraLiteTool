package com.example.JiraLiteTool.JiraLiteTool.webcontrollers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.JiraLiteTool.JiraLiteTool.domains.Project;
import com.example.JiraLiteTool.JiraLiteTool.services.ProjectService;
import com.example.JiraLiteTool.JiraLiteTool.services.ValidationErrorService;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;

	@Autowired
	private ValidationErrorService validationErrorService;

	@PostMapping("")
	public ResponseEntity<?> createNewProject(@Valid @RequestBody Project project, BindingResult result) {

		ResponseEntity<?> errorMap = validationErrorService.validationError(result);

		if (errorMap != null) {
			return errorMap;
		}

		projectService.saveOrUpdateProject(project);
		return new ResponseEntity<>(project, HttpStatus.CREATED);
	}
	
	@GetMapping("/{projectId}")
	public ResponseEntity<?> getProjectById(@PathVariable String projectId) {
		
		Project project = projectService.findProjectByIdentifier(projectId);
		
		return new ResponseEntity<Project>(project, HttpStatus.OK);
	}
	
	@GetMapping("/all")
	public Iterable<Project> getAllProjects() {
		return projectService.findAllProject();
	}
	
	 @DeleteMapping("/{projectId}")
	    public ResponseEntity<?> deleteProject(@PathVariable String projectId){
	        projectService.deleteProjectByIdentifier(projectId);

	        return new ResponseEntity<String>("Project with ID: '"+projectId+"' was deleted", HttpStatus.OK);
	    }
}
