<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1>Chi tiết Bài viết</h1>
    <div class="card">
        <%
            model.Article art = (model.Article) request.getAttribute("article");
        %>
        <p>Tiêu đề: <strong><%= art!=null?art.getTitle():"" %></strong></p>
        <p>Trạng thái: <%= art!=null?art.getStatus():"" %></p>
        <p>Ngày tạo: <%= art!=null?art.getCreateAt():null %></p>
        <p>Nội dung:</p>
        <div class="card"><%= art!=null?art.getContent():"" %></div>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />

