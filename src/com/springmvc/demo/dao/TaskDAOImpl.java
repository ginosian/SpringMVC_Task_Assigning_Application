package com.springmvc.demo.dao;

import com.springmvc.demo.dto.ProjectDTO;
import com.springmvc.demo.dto.TaskDTO;
import com.springmvc.demo.dto.UserDTO;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * Created by Martha on 6/14/2016.
 */
@Repository
public class TaskDAOImpl implements TaskDAO {

    @Autowired
    SessionFactory sessionFactory;

    private Session openSession() {
        return sessionFactory.openSession();
    }

    @Override
    public TaskDTO getTaskById(int id) {
        Session session = openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Query query = openSession().createQuery("from TaskDTO task where task.id = :id");
            query.setParameter("id", id);
            List<TaskDTO> taskDTOLIST = query.list(); // TODO check if return type match , ask Lyov if why not to close session
            transaction.commit();
            if (taskDTOLIST.size() == 0)return null;
            return taskDTOLIST.get(0);
        }catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public void addTask(TaskDTO taskDTO) {
        Session session = openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            session.save(taskDTO);
            transaction.commit();
        }catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Override
    public TaskDTO reassignTask(TaskDTO taskDTO, UserDTO newAssignee) {
        return null;
    }

    @Override
    public TaskDTO markTaskAsComplete() {
        return null;
    }

    @Override
    public Collection<TaskDTO> allTasks(boolean complete) {
        Session session = openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            List<TaskDTO> projects = session.createQuery("from TaskDTO").list();
            transaction.commit();
            if(projects.size() == 0)return null;
            return projects;
        }catch (HibernateException e){
            if(transaction != null)transaction.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Collection<TaskDTO> getTasksWithinProjects(ProjectDTO projectDTO) {
        String project = projectDTO.getStory();
        Session session = openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Query query = openSession().createQuery("select task from TaskDTO task join task.projectDTO project where project.story = :story");
            query.setParameter("story", project);
            Collection<TaskDTO> taskDTOList = query.list();
            transaction.commit();
            if (taskDTOList.size() == 0)return null;
            return taskDTOList;
        }catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Collection<TaskDTO> getTaskByUser(UserDTO userDTO) {
        String user = userDTO.getName();
        Session session = openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Query query = openSession().createQuery("select task from TaskDTO task join task.userDTO assignee where assignee.name = :name");
            query.setParameter("name", user);
            Collection<TaskDTO> taskDTOList = query.list();
            transaction.commit();
            if (taskDTOList.size() == 0)return null;
            return taskDTOList;
        }catch (HibernateException e) {
            if (transaction!=null) transaction.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public TaskDTO getTaskByStory(String story) {
        Session session = openSession();
        Transaction transaction = null;
        try{
            transaction = session.beginTransaction();
            Query query = session.createQuery("from TaskDTO task where task.story = :story");
            query.setParameter("story", story);
            List<TaskDTO> task = query.list();
            if(task.size() == 0) return null;
            return task.get(0);
        }catch (HibernateException e){
            if(transaction != null)transaction.rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }
}
