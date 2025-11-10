<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 7l9-4 9 4-9 4-9-4z"/><path d="M21 10l-9 4-9-4"/><path d="M9 17l2 2 4-4"/></svg>
        Duyệt khóa học
    </h1>
    
    <%-- Hiển thị thông báo thành công hoặc lỗi --%>
    <%
        String success = (String) session.getAttribute("success");
        String error = (String) session.getAttribute("error");
        if (success != null) {
            session.removeAttribute("success");
    %>
        <div style="background: #d4edda; color: #155724; padding: 12px; border-radius: 4px; margin-bottom: 16px; border: 1px solid #c3e6cb;">
            ✓ <%= success %>
        </div>
    <%
        }
        if (error != null) {
            session.removeAttribute("error");
    %>
        <div style="background: #f8d7da; color: #721c24; padding: 12px; border-radius: 4px; margin-bottom: 16px; border: 1px solid #f5c6cb;">
            ✗ <%= error %>
        </div>
    <%
        }
    %>
    
    <div class="card">
        <table class="table">
            <thead>
                <tr>
                    <th>Tên</th>
                    <th>Level</th>
                    <th>Giá</th>
                    <th>Trạng thái</th>
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
                    <td><span style="color: red; font-weight: bold;">Chờ duyệt</span></td>
                    <td>
                        <form method="post" action="<%=request.getContextPath()%>/admin/approvals" style="display:inline;">
                            <input type="hidden" name="courseID" value="<%= c.getCourseID() %>"/>
                            <input type="hidden" name="action" value="approve"/>
                            <button class="btn" type="submit">Duyệt</button>
                        </form>
                        <form method="post" action="<%=request.getContextPath()%>/admin/approvals" style="display:inline;margin-left:6px;">
                            <input type="hidden" name="courseID" value="<%= c.getCourseID() %>"/>
                            <input type="hidden" name="action" value="reject"/>
                            <button class="btn ghost" type="submit" onclick="return confirm('Từ chối khóa học này?');">Từ chối</button>
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

