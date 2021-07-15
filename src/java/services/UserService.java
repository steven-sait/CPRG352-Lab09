package services;

import java.util.ArrayList;
import models.User;
import dataaccess.UserDB;

public class UserService
{
    public ArrayList<User> getAll() throws Exception
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
        db.insert( new User( email, active , first, last, pwd, role ) );
    }
    
    public void update( String email, boolean active, String first, String last, String pwd, int role ) throws Exception
    {
        UserDB db = new UserDB();
        db.update( new User( email, active , first, last, pwd, role ) );
    }
    
    public void delete( String email ) throws Exception 
    {
        UserDB db = new UserDB();
        db.delete( email );
    }
}