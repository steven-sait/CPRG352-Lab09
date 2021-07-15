package services;

import dataaccess.RoleDB;
import java.util.ArrayList;
import models.Role;

public class RoleService
{
    public ArrayList<Role> getAll() throws Exception
    {
        RoleDB db = new RoleDB();
        return db.getAll();
    }
}