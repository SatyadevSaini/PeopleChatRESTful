package com.incapp.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Message;
import com.incapp.beans.User;

public interface PeopleService {
	String addUser(User u,String type,MultipartFile photo);
	String login(String email,String password);
	User getUserByEmail(String email);
	List<User> getUserSearch(String state,String city,String area,String email);
	byte[] getPhoto(String email);
	byte[] downloadFile(int id);
	String sendMessage(Message m,MultipartFile file);
	String sendMessageWithoutFile(Message m);
	List<Message> getMessages(String semail,String remail);
	User getUserByAccountType(String type,String email);
}
