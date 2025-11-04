<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/><rect x="14" y="14" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/></svg>
        Quản lý Lesson
    </h1>
    <div class="card">
        <table class="table">
            <thead>
                <tr>
                    <th>Tên</th>
                    <th>Mô tả</th>
                    <th>Trạng thái</th>
                    <th>CourseID</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    java.util.List<model.Sections> sections = (java.util.List<model.Sections>) request.getAttribute("sections");
                    if (sections == null) sections = java.util.Collections.emptyList();
                    for (model.Sections s : sections) {
                %>
                <tr>
                    <td><%= s.getName() %></td>
                    <td><%= s.getDescription() %></td>
                    <td><%= s.isStatus()?"Active":"Inactive" %></td>
                    <td><%= s.getCourseID() %></td>
                    <td>
                        <form method="post" action="<%=request.getContextPath()%>/admin/sections" style="display:inline;">
                            <input type="hidden" name="sectionID" value="<%= s.getSectionID() %>"/>
                            <input type="hidden" name="action" value="delete"/>
                            <button class="btn ghost" type="submit" onclick="return confirm('Xóa section?');">Xóa</button>
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

