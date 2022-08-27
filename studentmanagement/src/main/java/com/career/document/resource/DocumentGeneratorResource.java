package com.career.document.resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.career.students.service.ContractGenerator;

@RestController
@RequestMapping("/document")
public class DocumentGeneratorResource {
	
	@Autowired
	ContractGenerator generatorService;
	@GetMapping
	public Map<String, List<String>> generateReport() {
	return 	generatorService.prepareContract();
		
	}

}
