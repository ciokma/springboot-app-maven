package com.example.demo.interfaceService;

import java.util.List;
import java.util.Optional;

import com.example.demo.models.*;
public interface IpersonService {
	public List<Person>listar();
	public Optional<Person>listarId(int id);
	public int save(Person p);
	public void delete(int id);
	
}
