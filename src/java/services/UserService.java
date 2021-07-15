package services;

import java.util.List;
import models.User;
import dataaccess.UserDB;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Role;

public class UserService
{
    RoleService role_service = new RoleService();
    List<Role> roles = new ArrayList();
    
    public UserService()
    {
        try
        {
            roles = role_service.getAll();
        }
        catch( Exception ex )
        {
            Logger.getLogger( UserService.class.getName() ).log( Level.SEVERE, null, ex );
        }
    }
    
    public List<User> getAll() throws Exception
    {
        UserDB db = new UserDB();
        return db.getAll();
    }
    
    public User get( String email ) throws Exception
    {
        UserDB db = new UserDB();
        return db.get( email );
    }

    public void insert( String email, boolean active, String first, String last, String pwd, int role ) throws Exception
    {
        UserDB db = new UserDB();
        db.insert( new User( email, active, first, last, pwd, roles.get( role - 1 ) ) );
    }
    
    public void update( String email, boolean active, String first, String last, String pwd, int role ) throws Exception
    {
        UserDB db = new UserDB();
        db.update( new User( email, active , first, last, pwd, roles.get( role - 1 ) ) );
    }
    
    public void delete( String email ) throws Exception 
    {
        UserDB db = new UserDB();
        db.delete( email );
    }
}