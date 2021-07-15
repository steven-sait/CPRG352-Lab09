package dataaccess;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import models.User;

public class UserDB
{
    public List<User> getAll() throws Exception
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try
        {
            List<User> users = em.createNamedQuery( "User.findAll", User.class ).getResultList();
            return users;
        }
        finally
        {
            em.close();
        }
    }
    
    public User get( String email ) throws Exception
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        try
        {
            User user = em.find( User.class, email );
            return user;
        }
        finally
        {
            em.close();
        }
    }
    
    public void insert( User user )
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction tr = em.getTransaction();
        try
        {
            tr.begin();
            {
                em.persist( user );
                em.merge( user );
            }
            tr.commit();
        }
        catch( Exception ex )
        {
            tr.rollback();
        }
        finally
        {
            em.close();
        }
    }
    
    public void update( User new_user )
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction tr = em.getTransaction();
        
        try
        {
            tr.begin();
            em.merge( new_user );
            tr.commit();
        }
        catch( Exception ex )
        {
            tr.rollback();
        }
        finally
        {
            em.close();
        }
    }
    
    public void delete( String email )
    {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction tr = em.getTransaction();

        try
        {
            //User user = em.createNamedQuery( "User.findByEmail", User.class ).setParameter( "email", email ).getSingleResult();
            User user = em.find( User.class, email );      
            tr.begin();
            em.remove( em.merge( user ) );
            tr.commit();
        }
        catch( Exception ex )
        {
            tr.rollback();
        }
        finally
        {
            em.close();
        }
    }
}