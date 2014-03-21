package no.ntnu.stud.ubilearn.server.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import no.ntnu.stud.ubilearn.server.Exceptions.EmptyResultSetException;
import no.ntnu.stud.ubilearn.server.models.User;

public class UserDAO extends DAO {
	
	public void insert(User user){
		PreparedStatement st = null;
		ResultSet rs = null;
		try{
			st = db.prepareStatement("INSERT INTO espehel_ubilearn.User(email,password) VALUES(?,?)");
			st.setString(1, user.getEmail());
			st.setString(2, user.getPassword());
			st.executeUpdate();

		}catch(SQLException e){
			e.printStackTrace();
			//TODO log this or something
		}finally{
			db.close(st,rs);
		}
	}
	public void update(User user){
		
	}
	public void delete(User user){
		
	}
	public User select(int id){
		return null;
	}
	public User getUser(String email) throws EmptyResultSetException{
		PreparedStatement st = null;
		ResultSet rs = null;
		User user = null;
		try{
			st = db.prepareStatement("SELECT * FROM espehel_ubilearn.User WHERE email=?");
			st.setString(1, email);

			rs = st.executeQuery();
			
			if(rs.next()){
				user = createUser(rs);
			}
			else{
				throw new EmptyResultSetException();
			}
							
		}catch(SQLException e){
			e.printStackTrace();
			//TODO log this or something
		}finally{
			db.close(st,rs);
		}

		return user;
				
	}
	private User createUser(ResultSet rs) {
		User user = null;
		try {
			System.out.println();
			long id = rs.getLong("id");
			String email = rs.getString("email");
			String pw = rs.getString("password");
			user = new User(id, email, pw);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}
	public static void main(String[] args) {
		UserDAO u = new UserDAO();
		
		
		
		User user = null;
		try {
			user = u.getUser("kai@gmail.com");
		} catch (EmptyResultSetException e) {
			System.out.println("empty result");
			e.printStackTrace();
		}
		System.out.println(user);
	}

}
