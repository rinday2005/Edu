<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="2" y="7" width="20" height="14" rx="2"/><path d="M16 7V5a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2v2"/><circle cx="16" cy="14" r="2"/></svg>
        Ví người dùng
    </h1>
    <div class="card">
        <table class="table">
            <thead><tr><th>UserID</th><th>Ngân hàng</th><th>Số TK</th><th>Số dư</th><th>Actions</th></tr></thead>
            <tbody>
            <%
                java.util.List<model.Wallet> wallets = (java.util.List<model.Wallet>) request.getAttribute("wallets");
                if (wallets == null) wallets = java.util.Collections.emptyList();
                for (model.Wallet w : wallets) {
            %>
                <tr>
                    <td><%= w.getUserID() %></td>
                    <td><%= w.getBankName() %></td>
                    <td><%= w.getBankAccount() %></td>
                    <td><strong><%= w.getBalance() %></strong></td>
                    <td></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />

