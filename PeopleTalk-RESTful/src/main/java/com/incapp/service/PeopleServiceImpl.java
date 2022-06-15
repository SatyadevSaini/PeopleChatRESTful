package com.incapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Message;
import com.incapp.beans.User;
import com.incapp.repo.PeopleRepo;

@Service
public class PeopleServiceImpl implements PeopleService {

	@Autowired
	PeopleRepo peopleRepo;
	@Override
	public String addUser(User u, String type,MultipartFile photo) {
		return peopleRepo.addUser(u,type, photo);
	}
	@Override
	public String login(String email, String password) {
		return peopleRepo.login(email, password);
	}
	@Override
	public User getUserByEmail(String email) {
		return peopleRepo.getUserByEmail(email);
	}
	@Override
	public List<User> getUserSearch(String state, String city, String area,String email) {
		return peopleRepo.getUserSearch(state,city,area,email);
	}
	@Override
	public byte[] getPhoto(String email) {
		return peopleRepo.getPhoto(email);
	}
	@Override
	public String sendMessage(Message m, MultipartFile file) {
		return peopleRepo.sendMessage(m, file);
	}
	@Override
	public String sendMessageWithoutFile(Message m) {
		return peopleRepo.sendMessageWithoutFile(m);
	}
	@Override
	public List<Message> getMessages(String semail, String remail) {
		return peopleRepo.getMessages(semail,remail);
	}
	@Override
	public byte[] downloadFile(int id) {
		return peopleRepo.downloadFile(id);
	}
	@Override
	public User getUserByAccountType(String type, String email) {
		return peopleRepo.getUserByAccountType(type,email);
	}

}
