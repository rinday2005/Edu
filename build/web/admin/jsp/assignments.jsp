<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M9 12h6"/><path d="M9 16h6"/><rect x="4" y="4" width="16" height="16" rx="2"/></svg>
        Quản lý Assignment
    </h1>
    <div class="card">
        <table class="table">
            <thead>
                <tr>
                    <th>Tên</th>
                    <th>Mô tả</th>
                    <th>Thứ tự</th>
                    <th>SectionID</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    java.util.List<model.Assignment> asgs = (java.util.List<model.Assignment>) request.getAttribute("assignments");
                    if (asgs == null) asgs = java.util.Collections.emptyList();
                    for (model.Assignment a : asgs) {
                %>
                <tr>
                    <td><%= a.getName() %></td>
                    <td><%= a.getDescription() %></td>
                    <td><%= a.getOrder() %></td>
                    <td><%= a.getSectionID() %></td>
                    <td>
                        <form method="post" action="<%=request.getContextPath()%>/admin/assignments" style="display:inline;">
                            <input type="hidden" name="assignmentID" value="<%= a.getAssignmentID() %>"/>
                            <input type="hidden" name="action" value="delete"/>
                            <button class="btn ghost" type="submit" onclick="return confirm('Xóa assignment?');">Xóa</button>
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

