package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import models.Role;

public class RoleDB
{
    public ArrayList<Role> getAll() throws Exception
    {
        ArrayList<Role> roles = new ArrayList<>();
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection conn = pool.getConnection();
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String sql_query = "SELECT * FROM role";

        try 
        {
            ps = conn.prepareStatement( sql_query );
            rs = ps.executeQuery();
            
            while( rs.next() )
                roles.add( new Role( rs.getInt( 1 ), rs.getString( 2 ) ) );
        }
        catch( java.sql.SQLException e )
        {
            e.printStackTrace();
        }
        finally
        {
            DBUtil.closePreparedStatement( ps );
            DBUtil.closeResultSet( rs );
            pool.freeConnection( conn );
        }
        
        return roles;
    }
}