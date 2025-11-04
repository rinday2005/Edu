<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1>Chi tiết Bình luận</h1>
    <div class="card">
        <%
            java.util.Map<String,Object> c = (java.util.Map<String,Object>) request.getAttribute("comment");
            if (c == null) c = new java.util.HashMap<>();
        %>
        <p>Nội dung: <strong><%= c.get("content") %></strong></p>
        <p>UserID: <%= c.get("userID") %></p>
        <p>ArticleID: <%= c.get("articleID") %></p>
        <p>LessionID: <%= c.get("lessionID") %></p>
        <p>Ngày: <%= c.get("createAt") %></p>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />

