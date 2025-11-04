<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1>Nhật ký hoạt động (tổng hợp)</h1>
    <div class="card">
        <table class="table">
            <thead><tr><th>Bảng</th><th>EntityID</th><th>Thời gian</th><th>ByUserID</th></tr></thead>
            <tbody>
            <%
                java.util.List<java.util.Map<String,Object>> logs = (java.util.List<java.util.Map<String,Object>>) request.getAttribute("logs");
                if (logs == null) logs = java.util.Collections.emptyList();
                for (java.util.Map<String,Object> r : logs) {
            %>
                <tr>
                    <td><%= r.get("tableName") %></td>
                    <td><%= r.get("entityID") %></td>
                    <td><%= r.get("ts") %></td>
                    <td><%= r.get("byId") %></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />

