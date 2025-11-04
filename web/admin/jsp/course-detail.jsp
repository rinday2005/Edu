<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1>Chi tiết khóa học</h1>
    <div class="card">
        <h3><%= ((model.Courses)request.getAttribute("course")).getName() %></h3>
        <p>Level: <%= ((model.Courses)request.getAttribute("course")).getLevel() %></p>
        <p>Giá: <%= ((model.Courses)request.getAttribute("course")).getPrice() %></p>
        <p>Approved: <%= ((model.Courses)request.getAttribute("course")).isApproved()?"Yes":"No" %></p>
    </div>

    <div class="cards" style="margin-top:16px;">
        <div class="card">
            <h3>Sections</h3>
            <table class="table">
                <thead><tr><th>Tên</th><th>Mô tả</th><th>Trạng thái</th></tr></thead>
                <tbody>
                <%
                    java.util.List<model.Sections> ss = (java.util.List<model.Sections>) request.getAttribute("sections");
                    if (ss == null) ss = java.util.Collections.emptyList();
                    for (model.Sections s : ss) {
                %>
                    <tr><td><%= s.getName() %></td><td><%= s.getDescription() %></td><td><%= s.isStatus()?"Active":"Inactive" %></td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>

        <div class="card">
            <h3>Lessons</h3>
            <table class="table">
                <thead><tr><th>Tên</th><th>Video</th><th>Duration</th></tr></thead>
                <tbody>
                <%
                    java.util.List<model.Lession> ls = (java.util.List<model.Lession>) request.getAttribute("lessons");
                    if (ls == null) ls = java.util.Collections.emptyList();
                    for (model.Lession l : ls) {
                %>
                    <tr><td><%= l.getName() %></td><td><%= l.getVideoUrl() %></td><td><%= l.getVideoDuration() %></td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>

        <div class="card">
            <h3>Assignments</h3>
            <table class="table">
                <thead><tr><th>Tên</th><th>Mô tả</th><th>Order</th></tr></thead>
                <tbody>
                <%
                    java.util.List<model.Assignment> asg = (java.util.List<model.Assignment>) request.getAttribute("assignments");
                    if (asg == null) asg = java.util.Collections.emptyList();
                    for (model.Assignment a : asg) {
                %>
                    <tr><td><%= a.getName() %></td><td><%= a.getDescription() %></td><td><%= a.getOrder() %></td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />

