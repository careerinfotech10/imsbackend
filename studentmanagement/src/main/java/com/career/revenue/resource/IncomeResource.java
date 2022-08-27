package com.career.revenue.resource;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.career.revenue.entity.Income;
import com.career.revenue.service.IncomeService;

@RestController
@RequestMapping("/revenue")
public class IncomeResource {

	@Autowired
	private IncomeService incomeService;

	@GetMapping()
	public List<Income> getIncome() {
		return incomeService.getIncomes();
	}

	@PostMapping("/incomes")
	public Map<String, List<String>> saveIncomes(@RequestBody List<Income> incomes) {
		return incomeService.saveIncomes(incomes);
	}
	@PostMapping("/")
	public Income createIncome(@RequestBody Income income) {
		
		return incomeService.createIncome(income);
	}

}
