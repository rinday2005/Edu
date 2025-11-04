<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1>Giao dịch UserCourse</h1>
    <div class="card">
        <table class="table">
            <thead><tr><th>TransactionID</th><th>UserID</th><th>CourseID</th></tr></thead>
            <tbody>
            <%
                java.util.List<java.util.Map<String,Object>> rows = (java.util.List<java.util.Map<String,Object>>) request.getAttribute("rows");
                if (rows == null) rows = java.util.Collections.emptyList();
                for (java.util.Map<String,Object> r : rows) {
            %>
                <tr>
                    <td><%= r.get("userCourseID") %></td>
                    <td><%= r.get("userID") %></td>
                    <td><%= r.get("courseID") %></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />

