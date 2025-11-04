<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 7l9-4 9 4-9 4-9-4z"/><path d="M21 10l-9 4-9-4"/><path d="M9 17l2 2 4-4"/></svg>
        Duyệt khóa học
    </h1>
    <div class="card">
        <table class="table">
            <thead>
                <tr>
                    <th>Tên</th>
                    <th>Level</th>
                    <th>Giá</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    java.util.List<model.Courses> pending = (java.util.List<model.Courses>) request.getAttribute("pendingCourses");
                    if (pending == null) pending = java.util.Collections.emptyList();
                    for (model.Courses c : pending) {
                %>
                <tr>
                    <td><%= c.getName() %></td>
                    <td><%= c.getLevel() %></td>
                    <td><%= c.getPrice() %></td>
                    <td>
                        <form method="post" action="<%=request.getContextPath()%>/admin/approvals" style="display:inline;">
                            <input type="hidden" name="courseID" value="<%= c.getCourseID() %>"/>
                            <input type="hidden" name="action" value="approve"/>
                            <button class="btn" type="submit">Duyệt</button>
                        </form>
                        <form method="post" action="<%=request.getContextPath()%>/admin/approvals" style="display:inline;margin-left:6px;">
                            <input type="hidden" name="courseID" value="<%= c.getCourseID() %>"/>
                            <input type="hidden" name="action" value="reject"/>
                            <button class="btn ghost" type="submit">Từ chối</button>
                        </form>
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

