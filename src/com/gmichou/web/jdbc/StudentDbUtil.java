package com.gmichou.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	private DataSource dataSource;
	
	public StudentDbUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Student> getStudents() throws Exception{
		List<Student> students = new ArrayList<>();
		
		Connection connection = null;
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "SELECT * FROM student ORDER BY last_name";
	 		stmt = connection.createStatement();
			result = stmt.executeQuery(sql);
			
			while(result.next()){
				Student tempStudent = new Student(
						result.getInt("id"),
						result.getString("first_name"),
						result.getString("last_name"),
						result.getString("email")
				);
				
				students.add(tempStudent);
			}
			
			return students;
		}
		finally {
			close(connection, stmt, result);
		}
	}

	private void close(Connection connection, Statement stmt, ResultSet result) {
		try {
			if(result != null) result.close();
			if(stmt != null) stmt.close();
			if(connection != null) connection.close(); //doesnt't really close connection... puts back in connection pool and makes it available for someone else
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public void insertStudent(Student student) throws Exception{
				
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "INSERT INTO student"
						+ "(first_name, last_name, email)"
						+ "VALUES(?, ?, ?)";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, student.getFirstName());
			stmt.setString(2, student.getLastName());
			stmt.setString(3, student.getEmail());
			
			stmt.execute();
			
		}
		finally {
			close(connection, stmt, null);
		}
	}

	public Student getStudent(String studentId) throws Exception{
		Student student = null;
		
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet result = null;
		int id;
		
		try {
			
			id = Integer.parseInt(studentId);
			
			connection = dataSource.getConnection();
			
			String sql = "SELECT * FROM student WHERE id = ?";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, id); 
			result = stmt.executeQuery();
			
			if(result.next()) {
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String email = result.getString("email");
				
				student = new Student(id, firstName, lastName, email);
			}
			else{
				throw new Exception("Could not find student id: " + id);
			}
			
			return student;
		}
		finally {
			
			close(connection, stmt, result);
		}
	}

	public void updateStudent(Student student) throws Exception{
		
		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "UPDATE student "
					+ "SET first_name = ? , last_name = ? , email = ?"
					+ "WHERE id = ?";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, student.getFirstName());
			stmt.setString(2, student.getLastName());
			stmt.setString(3, student.getEmail());
			stmt.setInt(4, student.getId());
			
			stmt.execute();
		}
		finally {
			
			close(connection, stmt, null);
		}
	}

	public void deleteStudent(int id) throws Exception{

		Connection connection = null;
		PreparedStatement stmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			String sql = "DELETE FROM student "
					+ "WHERE id = ?";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setInt(1, id);
			stmt.execute();
		}
		finally {
			close(connection, stmt, null);
		}
	}
}





