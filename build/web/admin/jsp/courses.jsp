<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 7l9-4 9 4-9 4-9-4z"/><path d="M21 10l-9 4-9-4"/><path d="M12 14v7"/></svg>
        Quản lý khóa học
    </h1>
    <div class="card">
        <table class="table">
            <thead>
                <tr>
                    <th>Tên</th>
                    <th>Level</th>
                    <th>Giá</th>
                    <th>Approved</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
            <%
                java.util.List<model.Courses> courses = (java.util.List<model.Courses>) request.getAttribute("courses");
                if (courses == null) courses = java.util.Collections.emptyList();
                for (model.Courses c : courses) {
            %>
                <tr>
                    <td><span style="text-decoration:none;color:inherit;"> <%= c.getName() %> </span></td>
                    <td><%= c.getLevel() %></td>
                    <td><%= c.getPrice() %></td>
                    <td><%= c.isApproved()?"Yes":"No" %></td>
                    <td>
                        <form method="post" action="<%=request.getContextPath()%>/admin/courses" style="display:inline;">
                            <input type="hidden" name="courseID" value="<%= c.getCourseID() %>"/>
                            <input type="hidden" name="action" value="toggleApprove"/>
                            <button class="btn secondary" type="submit"><%= c.isApproved()?"Gỡ":"Duyệt" %></button>
                        </form>
                        <form method="post" action="<%=request.getContextPath()%>/admin/courses" style="display:inline;margin-left:6px;">
                            <input type="hidden" name="courseID" value="<%= c.getCourseID() %>"/>
                            <input type="hidden" name="action" value="delete"/>
                            <button class="btn ghost" type="submit" onclick="return confirm('Xóa khóa học?');">Xóa</button>
                        </form>
                        <a class="btn" style="margin-left:6px;" href="<%=request.getContextPath()%>/admin/course/detail?id=<%= c.getCourseID() %>">Xem chi tiết</a>
                    </td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />

