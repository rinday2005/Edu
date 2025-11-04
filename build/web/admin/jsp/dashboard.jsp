<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1>Dashboard</h1>
    <div class="cards">
        <div class="card">
            <h3 class="title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M20 21v-2a4 4 0 0 0-3-3.87M4 21v-2a4 4 0 0 1 3-3.87"/><circle cx="12" cy="7" r="4"/></svg>
                Người dùng
            </h3>
            <p>Tổng: <strong><%= String.valueOf(request.getAttribute("totalUsers")) %></strong></p>
            <p>Admin: <strong><%= String.valueOf(request.getAttribute("adminCount")) %></strong> · Instructor: <strong><%= String.valueOf(request.getAttribute("instructorCount")) %></strong> · Learner: <strong><%= String.valueOf(request.getAttribute("learnerCount")) %></strong></p>
            
        </div>
        <div class="card">
            <h3 class="title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 7l9-4 9 4-9 4-9-4z"/><path d="M21 10l-9 4-9-4"/><path d="M12 14v7"/></svg>
                Khóa học
            </h3>
            <p>Tổng: <strong><%= String.valueOf(request.getAttribute("totalCourses")) %></strong></p>
            <p>Đã duyệt: <strong><%= String.valueOf(request.getAttribute("approvedCourses")) %></strong> · Chờ duyệt: <strong><%= String.valueOf(request.getAttribute("pendingCourses")) %></strong></p>
            
        </div>
        <div class="card">
            <h3 class="title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="3" width="7" height="7"/><rect x="14" y="3" width="7" height="7"/><rect x="14" y="14" width="7" height="7"/><rect x="3" y="14" width="7" height="7"/></svg>
                Nội dung học
            </h3>
            <p>Section: <strong><%= String.valueOf(request.getAttribute("sectionCount")) %></strong> · Lesson: <strong><%= String.valueOf(request.getAttribute("lessonCount")) %></strong> · Assignment: <strong><%= String.valueOf(request.getAttribute("assignmentCount")) %></strong></p>
            
        </div>
        <div class="card">
            <h3 class="title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M21 15a4 4 0 0 1-4 4H7l-4 4V7a4 4 0 0 1 4-4h10a4 4 0 0 1 4 4z"/></svg>
                Tương tác
            </h3>
            <p>Article: <strong><%= String.valueOf(request.getAttribute("articleCount")) %></strong> · Comment: <strong><%= String.valueOf(request.getAttribute("commentCount")) %></strong></p>
            
        </div>
        <div class="card">
            <h3 class="title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="2" y="7" width="20" height="14" rx="2"/><path d="M16 7V5a2 2 0 0 0-2-2H4a2 2 0 0 0-2 2v2"/><circle cx="16" cy="14" r="2"/></svg>
                Tài chính
            </h3>
            <p>Ví: <strong><%= String.valueOf(request.getAttribute("walletCount")) %></strong></p>
            <p>Doanh thu: <strong><%= String.valueOf(request.getAttribute("totalRevenue")) %></strong></p>
            
        </div>
        <div class="card">
            <h3 class="title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M8 21v-4a4 4 0 0 1 8 0v4"/><circle cx="12" cy="7" r="4"/></svg>
                Top Instructors
            </h3>
            <table class="table">
                <thead><tr><th>UserID</th><th>Enrolls</th><th>Revenue</th></tr></thead>
                <tbody>
                <%
                    java.util.List<java.util.Map<String,Object>> tin = (java.util.List<java.util.Map<String,Object>>) request.getAttribute("topInstructors");
                    if (tin == null) tin = java.util.Collections.emptyList();
                    for (java.util.Map<String,Object> r : tin) {
                %>
                    <tr><td><%= r.get("userID") %></td><td><%= r.get("enrolls") %></td><td><%= r.get("revenue") %></td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
        <div class="card">
            <h3 class="title">
                <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M3 7l9-4 9 4-9 4-9-4z"/><path d="M21 10l-9 4-9-4"/></svg>
                Top Courses
            </h3>
            <table class="table">
                <thead><tr><th>Course</th><th>Enrolls</th><th>Revenue</th></tr></thead>
                <tbody>
                <%
                    java.util.List<java.util.Map<String,Object>> tc = (java.util.List<java.util.Map<String,Object>>) request.getAttribute("topCourses");
                    if (tc == null) tc = java.util.Collections.emptyList();
                    for (java.util.Map<String,Object> r : tc) {
                %>
                    <tr><td><%= r.get("name") %></td><td><%= r.get("enrolls") %></td><td><%= r.get("revenue") %></td></tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</main>
<jsp:include page="/admin/common/footer.jsp" />

