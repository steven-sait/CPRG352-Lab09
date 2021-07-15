<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lab 7</title>
        
        <link rel="stylesheet" href="style.css"/>
    </head>
    <body>
        <div class="body-container">
            <h1>User List</h1>
            <c:if test="${not empty system_message}">
                <p style="color:red;">${system_message}</p>
            </c:if>
            <c:if test="${not empty user_list}">
                <div class="user-list">
                    <table>
                        <thead>
                            <tr>
                                <td>Email</td>
                                <td>First Name</td>
                                <td>Last Name</td>
                                <td>Role</td>
                                <td>Edit</td>
                                <td>Delete</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="user" varStatus="loop" items="${user_list}">
                                <tr>
                                    <td>${user.getEmail()}</td>
                                    <td>${user.getFirstName()}</td>
                                    <td>${user.getLastName()}</td>
                                    <td>${role_list.get(user.getRole() - 1).getName()}</td>
                                    <td><a href="user?action=edit&id=<c:out value='${loop.index}'/>">Edit</a></td>
                                    <td><a href="user?action=delete&id=<c:out value='${loop.index}'/>">Delete</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <div class="user-selection">
                    <c:choose>
                        <c:when test="${not empty action_user}">
                            <h2>Editing <c:out value='${action_user.getEmail()}'/></h2>
                            <form action="user" method="POST">
                                <label for="email">Email: </label>
                                <input type="email" id="email" name="email" value="<c:out value='${action_user.getEmail()}'/>" readonly="readonly"><br>
                                <label for="first">First Name: </label>
                                <input type="text" id="first" name="first" value="<c:out value='${action_user.getFirstName()}'/>" required><br>
                                <label for="last">Last Name: </label>
                                <input type="text" id="last" name="last" value="<c:out value='${action_user.getLastName()}'/>" required><br>
                                <label for="pwd">Password: </label>
                                <input type="password" id="pwd" name="pwd" value="<c:out value='${action_user.getPassword()}'/>" required><br>
                                <label for="role">Role: </label>
                                <select id="role" name="role">
                                    <option value="1"<c:if test="${action_user.getRole() eq 1}">selected</c:if>>system admin</option>
                                    <option value="2"<c:if test="${action_user.getRole() eq 2}">selected</c:if>>regular user</option>
                                    <option value="3"<c:if test="${action_user.getRole() eq 3}">selected</c:if>>company admin</option>
                                </select><br>
                                <input type="checkbox" id="active" name="active" value="active"/>
                                <label for="active">Active </label>
                                <input type="submit" value="Save">
                                <input type="hidden" name="action" value="save_edit">
                            </form>
                        </c:when>
                        <c:otherwise>
                            <h2>Add New User</h2>
                            <form action="user" method="POST">
                                <label for="email">Email: </label>
                                <input type="email" id="email" name="email" pattern="[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*" required><br>
                                <label for="first" required>First Name: </label>
                                <input type="text" id="first" name="first" required><br>
                                <label for="last" required>Last Name: </label>
                                <input type="text" id="last" name="last" required><br>
                                <label for="pwd" required>Password: </label>
                                <input type="password" id="pwd" name="pwd" required><br>
                                <label for="role">Role: </label>
                                <select id="role" name="role">
                                    <option value="1">system admin</option>
                                    <option value="2">regular user</option>
                                    <option value="3">company admin</option>
                                </select><br>
                                <input type="checkbox" id="active" name="active" value="active"/>
                                <label for="active">Active </label>
                                <input type="submit" value="Add User">
                                <input type="hidden" name="action" value="add_user">
                            </form>
                        </c:otherwise>
                    </c:choose>
                </div>
            </c:if>
        </div>
    </body>
</html>