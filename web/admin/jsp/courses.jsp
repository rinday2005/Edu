<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 7l9-4 9 4-9 4-9-4z"/><path d="M21 10l-9 4-9-4"/><path d="M12 14v7"/></svg>
        Quản lý khóa học
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
                java.util.List<model.Courses> courses = (java.util.List<model.Courses>) request.getAttribute("courses");
                if (courses == null) courses = java.util.Collections.emptyList();
                // Sắp xếp theo trạng thái duyệt: đã duyệt ở trước
                java.util.Collections.sort(courses, (c1, c2) -> {
                    if (c1.isApproved() == c2.isApproved()) return 0;
                    return c1.isApproved() ? -1 : 1;
                });
                for (model.Courses c : courses) {
                    // Đã duyệt hiển thị màu xanh, chờ duyệt hiển thị màu đỏ (nhưng ở đây chỉ nên hiển thị đã duyệt)
                    String statusColor = c.isApproved() ? "green" : "red";
                    String statusText = c.isApproved() ? "Đã duyệt" : "Chờ duyệt";
            %>
                <tr>
                    <td><span style="text-decoration:none;color:inherit;"> <%= c.getName() %> </span></td>
                    <td><%= c.getLevel() %></td>
                    <td><%= c.getPrice() %></td>
                    <td><span style="color: <%= statusColor %>; font-weight: bold;"><%= statusText %></span></td>
                    <td>
                        <% if (c.isApproved()) { %>
                        <form method="post" action="<%=request.getContextPath()%>/admin/courses" style="display:inline;">
                            <input type="hidden" name="courseID" value="<%= c.getCourseID() %>"/>
                            <input type="hidden" name="action" value="remove"/>
                            <button class="btn secondary" type="submit" onclick="return confirm('Gỡ khóa học khỏi danh sách đã duyệt?');">Gỡ</button>
                        </form>
                        <% } %>
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

