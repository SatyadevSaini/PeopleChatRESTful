package com.incapp.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.incapp.beans.Message;
import com.incapp.beans.User;

@Repository
public class PeopleRepo {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public String addUser(User u,String type,MultipartFile photo){
		try {
			String query="insert into users (email,name,phone,gender,dob,state,city,area,password,photo,account_type) values(?,?,?,?,?,?,?,?,?,?,?)";
			jdbcTemplate.update(query,new Object[] {u.getEmail(),u.getName(),u.getPhone(),u.getGender(),u.getDob(),u.getState(),u.getCity(),u.getArea(),u.getPassword(),photo.getInputStream(),type});
			
			return "success";
		}catch(Exception ex) {
			return "failed";
		}
	}
	
	public String login(String email,String password){
		class DataMapper implements RowMapper{
			public String mapRow(ResultSet rs,int rowNum)throws SQLException{
				return 	rs.getString("name");
			}
		}
		try {
			final String query ="select name from users where email=? and password=?";
			String r=(String) jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {email,password});
			return "success";
		}catch(EmptyResultDataAccessException ex) {
			return "failed";
		}
	}
	
	public User getUserByEmail(String email){
		class DataMapper implements RowMapper{
			public User mapRow(ResultSet rs,int rowNum)throws SQLException{
				User u=new User();
				u.setName(rs.getString("name"));
				u.setEmail(rs.getString("email"));
				u.setPhone(rs.getString("phone"));
				u.setGender(rs.getString("gender"));
				u.setState(rs.getString("state"));
				u.setCity(rs.getString("city"));
				u.setArea(rs.getString("area"));
				u.setDob(rs.getString("dob"));
				return 	u;
			}
		}
		try {
			final String query ="select * from users where email=? ";
			User u=(User) jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {email});
			return u;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	public User getUserByAccountType(String type,String email){
		class DataMapper implements RowMapper{
			public User mapRow(ResultSet rs,int rowNum)throws SQLException{
				User u=new User();
				u.setName(rs.getString("name"));
				u.setEmail(rs.getString("email"));
				u.setPhone(rs.getString("phone"));
				u.setGender(rs.getString("gender"));
				u.setState(rs.getString("state"));
				u.setCity(rs.getString("city"));
				u.setArea(rs.getString("area"));
				u.setDob(rs.getString("dob"));
				return 	u;
			}
		}
		try {
			final String query ="select * from users where email=? and account_type=? ";
			User u=(User) jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {email,type});
			return u;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	public List<User> getUserSearch(String state, String city, String area,String email){
		class DataMapper implements RowMapper{
			public User mapRow(ResultSet rs,int rowNum)throws SQLException{
				User u=new User();
				u.setName(rs.getString("name"));
				u.setEmail(rs.getString("email"));
				u.setPhone(rs.getString("phone"));
				u.setGender(rs.getString("gender"));
				u.setState(rs.getString("state"));
				u.setCity(rs.getString("city"));
				u.setArea(rs.getString("area"));
				u.setDob(rs.getString("dob"));
				return 	u;
			}
		}
		try {
			final String query ="select * from users where state=? and city=? and area like ? and email!=? ";
			List<User> u=jdbcTemplate.query(query,new DataMapper(),new Object[] {state,city,"%"+area+"%",email});
			if(u.isEmpty())
				return null;
			return u;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	public List<Message> getMessages(String semail, String remail){
		class DataMapper implements RowMapper{
			public Message mapRow(ResultSet rs,int rowNum)throws SQLException{
				Message m=new Message();
				m.setId(rs.getInt("id"));
				m.setMessage(rs.getString("msg"));
				m.setsEmail(rs.getString("sid"));
				m.setrEmail(rs.getString("rid"));
				m.setFileName(rs.getString("filename"));
				m.setUdate(rs.getDate("udate").toLocalDate());
				m.setUtime(rs.getString("utime"));
				return 	m;
			}
		}
		try {
			final String query ="select * from peoplemsg where sid=? and rid=? ";
			List<Message> m=jdbcTemplate.query(query,new DataMapper(),new Object[] {semail,remail});
			if(m.isEmpty())
				return null;
			return m;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	public byte[] getPhoto(String email){
		class DataMapper implements RowMapper{
			public byte[] mapRow(ResultSet rs,int rowNum)throws SQLException{
				return 	rs.getBytes("photo");
			}
		}
		try {
			final String query ="select photo from users where email=?";
			byte[] r=(byte[]) jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {email});
			return r;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	public byte[] downloadFile(int id){
		class DataMapper implements RowMapper{
			public byte[] mapRow(ResultSet rs,int rowNum)throws SQLException{
				return 	rs.getBytes("ufile");
			}
		}
		try {
			final String query ="select ufile from peoplemsg where id=?";
			byte[] r=(byte[]) jdbcTemplate.queryForObject(query,new DataMapper(),new Object[] {id});
			return r;
		}catch(EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	public String sendMessage(Message m, MultipartFile file){
		try {
			String query="insert into peoplemsg (sid,rid,msg,filename,ufile,udate,utime) values(?,?,?,?,?,?,?)";
			jdbcTemplate.update(query,new Object[] {m.getsEmail(),m.getrEmail(),m.getMessage(),m.getFileName(),file.getInputStream(),m.getUdate(),m.getUtime()});
			
			return "success";
		}catch(Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
	}
	
	public String sendMessageWithoutFile(Message m){
		try {
			String query="insert into peoplemsg (sid,rid,msg,udate,utime) values(?,?,?,?,?)";
			jdbcTemplate.update(query,new Object[] {m.getsEmail(),m.getrEmail(),m.getMessage(),m.getUdate(),m.getUtime()});
			
			return "success";
		}catch(Exception ex) {
			ex.printStackTrace();
			return "failed";
		}
	}
	
}
