<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1>Chi tiết Ví</h1>
    <div class="card">
        <%
            model.Wallet w = (model.Wallet) request.getAttribute("wallet");
        %>
        <p>UserID: <%= w!=null?w.getUserID():null %></p>
        <p>Ngân hàng: <%= w!=null?w.getBankName():"" %></p>
        <p>Số TK: <%= w!=null?w.getBankAccount():"" %></p>
        <p>Số dư: <strong><%= w!=null?w.getBalance():0 %></strong></p>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />

