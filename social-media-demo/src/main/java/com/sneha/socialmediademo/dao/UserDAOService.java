package com.sneha.socialmediademo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.sneha.socialmediademo.model.User;

@Component
public class UserDAOService {

	private static List<User> users = new ArrayList<>();
	private static int count =0;
	static {
		users.add(new User(++count,"John",LocalDate.now().minusYears(25)));
		users.add(new User(++count,"Jane", LocalDate.now().minusYears(30)));
		users.add(new User(23,"Max",LocalDate.now().minusYears(18)));
	}
	
	public User saveUser(User user) {
		user.setId(++count);
		users.add(user);
		return user;
	}
	
	public List<User> findAll(){
		return users;
	}
	
	public User findUserByID(int id) {
		for(User temp:users) {
			if(temp.getId() == id) {
				return temp;
			}
		}
		return null;
	}
	
	public User deleteUser(int id) { 
//		Iterator<User> iterator = users.iterator();
//		while(iterator.hasNext()) {
//			User user = iterator.next();
//			if(user.getId() == id) {
//				iterator.remove();
//			}
//		}
		
		for(User temp:users) {
			if(temp.getId() == id) {
				users.remove(temp);
				return temp;
			}
		}
		
		return null;
	}
}
