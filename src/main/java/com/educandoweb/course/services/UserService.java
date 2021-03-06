package com.educandoweb.course.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.DatabaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Long id) {
		Optional<User> obj = userRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id)); 
	}

	public User insert(User user) {
		return userRepository.save(user);
	}

	public void delete(Long id) {
		try {
			userRepository.deleteById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
		
	}

	public User update(Long id, User user) {
		try {
		Optional<User> found = userRepository.findById(id);
		found.get().setNome(user.getNome());
		found.get().setEmail(user.getEmail());
		found.get().setPhone(user.getPhone());
		return userRepository.save(found.get());
		}catch(NoSuchElementException e) {
			throw new ResourceNotFoundException(id);
		}
	}

//	public User update(Long id, User obj) {
//		try {
//			User entity = userRepository.getOne(id);
//			updateData(entity, obj);
//			return userRepository.save(entity);
//		}catch(RuntimeException e) {
//			e.printStackTrace();
//			throw new ResourceNotFoundException(id);
//		}
//		
//	}
//
//	private void updateData(User entity, User obj) {
//		entity.setNome(obj.getNome());
//		entity.setEmail(obj.getEmail());
//		entity.setPhone(obj.getPhone());
//
//	}
}
