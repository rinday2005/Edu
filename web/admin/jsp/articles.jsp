<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M4 4h16v16H4z"/><path d="M8 8h8M8 12h8M8 16h5"/></svg>
        Quản lý Bài viết
    </h1>
    <div class="card">
        <table class="table">
            <thead>
                <tr>
                    <th>Tiêu đề</th>
                    <th>Trạng thái</th>
                    <th>Ngày tạo</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    java.util.List<model.Article> articles = (java.util.List<model.Article>) request.getAttribute("articles");
                    if (articles == null) articles = java.util.Collections.emptyList();
                    for (model.Article a : articles) {
                %>
                <tr>
                    <td><%= a.getTitle() %></td>
                    <td><%= a.getStatus() %></td>
                    <td><%= a.getCreateAt() %></td>
                    <td>
                        <form method="post" action="<%=request.getContextPath()%>/admin/articles" style="display:inline;">
                            <input type="hidden" name="articleID" value="<%= a.getArticleID() %>"/>
                            <input type="hidden" name="action" value="<%= (a.getStatus()!=null && a.getStatus().equals("hidden"))?"show":"hide" %>"/>
                            <button class="btn secondary" type="submit"><%= (a.getStatus()!=null && a.getStatus().equals("hidden"))?"Hiện":"Ẩn" %></button>
                        </form>
                        <form method="post" action="<%=request.getContextPath()%>/admin/articles" style="display:inline;margin-left:6px;">
                            <input type="hidden" name="articleID" value="<%= a.getArticleID() %>"/>
                            <input type="hidden" name="action" value="delete"/>
                            <button class="btn ghost" type="submit" onclick="return confirm('Xóa bài viết?');">Xóa</button>
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

