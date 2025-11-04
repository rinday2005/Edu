<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1>Chi tiết Assignment</h1>
    <div class="card">
        <%
            model.Assignment a = (model.Assignment) request.getAttribute("assignment");
        %>
        <p>Tên: <strong><%= a!=null?a.getName():"" %></strong></p>
        <p>Mô tả: <%= a!=null?a.getDescription():"" %></p>
        <p>Order: <%= a!=null?a.getOrder():0 %></p>
        <p>SectionID: <%= a!=null?a.getSectionID():null %></p>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />

