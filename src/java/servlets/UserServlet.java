package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.Role;

import models.User;
import services.UserService;
import services.RoleService;

public class UserServlet extends HttpServlet
{
    private UserService user_service;
    private RoleService role_service;
    
    private ArrayList<User> user_list = new ArrayList<>();
    private ArrayList<Role> role_list = new ArrayList<>();
    
    private void printSystemMessage( HttpServletRequest request, HttpServletResponse response, String message ) throws ServletException, IOException
    {
        request.setAttribute( "system_message", message );
        getServletContext().getRequestDispatcher( "/WEB-INF/users.jsp" ).forward( request, response );
    }
    
    private void serveLists( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        if( user_service == null )
            user_service = new UserService();
        
        if( role_service == null )
            role_service = new RoleService();
        
        if( role_list.isEmpty() )
        {
            try
            {
                role_list = role_service.getAll();
            }
            catch( Exception ex )
            {
                Logger.getLogger( UserServlet.class.getName() ).log( Level.SEVERE, null, ex );
                printSystemMessage( request, response, "Failed to initialize role list" );
                return;
            }
        }
        
        try
        {
            user_list = user_service.getAll();
        }
        catch( Exception ex )
        {
            Logger.getLogger( UserServlet.class.getName() ).log( Level.SEVERE, null, ex );
            printSystemMessage( request, response, "Failed to initialize user list" );
            return;
        }
              
        request.setAttribute( "role_list", role_list );
        request.setAttribute( "user_list", user_list );    
    }
    
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        serveLists( request, response );
        
        final String ACTION = request.getParameter( "action" );     
        if( ACTION != null && request.getParameter( "id" ) != null )
        {
            int user_id = -1;
            try
            {
                user_id = Integer.parseInt( request.getParameter( "id" ) );
            }
            catch( NumberFormatException ex )
            {
                Logger.getLogger( "PARSE_ID" ).log( Level.SEVERE, null, ex);
                printSystemMessage( request, response, "Invalid ID" );
                return;
            }
            
            if( user_id == -1 )
            {
                printSystemMessage( request, response, "Invalid ID" );
                return;
            }
            
            User action_user = user_list.get( user_id );
            if( action_user == null )
            {
                printSystemMessage( request, response, "Could not find User" );
                return;
            }
                
            if( ACTION.equals( "edit" ) )
                request.setAttribute( "action_user", action_user );
            else if( ACTION.equals( "delete" ) )
            {
                try
                {
                    user_service.delete( action_user.getEmail() );
                    
                    // update our list
                    serveLists( request, response );
                }
                catch( Exception ex )
                {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
       getServletContext().getRequestDispatcher( "/WEB-INF/users.jsp" ).forward( request, response );
    }
   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        final String ACTION = request.getParameter( "action" );
        if( ACTION == null )
            return;
         
        String email = request.getParameter( "email" );
        String first = request.getParameter( "first" );
        String last = request.getParameter( "last" );
        String password = request.getParameter( "pwd" );
        boolean active = request.getParameter( "active" ) != null;
        int role = -1;
        
        try
        {
            role = Integer.parseInt( request.getParameter( "role" ) );
        }
        catch( NumberFormatException ex )
        {
            Logger.getLogger( "PARSE_ROLE" ).log( Level.SEVERE, null, ex);
            printSystemMessage( request, response, "Invalid Role" );
            return;
        }
        
        if( email == null || first == null || last == null || password == null || role == -1 )
        {
            printSystemMessage( request, response, "Invalid Edit Request" );
            return;
        }
        
        try
        {
            if( ACTION.equals( "save_edit" ) )
                user_service.update( email, active, first, last, password, role );
            else if( ACTION.equals( "add_user" ) )
            {
                boolean duplicate_user = false;
                for( User user_itr : user_list )
                {
                    if( user_itr.getEmail().equals( email ) )
                    {
                        duplicate_user = true;
                        break;
                    }
                }
                
                if( duplicate_user )
                {
                    serveLists( request, response );
                    printSystemMessage( request, response, String.format( "%s already exists!", email ) );
                    return;
                }
                
                user_service.insert( email, active, first, last, last, role );
            }
        }
        catch( Exception ex )
        {
            Logger.getLogger( UserServlet.class.getName() ).log( Level.SEVERE, null, ex );
        }
        
        serveLists( request, response );
        printSystemMessage( request, response, String.format( "Successfully edited %s", email ) );
    }
}
