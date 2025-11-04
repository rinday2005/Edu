<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1>Chi tiết người dùng</h1>
    <div class="cards">
        <div class="card">
            <h3>Thông tin</h3>
            <p>Họ tên: <strong><%= ((model.User)request.getAttribute("user")).getFullName() %></strong></p>
            <p>Email: <%= ((model.User)request.getAttribute("user")).getEmail() %></p>
            <p>Role: <%= ((model.User)request.getAttribute("user")).getRole() %></p>
            <p>Trạng thái: <%= ((model.User)request.getAttribute("user")).getStatus()?"Active":"Locked" %></p>
        </div>
        <div class="card">
            <h3>Ví</h3>
            <%
                model.Wallet w = (model.Wallet) request.getAttribute("wallet");
                if (w != null) {
            %>
            <p>Ngân hàng: <%= w.getBankName() %></p>
            <p>Số TK: <%= w.getBankAccount() %></p>
            <p>Số dư: <strong><%= w.getBalance() %></strong></p>
            <%
                } else {
            %>
            <p>Chưa có ví.</p>
            <%
                }
            %>
        </div>
    </div>

    <div class="card" style="margin-top:16px;">
        <h3>Khóa học đã tạo</h3>
        <table class="table">
            <thead><tr><th>Tên</th><th>Level</th><th>Giá</th><th>Approved</th></tr></thead>
            <tbody>
            <%
                java.util.List<model.Courses> cc = (java.util.List<model.Courses>) request.getAttribute("createdCourses");
                if (cc == null) cc = java.util.Collections.emptyList();
                for (model.Courses c : cc) {
            %>
                <tr>
                    <td><%= c.getName() %></td>
                    <td><%= c.getLevel() %></td>
                    <td><%= c.getPrice() %></td>
                    <td><%= c.isApproved()?"Yes":"No" %></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <div class="card" style="margin-top:16px;">
        <h3>Bài viết</h3>
        <table class="table">
            <thead><tr><th>Tiêu đề</th><th>Trạng thái</th><th>Ngày tạo</th></tr></thead>
            <tbody>
            <%
                java.util.List<model.Article> arts = (java.util.List<model.Article>) request.getAttribute("articles");
                if (arts == null) arts = java.util.Collections.emptyList();
                for (model.Article a : arts) {
            %>
                <tr>
                    <td><%= a.getTitle() %></td>
                    <td><%= a.getStatus() %></td>
                    <td><%= a.getCreateAt() %></td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />




