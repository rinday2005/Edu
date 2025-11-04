<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 15a4 4 0 0 1-4 4H7l-4 4V7a4 4 0 0 1 4-4h10a4 4 0 0 1 4 4z"/></svg>
        Quản lý Bình luận
    </h1>
    <div class="card">
        <table class="table">
            <thead>
                <tr>
                    <th>Nội dung</th>
                    <th>UserID</th>
                    <th>Ngày</th>
                    <th>ArticleID</th>
                    <th>LessionID</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    java.util.List<java.util.Map<String,Object>> rows = (java.util.List<java.util.Map<String,Object>>) request.getAttribute("rows");
                    if (rows == null) rows = java.util.Collections.emptyList();
                    for (java.util.Map<String,Object> r : rows) {
                %>
                <tr>
                    <td><%= r.get("content") %></td>
                    <td><%= r.get("userID") %></td>
                    <td><%= r.get("createAt") %></td>
                    <td><%= r.get("articleID") %></td>
                    <td><%= r.get("lessionID") %></td>
                    <td>
                        <form method="post" action="<%=request.getContextPath()%>/admin/comments" style="display:inline;">
                            <input type="hidden" name="commentID" value="<%= r.get("commentID") %>"/>
                            <input type="hidden" name="action" value="delete"/>
                            <button class="btn ghost" type="submit" onclick="return confirm('Xóa bình luận?');">Xóa</button>
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

