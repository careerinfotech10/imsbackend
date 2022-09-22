package com.career.revenue.service;

import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.career.revenue.entity.Income;
import com.career.revenue.repo.IncomeRepo;

@Service
public class IncomeService {

	private ModelMapper modelMapper=new ModelMapper();
	@Autowired
	private IncomeRepo incomeRepo;

	public List<Income> getIncomes() {
		// TODO Auto-generated method stub
		return incomeRepo.findAll();
	}

	public Map<String, List<String>> saveIncomes(List<Income> incomes) {
		// TODO Auto-generated method stub

		List<Income> saveAll = incomeRepo.saveAll(incomes);

		return null;

	}

	public Income createIncome(Income income) {
		// TODO Auto-generated method stub
		return incomeRepo.save(income);
	}

}
