package com.project.springboot_one_to_one_CRUD_bi;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentController 
{

	@Autowired
	StudentRepository srepo;
	
	@Autowired
	AddressRepository arepo;
	
	@RequestMapping("/test")
	public String test()
	{
		return "One to one Bidirectional project";
	}
	@PostMapping("/save")
	public String saveData(@RequestBody Student s)
	{
		srepo.save(s);
		return "Data is saved";
	}
	
//	@RequestMapping("/save")
//	public String save(@RequestBody Student student)
//	{
//		Student s=new Student();
//		Address a=new Address();
//		a.setStreet("lalpur");
//		a.setCity("Ranchi");
//		s.setName("Rahul");
//		s.setAge(21);
//		s.setCourse("IT");
//		s.setRoll(98);
//		s.setAddress(a);
//		a.setStudent(s);
//		
//		srepo.save(s);
//		
//		student.getAddress().setStudent(student);
//		Student saveuser=srepo.save(student);
//		return "Data saved";
//	}
	

	@GetMapping("/all")
	public List<Student> allData()
	{
		return srepo.findAll();
	}
	@RequestMapping("/{id}")
	public Optional<Student> byId(@PathVariable int id)
	{
		return srepo.findById(id);
	}
	@DeleteMapping("/del/{id}")
	public String delete(@PathVariable int id)
	{
		srepo.deleteById(id);
		return "Data is deleted";
	}
	
	@GetMapping("/name/{name}")
	public List<Student> byName(@PathVariable String name)
	{
		return srepo.findByName(name);
	}
	

	//CRUD Operation by second pojo Address
	
	@PostMapping("/savedata")
	public String saveInfo(@RequestBody Address a)
	{
		arepo.save(a);
		return "data is saved";
	}
	
	@GetMapping("/alldata")
	public List<Address> allInfo()
	{
		return arepo.findAll();
		
	}

	@DeleteMapping("/address/del/{id}")
	public String deleteByAddress(@PathVariable int id)
	{
		arepo.deleteById(id);
		return "Data is deleted";
	}
	
	@RequestMapping("/city/{city}")
	public List<Address> bycity(@PathVariable String city)
	{
		return arepo.findByCity(city);
	}
	
	@RequestMapping("/upd/{id}")
	public Student update(@RequestBody Student student,@PathVariable int id)
	{
		Student s=srepo.findById(id).get();
		s.setName(student.getName());
		s.setAge(student.getAge());
		s.setRoll(student.getRoll());
		s.setCourse(student.getCourse());
		s.setAddress(student.getAddress());
		s.address.setStreet(student.getAddress().getStreet());
		s.address.setCity(student.getAddress().getCity());
		return srepo.save(s);
	}
	//Partial update (patch) only user selected fields
	@RequestMapping("/parupd/{id}")
	public Student partialUpdate(@RequestBody Map<String,Object> fields,@PathVariable int id)
	{
		Optional<Student> s=srepo.findById(id);
		fields.forEach((key,value)->
		{
			Field field=ReflectionUtils.findRequiredField(Student.class, key);
			field.setAccessible(true);
			ReflectionUtils.setField(field, s.get(), value);
		});
		return srepo.save(s.get());
	}
}
