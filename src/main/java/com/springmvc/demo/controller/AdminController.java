package com.springmvc.demo.controller;

import com.springmvc.demo.dto.ProjectDTO;
import com.springmvc.demo.dto.TaskDTO;
import com.springmvc.demo.dto.UserDTO;
import com.springmvc.demo.services.ProjectManager;
import com.springmvc.demo.services.TaskManager;
import com.springmvc.demo.services.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Martha on 6/20/2016.
 */

/**
 * <p>Only users with role ADMIN specified in the config.properties file have access to this
 * resource. This controller provides all the administrator functionality.</p>
 * <p>Resources</p>
 * <p>{@link #adminHome()}</p>
 * <p>{@link #createUser(String, String)}</p>
 * <p>{@link #register(String, String, String, String, String)}</p>
 * <p>{@link #userDetail(String, String)}</p>
 * <p>{@link #createProject(String)}</p>
 * <p>{@link #projectDetail(String, String)}</p>
 * <p>{@link #modifyProject(String, String, String, String, String)}</p>
 * <p>{@link #createTask(String, String, String)}</p>
 * <p>{@link #taskDetail(String, String, String)}</p>
 * <p>{@link #modifyTask(String, String, String, String, String, String, String, String)}</p>
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    // region Static Strings
    // Keys for passing resource string names
    public static final String USER_DETAIL_RESOURCE = "user_detail_resource";
    public static final String CREATE_USER_RESOURCE = "create_user_resource";
    public static final String USER_RESOURCES_PATH = "user";

    public static final String PROJECT_DETAIL_RESOURCE = "project_detail_resource";
    public static final String CREATE_PROJECT_RESOURCE = "create_project_resource";
    public static final String PROJECT_RESOURCES_PATH = "project";

    public static final String TASK_DETAIL_RESOURCE = "task_detail_resource";
    public static final String CREATE_TASK_RESOURCE = "create_task_resource";
    public static final String TASK_RESOURCES_PATH = "task";

    public static final String HOME = "home";
    public static final String HOME_VALUE = "admin";
    public static final String REDIRECT_MODIFY_TO = "redirect_modify_to";
    public static final String BACK = "back";
    public static final String BUTTON_REDIRECTION_PAGE = "button_redirection_page";
    public static final String MODIFY = "modify";

    // JSP's names
    public static final String ADMIN_PAGE_JSP = "admin_page";
    public static final String CREATE_USER_JSP = "create_user";
    public static final String USER_DETAIL_JSP = "user_detail";
    public static final String CREATE_PROJECT_JSP = "create_project";
    public static final String PROJECT_DETAIL_JSP = "project_detail";
    public static final String CREATE_TASK_JSP = "create_task";
    public static final String TASK_DETAIL_JSP = "task_detail";
    public static final String CREATE_TASK_FROM_PROJECT_JSP = "create_task_from_project";

    // Keys for passing objects
    public static final String USERS = "users";
    public static final String PROJECTS = "projects";
    public static final String TASKS = "tasks";

    public static final String ROLES = "roles";
    public static final String USER = "user";
    public static final String USERID = "userId";
    public static final String USERIDFORUSERDT = "userIdForUserTD";
    public static final String USERNAME = "username";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String ROLE = "role";

    public static final String PROJECT = "project";
    public static final String PROJECTID = "projectId";
    public static final String PROJECT_STORY = "project_story";
    public static final String PROJECT_DESCRIPTION = "project_description";
    public static final String PROJECT_TASKS = "project_tasks";

    public static final String TASK = "task";
    public static final String TASKID = "taskId";
    public static final String TASK_STORY = "task_story";
    public static final String TASK_DESCRIPTION = "task_description";

    public static final String MAP = "map";

    // Error handling
    public static final String ERROR = "error";

    // Labels
    public static final String BUTTON_LABEL = "button_label";

    // endregion

    // region Beans
    @Autowired
    ProjectManager projectManager;

    @Autowired
    TaskManager taskManager;

    @Autowired
    UserManager userManager;

    @Autowired
    Environment environment;
    // endregion

    // region Home
    /**
     * A home page of "ADMIN" module. Displays all projects, tasks and users lists..
     * Only users with role USER specified in config.properties file are included in the returned in users list.
     * @return
     * <p>Admin home modelAndView with {@link #ADMIN_PAGE_JSP} JSP view.</p>
     * <p>Model object under {@link #PROJECTS} key - all projects list from DB.</p>
     * <p>Model object under {@link #PROJECT_DETAIL_RESOURCE} key with resource method {@link #projectDetail}- direction path to project detail page.
     *      Is called when a click on project name occur.</p>
     * <p>Model object under {@link #CREATE_PROJECT_RESOURCE} key with resource method {@link #createProject}- direction path to project creation form page.
     *      Is called when a click on "Create project" button occur.</p>
     * <p>Model object under {@link #TASKS} key - all tasks list from DB.</p>
     * <p>Model object under {@link #TASK_DETAIL_RESOURCE} key with resource method {@link #taskDetail}- direction path to task detail page.
     *      Is called when a click on task name occur.</p>
     * <p>Model object under {@link #CREATE_TASK_RESOURCE} key with resource method {@link #createTask}- direction path to task creation form page.
     *      Is called when a click on "Create task" button occur.</p>
     * <p>Model object under {@link #USERS} key - all users list with role type of "USER" under {@link #USERS} key  from DB.</p>
     * <p>Model object under {@link #USER_DETAIL_RESOURCE} key with resource method {@link #userDetail}- direction path to user detail page.
     *      Is called when a click on user name occur.</p>
     * <p>Model object under {@link #CREATE_USER_RESOURCE} key with resource method {@link #createUser}- direction path to user creation form page.
     *      Is called when a click on "Create user" button occur.</p>
     * <p>Model object under {@link #HOME} key - base URL path for "admin" module</p>
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView adminHome(){
        ModelAndView modelAndView = new ModelAndView();
        // Add project list and related resource paths.
        modelAndView.addObject(PROJECTS, projectManager.allProjects());
        modelAndView.addObject(PROJECT_DETAIL_RESOURCE, PROJECT_RESOURCES_PATH);
        modelAndView.addObject(CREATE_PROJECT_RESOURCE, PROJECT_RESOURCES_PATH);

        // Add task list and related resource paths.
        modelAndView.addObject(TASKS, taskManager.allTasks());
        modelAndView.addObject(TASK_DETAIL_RESOURCE, TASK_RESOURCES_PATH);
        modelAndView.addObject(CREATE_TASK_RESOURCE, TASK_RESOURCES_PATH);
        modelAndView.addObject(BACK, "admin");

        // Add user list and related resource paths.
        modelAndView.addObject(USERS, userManager.allUsersByRole(environment.getProperty("role_user")));
        modelAndView.addObject(USER_DETAIL_RESOURCE, USER_RESOURCES_PATH);
        modelAndView.addObject(CREATE_USER_RESOURCE, USER_RESOURCES_PATH);

        // Set home resource name.
        modelAndView.addObject(HOME, HOME_VALUE);

        // Set current view name.
        modelAndView.setViewName(ADMIN_PAGE_JSP);

        return modelAndView;
    }
    // endregion

    // region User
    /**
     * Provides a JSP form view for user creation.
     * @param request where
     * <p>{@link #ERROR} is error message which will appear if page was redirected to current in due to empty fields error.</p>
     * @return
     * <p>ModelAndView object with JSP view named "create_user" under {@link #CREATE_USER_JSP} key.</p>
     * <p>Model object under {@link #ROLES} key - List of existing role types in DB.</p>
     * <p>Model object under {@link #HOME} key - base URL path for "admin" module</p>
     * <p>Model object under {@link #MODIFY} key - universal mid- resource which is called when ""Submit" is pressed.
     * In this case it is {@link #register} method</p>
     * <p>Model object under {@link #ERROR} key - Error message in case of to redirection to the same page.</p>
     * see {@link #register}</p>
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public ModelAndView createUser(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        String receivedError = request.getParameter(ERROR);
        modelAndView.addObject(ROLES, userManager.allRoles());
        modelAndView.addObject(HOME,HOME_VALUE);
        modelAndView.addObject(MODIFY, "user");
        modelAndView.addObject(ERROR, receivedError);
        modelAndView.setViewName(CREATE_USER_JSP);
        return modelAndView;
    }

    /**
     *  <p>"Submit" in {@link #createUser}  resource invokes
     *  a call to a mid resource {@link #register} under {@link #MODIFY} key.</p>
     *  <p>{@link #register} doesn't hold view, it is only a mid resource to invoke user creation business logic holder method
     *  and redirect context to resource page under {@link #REDIRECT_MODIFY_TO} key</p>
     *  @param request where
     *  <p>receivedUsername -  username from {@link #createUser} filled form, NotNull and mail type field. Provided by user.</p>
     *  <p>receivedName -  name of user to be displayed, NotNull field.  Provided by user.</p>
     *  <p>receivedPassword -  can have nay length can hold eny character.  Provided by user.</p>
     *  <p>receivedRole -  to be choose from drop down list, NotNull field.  Provided by user.</p>
     *  <p>receivedHome -  Home redirection URL.</p>
     *  @return If username exist in DB a redirection will be made back to {@link #createUser} resource else back to {@link #adminHome()} homepage.
     */
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String register(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String receivedUsername = request.getParameter(USERNAME);
        String receivedName = request.getParameter(NAME);
        String receivedPassword = request.getParameter(PASSWORD);
        String receivedRole = request.getParameter(ROLE);
        if(receivedUsername.isEmpty() || receivedName.isEmpty() || receivedPassword.isEmpty()
                || receivedRole.isEmpty()){

            redirectAttributes.addAttribute("error", "Please fill all fields!");
            return "redirect:" + request.getHeader("Referer");
        }
        if(userManager.getUserByUsername(receivedUsername) != null) {
            redirectAttributes.addAttribute("error", "User already exists!");
            return "redirect:" + request.getHeader("Referer");
        }
        userManager.addUser(receivedName, receivedUsername, receivedPassword, true, receivedRole);
        return "redirect:/" + HOME_VALUE;
    }

    /**
     * Provides a JSP form view to display existing specified user detail.
     * Is invoked by clicking on the name of user from users list in {@link #adminHome} URL.
     * @param userId - requested user id as a path variable
     * @return
     * <p>ModelAndView object with JSP view named "user_detail" under {@link #USER_DETAIL_JSP} key.</p>
     * <p>Model object under {@link #USER} key - user under {@link #USERID} from DB.</p>
     * <p>Model object under {@link #MAP} key - Each key presents a project, value presents an ArrayList of task
     * within the project that are assigned to current user. Map doesn't contain projects where tasks assigned to current user doesn't exist</p>
     * <p>Model object under {@link #TASK_DETAIL_RESOURCE} key with resource value {@link #taskDetail}-
     * For user to be able to navigate to task detail page from user detail page.</p>
     * <p>Model object under {@link #BUTTON_LABEL} key represents label of submit button on user detail page.
     * <p>Model object under {@link #BUTTON_REDIRECTION_PAGE} key navigaets back to home page.
     * <p>Model object under {@link #BACK} key - a hint URL path for the page {@link #TASK_DETAIL_RESOURCE}
     * to be redirected. For example if task detail was navigated from user detail page, task detail page
     * submit should navigate back to user detail page.</p>
     * <p>Model object under {@link #HOME} key - base URL path for "admin" module</p>
     */
    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ModelAndView userDetail(@PathVariable("userId") String userId){
        ModelAndView modelAndView = new ModelAndView();
        UserDTO user = userManager.getUserById(userId);
        HashMap<String, ArrayList<TaskDTO>>  map = taskManager.userTasksMap(userId);
        if(map.size() == 0){
        }
        modelAndView.addObject(USER, user);
        modelAndView.addObject(MAP, map);
        modelAndView.addObject(TASK_DETAIL_RESOURCE, TASK_RESOURCES_PATH);
        modelAndView.addObject(BUTTON_LABEL, "SUBMIT");

        modelAndView.addObject(BACK, ("/" + USER_RESOURCES_PATH + "/" + userId));
        modelAndView.addObject(HOME, HOME_VALUE);
        modelAndView.addObject(BUTTON_REDIRECTION_PAGE, HOME_VALUE);

        modelAndView.setViewName(USER_DETAIL_JSP);
        return modelAndView;
    }
    // endregion

    // region Project
    /**
     * Provides a JSP form view for project creation.
     * @param request is used to receive error message in case of error redirection.
     * @return
     * <p>ModelAndView object with JSP view named "create_project" under {@link #CREATE_PROJECT_JSP} key.</p>
     * <p>Model object under {@link #HOME} key - base URL path for "admin" module</p>
     * <p>Model object under {@link #MODIFY} key - universal mid- resource which is called when ""Submit" is pressed.
     * see {@link #modifyProject}</p>
     * <p>Model object under {@link #ERROR} key - error message if error redirection cased by empty fields took place.</p>
     */
    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ModelAndView createProject(HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        String receivedError = request.getParameter(ERROR);
        modelAndView.addObject(ERROR, receivedError);
        modelAndView.addObject(HOME, HOME_VALUE);
        modelAndView.addObject(MODIFY, PROJECT_RESOURCES_PATH);

        modelAndView.setViewName(CREATE_PROJECT_JSP);

        return modelAndView;
    }

    /**
     * Provides a JSP form view to display existing specified project detail.
     * Is invoked by clicking on the name of project from project list in {@link #adminHome} URL.
     * @param projectId Id of project under {@link #PROJECTID} on which Story a click has occur.
     * @param request is used to receive error message in case of error redirection.
     * @return
     * <p>ModelAndView object with JSP view named "project_detail" under {@link #PROJECT_DETAIL_JSP} key.</p>
     * <p>Model object under {@link #PROJECT} key - project under {@link #PROJECTID} from DB.</p>
     * <p>Model object under {@link # PROJECT_TASKS} key - Tasks list assigned to this project</p>
     * <p>Model object under {@link #TASK_DETAIL_RESOURCE} key with resource value {@link #taskDetail}-
     * For user to be able to navigate to task detail page from project detail page.</p>
     * <p>Model object under {@link #CREATE_TASK_RESOURCE} key with resource value {@link #createTask}-
     * For user to be able to navigate to create task page and assign new task to this project</p>
     * <p>Model object under {@link #HOME} key - base URL path for "admin" module</p>
     * <p>Model object under {@link #MODIFY} key - universal mid- resource which is called when ""Submit" is pressed.
     * see {@link #modifyProject}</p>
     * <p>Model object under {@link #REDIRECT_MODIFY_TO} key - URL path for the page from where {@link #projectDetail} resource was called</p>
     * <p>Model object under {@link #BACK} key - a hint URL path for the pages {@link #TASK_DETAIL_RESOURCE}
     * and {@link #CREATE_TASK_RESOURCE} to be redirected. For example if task detail was navigated from project detail page, task detail page
     * submit should navigate back to project detail page.</p>
     */
    @RequestMapping(value = "/project/{projectId}", method = RequestMethod.GET)
    public ModelAndView projectDetail(@PathVariable("projectId") String projectId, HttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();

        String recievedError = request.getParameter(ERROR);

        ProjectDTO project = projectManager.getProjectById(projectId);
        Collection<TaskDTO> tasks = taskManager.getTasksWithinProject(projectId);

        modelAndView.addObject(PROJECT, project);
        modelAndView.addObject(PROJECT_TASKS, tasks);

        modelAndView.addObject(TASK_DETAIL_RESOURCE, TASK_RESOURCES_PATH);
        modelAndView.addObject(CREATE_TASK_RESOURCE, PROJECT_RESOURCES_PATH + "/" + projectId + "/" + "new_task");

        modelAndView.addObject(HOME, HOME_VALUE);
        modelAndView.addObject(ERROR, recievedError);
        modelAndView.addObject(MODIFY, PROJECT_RESOURCES_PATH);
        modelAndView.addObject(BACK, "/" + PROJECT_RESOURCES_PATH + "/" + projectId);
        modelAndView.setViewName(PROJECT_DETAIL_JSP);
        return modelAndView;
    }

    /**
     *  <p>"Submit" in a {@link #createProject}  or {@link #projectDetail} resources invokes
     *  a call to a mid resource {@link #modifyProject} under {@link #MODIFY} key.</p>
     *  <p>{@link #modifyProject} doesn't hold view, it is only a mid resource to invoke project creation or modification
     *  business logic holder method and redirect context to resource page under {@link #REDIRECT_MODIFY_TO} key</p>
     *  @param projectStory Story from {@link #createProject} filled form or modified-yes/no value from {@link #projectDetail} form, NotNull field.
     *  @param project_description description from {@link #createProject} filled form or modified-yes/no value from {@link #projectDetail} form, NotNull field.
     *  @param id Id of project under {@link #PROJECTID} on which Story a click has occur
     *  @param home Home redirection URL.
     *  @param redirect_modify_to under {@link #REDIRECT_MODIFY_TO} key - URL path for the page from where {@link #createProject} or {@link #projectDetail} resource was called</p>
     *  @return
     *  <p>ModelAndView object with JSP view under {@link #REDIRECT_MODIFY_TO} key.</p>
     */
    @RequestMapping(value = "/project", method = RequestMethod.POST)
    public String modifyProject(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String project_story = request.getParameter(PROJECT_STORY);
        String project_description = request.getParameter(PROJECT_DESCRIPTION);
        String projectid = request.getParameter(PROJECTID);

        if(project_story == null || project_story.isEmpty()
                || project_description == null || project_description.isEmpty()){
            redirectAttributes.addAttribute("error", "Please fill all fields!");
            return "redirect:" + request.getHeader("Referer");
        }
        projectManager.addOrUpdateProject(projectid, project_story, project_description);
        return "redirect:/" + HOME_VALUE ;
    }

    @RequestMapping(value = "/project/{projectId}/new_task", method = RequestMethod.GET)
    public ModelAndView createTaskForProject(@PathVariable("projectId") String projectId, HttpServletRequest request){

        String receivedError = request.getParameter(ERROR);
        ModelAndView modelAndView = new ModelAndView();

        Collection<UserDTO> users = userManager.allUsersByRole(environment.getProperty("role_user"));
        ProjectDTO project = projectManager.getProjectById(projectId);
        String modify = PROJECT_RESOURCES_PATH + "/" + projectId + "/" + "new_task";
        modelAndView.addObject(ERROR, receivedError);
        modelAndView.addObject(USERS, users);
        modelAndView.addObject(PROJECT, project);
        modelAndView.addObject(MODIFY, modify);
        modelAndView.addObject(HOME, HOME_VALUE);
        modelAndView.setViewName(CREATE_TASK_FROM_PROJECT_JSP);

        return modelAndView;
    }

    @RequestMapping(value = "/project/{projectId}/new_task", method = RequestMethod.POST)
    public String commitTaskFromProject(HttpServletRequest request, RedirectAttributes redirectAttributes, @PathVariable("projectId") String projectId){
        String task_story = request.getParameter(TASK_STORY);
        String task_description = request.getParameter(TASK_DESCRIPTION);
        String userid = request.getParameter(USERID);
        if(task_story.isEmpty() || task_description.isEmpty() || userid == null|| userid.isEmpty()){
            redirectAttributes.addAttribute("error", "Please fill all fields!");
            return "redirect:" + request.getHeader("Referer");
        }
        taskManager.addOrModifyTask("", task_story, task_description, projectId, userid);

        return "redirect:/" + HOME_VALUE + "/" + PROJECT_RESOURCES_PATH + "/" + projectId;
    }

    // endregion

    // region Task
    /**
     * Provides a JSP form view for task creation.
     * @param projectId Id of project under {@link #PROJECTID}, this field has value only if task creation is called from {@link #projectDetail} page.
     *                  It represent id of the project.
     * @param redirect_modify_task_to - a hint URL path under {@link #BACK} key , for {@link #modifyTask} to be redirected,
     *                                key keeps the path of page fom where current {@link #createTask} was called.
     * @param home Home redirection URL.
     * @return
     * <p>ModelAndView object with JSP view. If this resource was called from {@link #adminHome()}" page than a view named
     * "create_task" under {@link #CREATE_TASK_JSP} key will be returned.If this resource was called from {@link #projectDetail}}" page
     * than a view named "create_task_from_project" under {@link #CREATE_TASK_FROM_PROJECT_JSP} key will be returned.</p>
     * <p>Model object under {@link #USERS} key - all users list with role type of "USER" from DB.</p>
     * <p>Model object under {@link #PROJECTS} key - all projects list from DB. This field will be instantiated only if
     * this resource was called from {@link #adminHome()} page</p>
     * <p>Model object under {@link #PROJECT} key - ProjectDTO object under {@link #PROJECTID} key. This field will be instantiated only if
     * this resource was called from {@link #projectDetail} page.</p>
     * see {@link #modifyProject}</p>
     * <p>Model object under {@link #MODIFY} key - universal mid- resource which is called when ""Submit" is pressed.
     * <p>Model object under {@link #REDIRECT_MODIFY_TO} key - URL path for the page from where {@link #createTask} resource was called</p>
     * <p>Model object under {@link #HOME} key - base URL path for "admin" module</p>
     */
    @RequestMapping(value = "/task", method = RequestMethod.GET)
    public ModelAndView createTask(HttpServletRequest request){
        String recieved_projectid = request.getParameter(PROJECTID);
        String received_redirect_modify_task_to = request.getParameter(BACK);
        String recievedError = request.getParameter(ERROR);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(ERROR, recievedError);

        Collection<UserDTO> users = userManager.allUsersByRole(environment.getProperty("role_user"));
        modelAndView.addObject(USERS, users);

        if(recieved_projectid == null || recieved_projectid.isEmpty()){
            Collection<ProjectDTO> projects = projectManager.allProjects();
            modelAndView.addObject(PROJECTS, projects);
            modelAndView.setViewName(CREATE_TASK_JSP);
        } else {
            ProjectDTO project = projectManager.getProjectById(recieved_projectid);
            modelAndView.addObject(PROJECT, project);
            modelAndView.setViewName(CREATE_TASK_FROM_PROJECT_JSP);
        }

        modelAndView.addObject(REDIRECT_MODIFY_TO, received_redirect_modify_task_to);
        modelAndView.addObject(MODIFY, TASK_RESOURCES_PATH);
        modelAndView.addObject(HOME, HOME_VALUE);

        return modelAndView;
    }


    /**
     * Provides a JSP form view to display existing specified task detail.
     * Is invoked by clicking on the name of task from tasks list either from {@link #adminHome} or from {@link #projectDetail}
     * or from {@link #userDetail} URLs.
     * @param taskId Id of task under {@link #TASKID} on which Story a click has occur.
     * @param redirect_modify_task_to - a hint URL path under {@link #BACK} key , for {@link #modifyTask} to be redirected,
     *                                key keep the path of page fom where current {@link #taskDetail} was called.
     * @param home Home redirection URL.
     * @return
     * <p>ModelAndView object with JSP view named "task_detail" under {@link #TASK_DETAIL_JSP} key.</p>
     * <p>Model object under {@link #USERS} key - all users list with role type of "USER" under {@link #USERS} key  from DB.</p>
     * <p>Model object under {@link #TASK} key - The task object from DB</p>
     * <p>Model object under {@link #HOME} key - base URL path for "admin" module</p>
     * <p>Model object under {@link #MODIFY} key - universal mid- resource which is called when ""Submit" is pressed.
     * Eee {@link #modifyTask} resource</p>
     * <p>Model object under {@link #REDIRECT_MODIFY_TO} key - URL path for the page from where {@link #taskDetail} resource was called</p>
     * <p>Model object under {@link #BACK} key - a hint URL path for the pages {@link #TASK_DETAIL_RESOURCE}
     * and {@link #CREATE_TASK_RESOURCE} to be redirected. For example if task detail was navigated from project detail page, task detail page
     * submit should navigate back to project detail page.</p>
     */
    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.GET)
    public ModelAndView taskDetail(HttpServletRequest request, @PathVariable("taskId") String taskId, @RequestParam(value="back", required=false) String back){
        ModelAndView modelAndView = new ModelAndView();
        String receivedError = request.getParameter(ERROR);

        TaskDTO task = taskManager.getTaskById(taskId);
        Collection<UserDTO> users = userManager.allUsersByRole(environment.getProperty("role_user"));

        modelAndView.addObject(USERS, users);
        modelAndView.addObject(TASK, task);
        modelAndView.addObject(HOME, HOME_VALUE);
        modelAndView.addObject(MODIFY, TASK_RESOURCES_PATH);
        modelAndView.addObject(BACK, back);
        modelAndView.addObject(ERROR, receivedError);
        modelAndView.setViewName(TASK_DETAIL_JSP);
        return modelAndView;
    }

    /**
     *  <p>"Submit" in a {@link #createTask}  or {@link #taskDetail} resources invokes
     *  a call to a mid resource {@link #modifyTask} under {@link #MODIFY} key.</p>
     *  <p>{@link #modifyTask} doesn't hold view, it is only a mid resource to invoke task creation or modification
     *  business logic holder method and redirect context to resource page under {@link #REDIRECT_MODIFY_TO} key</p>
     *  @param taskStory Story from {@link #createTask} filled form or modified-yes/no value from {@link #taskDetail} form, NotNull field.
     *  @param taskDescription description from {@link #createTask} filled form or modified-yes/no value from {@link #taskDetail} form, NotNull field.
     *  @param taskId Id of task under {@link #TASKID} on which Story a click has occur, this field can be empty if resource call was made
     *                to by submitting "Create/Add task" buttons either from {@link #adminHome()} or from {@link #projectDetail}.
     *  @param projectId Id of project under {@link #PROJECTID}, this field can be empty only if provided param {@link #TASKID} has value.
     *  @param userId Id of user under {@link #USERID}, this field can be empty only if provided param {@link #TASKID} has value.
     *  @param redirect_modify_to under {@link #REDIRECT_MODIFY_TO} key - URL path for the page from where {@link #createTask} or {@link #taskDetail} resource was called</p>
     *  @param home Home redirection URL.
     *  @return
     *  <p>ModelAndView object with JSP view under {@link #REDIRECT_MODIFY_TO} key.</p>
     */
    @RequestMapping(value = "/task", method = RequestMethod.POST)
    public String modifyTask(HttpServletRequest request, RedirectAttributes redirectAttributes){
        String task_story = request.getParameter(TASK_STORY);
        String task_description = request.getParameter(TASK_DESCRIPTION);
        String taskid = request.getParameter(TASKID);
        String projectid = request.getParameter(PROJECTID);
        String userid = request.getParameter(USERID);
        String back = request.getParameter(BACK);
        if(task_story.isEmpty() || task_description.isEmpty() || projectid == null || projectid.isEmpty()){
            redirectAttributes.addAttribute("error", "Please fill all fields!");
            return "redirect:" + request.getHeader("Referer");
        }
        taskManager.addOrModifyTask(taskid, task_story, task_description, projectid, userid);

        if(back == null || back.isEmpty()) return "redirect:/" + HOME_VALUE;
        return "redirect:/" + HOME_VALUE + back;
    }
    // endregion

}
