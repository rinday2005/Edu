<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1>Chi tiết Section</h1>
    <div class="card">
        <%
            model.Sections s = (model.Sections) request.getAttribute("section");
        %>
        <p>Tên: <strong><%= s!=null?s.getName():"" %></strong></p>
        <p>Mô tả: <%= s!=null?s.getDescription():"" %></p>
        <p>Trạng thái: <%= s!=null && s.isStatus()?"Active":"Inactive" %></p>
        <p>CourseID: <%= s!=null?s.getCourseID():null %></p>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />

