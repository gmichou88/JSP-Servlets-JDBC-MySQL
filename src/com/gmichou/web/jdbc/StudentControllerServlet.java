package com.gmichou.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private StudentDbUtil studentDbUtil;
	@Resource(name="jdbc/web_student_tracker")
	private DataSource dataSource;
	
	@Override
	public void init() throws ServletException {
		super.init();
		try {
			studentDbUtil = new StudentDbUtil(dataSource);
		}
		catch(Exception e){
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String command = request.getParameter("command");
			
			if(command == null) command="LIST";
			
			switch(command) {			
				case "LIST":
					index(request, response);
					break;
				case "LOAD":
					loadStudent(request, response);
					break;
				case "DELETE":
					destroy(request, response);
					break;
				default:
					index(request, response);
			}
		}
		catch(Exception e) {
			throw new ServletException(e);
		}
	
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			String command = request.getParameter("command");
			
			if(command == null) command="LIST";
			
			switch(command) {
				case "LIST":
					index(request, response);
					break;
				case "ADD":
					create(request, response);
					break;
				case "UPDATE":
					update(request, response);
					break;
				default:
					index(request, response);
			}
		}
		catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	private void destroy(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int id = Integer.parseInt(request.getParameter("id"));
		
		studentDbUtil.deleteStudent(id);
		
		//index(request, response);
		
		response.sendRedirect(request.getContextPath() + "/StudentControllerServlet");
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		int id = Integer.parseInt(request.getParameter("id"));
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student student = new Student(id, firstName, lastName, email);

		studentDbUtil.updateStudent(student);
		
		//index(request, response);
		
		response.sendRedirect(request.getContextPath() + "/StudentControllerServlet");
	}

	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String studentId = request.getParameter("id");
		
		Student student = studentDbUtil.getStudent(studentId);
	
		request.setAttribute("student", student);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request, response);
	}

	private void create(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		
		Student student = new Student(firstName, lastName, email);

		
		studentDbUtil.insertStudent(student);
		
		//index(request, response);

		response.sendRedirect(request.getContextPath() + "/StudentControllerServlet");
		
	}

	private void index(HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		List<Student> students = studentDbUtil.getStudents();
		request.setAttribute("students", students);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
		dispatcher.forward(request, response);

	}

}
