package com.example.demo.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaceService.IpersonService;
import com.example.demo.interfaces.IPerson;
import com.example.demo.models.Person;

@Service
public class PersonService implements IpersonService{
	
	@Autowired
	private IPerson data;

	@Override
	public List<Person> listar() {
		// TODO Auto-generated method stub
		
		return (List<Person>) data.findAll();
	}

	@Override
	public Optional<Person> listarId(int id) {
		// TODO Auto-generated method stub
		return data.findById(id);
	}

	@Override
	public int save(Person p) {
		// TODO Auto-generated method stub
		int response=0;
		Person person= data.save(p);
		if(!person.equals(null)) {
			response=1;
		}
		return response;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		data.deleteById(id);
	}

}

