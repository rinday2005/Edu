<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 15a4 4 0 0 1-4 4H7l-4 4V7a4 4 0 0 1 4-4h10a4 4 0 0 1 4 4z"/></svg>
        Quản lý Bình luận
    </h1>
    
    <%-- Hiển thị thông báo --%>
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
    
    <%-- Tabs --%>
    <div style="margin-bottom: 20px; border-bottom: 2px solid #e0e0e0;">
        <a href="<%=request.getContextPath()%>/admin/comments?tab=all" 
           style="display: inline-block; padding: 10px 20px; text-decoration: none; color: <%= "all".equals(request.getParameter("tab")) || request.getParameter("tab") == null ? "#007bff" : "#666" %>; border-bottom: 2px solid <%= "all".equals(request.getParameter("tab")) || request.getParameter("tab") == null ? "#007bff" : "transparent" %>; margin-right: 10px;">
            Tất cả
        </a>
        <a href="<%=request.getContextPath()%>/admin/comments?tab=reported" 
           style="display: inline-block; padding: 10px 20px; text-decoration: none; color: <%= "reported".equals(request.getParameter("tab")) ? "#007bff" : "#666" %>; border-bottom: 2px solid <%= "reported".equals(request.getParameter("tab")) ? "#007bff" : "transparent" %>;">
            Đã báo cáo <span style="background: #dc3545; color: white; padding: 2px 6px; border-radius: 10px; font-size: 12px;">
                <%= java.util.stream.Stream.of((java.util.List<java.util.Map<String,Object>>) (request.getAttribute("rows") != null ? request.getAttribute("rows") : java.util.Collections.emptyList()))
                    .flatMap(java.util.Collection::stream)
                    .mapToLong(m -> ((Number)((java.util.Map<String,Object>)m).getOrDefault("pendingReports", 0)).longValue())
                    .sum() %>
            </span>
        </a>
    </div>
    
    <div class="card">
        <table class="table">
            <thead>
                <tr>
                    <th>Nội dung</th>
                    <th>UserID</th>
                    <th>Ngày</th>
                    <th>Báo cáo</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    java.util.List<java.util.Map<String,Object>> rows = (java.util.List<java.util.Map<String,Object>>) request.getAttribute("rows");
                    if (rows == null) rows = java.util.Collections.emptyList();
                    for (java.util.Map<String,Object> r : rows) {
                        long reportCount = ((Number)r.getOrDefault("reportCount", 0)).longValue();
                        long pendingReports = ((Number)r.getOrDefault("pendingReports", 0)).longValue();
                %>
                <tr <%= pendingReports > 0 ? "style='background-color: #fff3cd;'" : "" %>>
                    <td><%= r.get("content") %></td>
                    <td><%= r.get("userID") %></td>
                    <td><%= r.get("createAt") %></td>
                    <td>
                        <% if (pendingReports > 0) { %>
                            <span style="color: #dc3545; font-weight: bold;">⚠️ <%= pendingReports %> báo cáo đang chờ</span>
                        <% } else if (reportCount > 0) { %>
                            <span style="color: #6c757d;"><%= reportCount %> báo cáo (đã xử lý)</span>
                        <% } else { %>
                            <span style="color: #28a745;">✓ Không có báo cáo</span>
                        <% } %>
                    </td>
                    <td>
                        <form method="post" action="<%=request.getContextPath()%>/admin/comments" style="display:inline;">
                            <input type="hidden" name="commentID" value="<%= r.get("commentID") %>"/>
                            <input type="hidden" name="action" value="delete"/>
                            <button class="btn ghost" type="submit" onclick="return confirm('Xóa bình luận?');">Xóa</button>
                        </form>
                        <a class="btn" style="margin-left:6px;" href="<%=request.getContextPath()%>/admin/comment/detail?id=<%= r.get("commentID") %>">Xem chi tiết</a>
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

