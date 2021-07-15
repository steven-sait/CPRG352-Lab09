package models;

public class User
{
    private String email;
    private boolean active;
    private String first_name;
    private String last_name;
    private String password;
    private int role_id;
    
    public User( String email, boolean active, String first, String last, String pwd, int role_id )
    {
        this.email = email;
        this.active = active;
        this.first_name = first;
        this.last_name = last;
        this.password = pwd;
        this.role_id = role_id;
    }
    
    public void setActive( boolean status )
    {
        this.active = status;
    }
    
    public void setFirstName( String first )
    {
        this.first_name = first;
    }
    
    public void setLastName( String last )
    {
        this.last_name = last;
    }
    
    public void setPassword( String pwd )
    {
        this.password = pwd;
    }
    
    public void setRole( int id )
    {
        this.role_id = id;
    }   
    
    public String getEmail()
    {
        return this.email;
    }
    
    public boolean getActive()
    {
        return this.active;
    }
    
    public String getFirstName()
    {
        return this.first_name;
    }
    
    public String getLastName()
    {
        return this.last_name;
    }
    
    public String getPassword()
    {
        return this.password;
    }
    
    public int getRole()
    {
        return this.role_id;
    }
}
