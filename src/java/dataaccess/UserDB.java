package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.User;

public class UserDB
{
    private ConnectionPool pool = ConnectionPool.getInstance();
    private Connection conn = pool.getConnection();
    private PreparedStatement ps = null;
    private ResultSet rs = null;
        
    public ArrayList<User> getAll() throws Exception
    {
        ArrayList<User> users = new ArrayList<>();
        String sql_query = "SELECT * FROM user";
        
        try 
        {
            ps = conn.prepareStatement( sql_query );
            rs = ps.executeQuery();

            while( rs.next() )
                users.add( new User( rs.getString( 1 ), rs.getBoolean( 2 ), rs.getString( 3 ), rs.getString( 4 ), rs.getString( 5 ), rs.getInt( 6 ) ) );
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            DBUtil.closePreparedStatement( ps );
            DBUtil.closeResultSet( rs );
            pool.freeConnection( conn );
        }
        
        return users;
    }
    
    public User get( String email ) throws Exception
    {
        String sql_query = "SELECT * FROM user WHERE email = ?";
        User user = null;
                
        try {
            ps = conn.prepareStatement( sql_query );
            ps.setString( 1, email );
            rs = ps.executeQuery();
            
            while( rs.next() ) 
               user = new User( email, rs.getBoolean( 2 ), rs.getString( 3 ), rs.getString( 4 ), rs.getString( 5 ), rs.getInt( 6 ) );
            
        } finally {
            DBUtil.closePreparedStatement( ps );
            DBUtil.closeResultSet( rs );
            pool.freeConnection( conn );
        }
        
        return user;
    }
    
    public void insert( User user )
    {
        String sql_query = "INSERT INTO user VALUES(?,?,?,?,?,?)";
        
        try 
        {
            ps = conn.prepareStatement( sql_query );
            
            // Initialize our values
            ps.setString( 1, user.getEmail() );
            ps.setBoolean( 2, user.getActive() );
            ps.setString( 3, user.getFirstName() );
            ps.setString( 4, user.getLastName() );
            ps.setString( 5, user.getPassword() );
            ps.setInt( 6, user.getRole() );
            
            ps.executeUpdate();
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            DBUtil.closePreparedStatement( ps );
            pool.freeConnection( conn );
        }
    }
    
    public void update( User new_user )
    {
        String sql_query = "UPDATE user SET active = ?, first_name = ?, last_name = ?, password = ?, role = ? WHERE email = ?";
        
        try
        {
            ps = conn.prepareStatement( sql_query );
            ps.setBoolean( 1, new_user.getActive() );
            ps.setString( 2, new_user.getFirstName() );
            ps.setString( 3, new_user.getLastName() );
            ps.setString( 4, new_user.getPassword() );
            ps.setInt( 5, new_user.getRole() );
            ps.setString( 6, new_user.getEmail() );
            ps.executeUpdate();
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            DBUtil.closePreparedStatement( ps );
            pool.freeConnection( conn );
        }
    }
    
    public void delete( String email )
    {
        String sql_query = "DELETE FROM user WHERE email = ?";
        
        try
        {
            ps = conn.prepareStatement( sql_query );
            ps.setString( 1, email );
            ps.executeUpdate();
        }
        catch( SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            DBUtil.closePreparedStatement( ps );
            pool.freeConnection( conn );
        }
    }
}
