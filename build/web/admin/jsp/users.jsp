<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<%!
    private String getFormValue(java.util.Map<String, String> data, String key) {
        if (data == null) return "";
        String v = data.get(key);
        return v == null ? "" : v;
    }
%>
<%
    String flashSuccess = (String) session.getAttribute("flashSuccess");
    if (flashSuccess != null) session.removeAttribute("flashSuccess");
    String flashError = (String) session.getAttribute("flashError");
    if (flashError != null) session.removeAttribute("flashError");
    java.util.List<String> formErrors = (java.util.List<String>) request.getAttribute("formErrors");
    java.util.Map<String, String> formData = (java.util.Map<String, String>) request.getAttribute("formData");
    boolean reopenModal = formErrors != null && !formErrors.isEmpty();
    String ctx = request.getContextPath();
%>
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M20 21v-2a4 4 0 0 0-3-3.87M4 21v-2a4 4 0 0 1 3-3.87"/><circle cx="12" cy="7" r="4"/></svg>
        Quản lý tài khoản
    </h1>
    <%
        if (flashSuccess != null) {
    %>
    <div style="margin-bottom:12px; padding:12px 16px; border-radius:6px; background:#e6ffed; color:#046c4e;"><%= flashSuccess %></div>
    <%
        }
        if (flashError != null) {
    %>
    <div style="margin-bottom:12px; padding:12px 16px; border-radius:6px; background:#ffe6e6; color:#a8071a;"><%= flashError %></div>
    <%
        }
    %>
    <div style="margin-bottom:12px; display:flex; gap:8px;">
        <a class="btn ghost" href="<%=ctx%>/admin/users">All</a>
        <a class="btn ghost" href="<%=ctx%>/admin/users?role=Instructor">Instructor</a>
        <a class="btn ghost" href="<%=ctx%>/admin/users?role=Learner">Learner</a>
        <div style="flex:1"></div>
        <button id="openAddUserModalBtn" class="btn" type="button">+ Thêm tài khoản</button>
    </div>
    <div class="card">
        <table class="table">
            <thead>
                <tr>
                    <th>FullName</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    java.util.List<model.User> users = (java.util.List<model.User>) request.getAttribute("users");
                    if (users == null) users = java.util.Collections.emptyList();
                    for (model.User u : users) {
                %>
                <tr>
                    <td><%= u.getFullName() %></td>
                    <td><%= u.getEmail() %></td>
                    <td><%= u.getPhoneNumber() %></td>
                    <td><%= u.getRole() %></td>
                    <td><%= u.getStatus() ? "Active" : "Locked" %></td>
                    <td>
                        <form method="post" action="<%=request.getContextPath()%>/admin/users" style="display:inline;">
                            <input type="hidden" name="userID" value="<%= u.getUserID() %>"/>
                            <input type="hidden" name="action" value="<%= u.getStatus()?"lock":"unlock" %>"/>
                            <button class="btn secondary" type="submit"><%= u.getStatus()?"Khóa":"Mở khóa" %></button>
                        </form>
                        <form method="post" action="<%=request.getContextPath()%>/admin/users" style="display:inline;margin-left:6px;">
                            <input type="hidden" name="userID" value="<%= u.getUserID() %>"/>
                            <input type="hidden" name="action" value="delete"/>
                            <button class="btn ghost" type="submit" onclick="return confirm('Xóa tài khoản?');">Xóa</button>
                        </form>
        <a class="btn" style="margin-left:6px;" href="<%=ctx%>/admin/user/detail?id=<%= u.getUserID() %>">Xem chi tiết</a>
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

<!-- Front-end only Add User Modal -->
<div id="addUserModal" style="display:none; position:fixed; inset:0; background:rgba(0,0,0,.5); z-index:1000; align-items:center; justify-content:center;">
    <div style="background:#fff; width:100%; max-width:640px; border-radius:8px; box-shadow:0 10px 30px rgba(0,0,0,.2);">
        <div style="padding:16px 20px; border-bottom:1px solid #eee; display:flex; align-items:center; justify-content:space-between;">
            <h2 style="margin:0; font-size:18px;">Thêm tài khoản</h2>
            <button id="closeAddUserModalBtn" class="btn ghost" type="button">Đóng</button>
        </div>
        <form id="addUserForm" method="post" action="<%=ctx%>/admin/users" style="padding:16px 20px;" data-should-open="<%=reopenModal ? "true" : "false"%>">
            <input type="hidden" name="action" value="create" />
            <div style="display:grid; grid-template-columns:1fr 1fr; gap:12px;">
                <div>
                    <label for="auFullName" style="display:block; font-weight:600; margin-bottom:6px;">Họ và tên</label>
                    <input id="auFullName" name="fullName" type="text" class="input" placeholder="Nguyễn Văn A" required style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;" value="<%=getFormValue(formData, "fullName")%>" />
                </div>
                <div>
                    <label for="auEmail" style="display:block; font-weight:600; margin-bottom:6px;">Email</label>
                    <input id="auEmail" name="email" type="email" class="input" placeholder="email@domain.com" required style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;" value="<%=getFormValue(formData, "email")%>" />
                </div>
                <div>
                    <label for="auPhone" style="display:block; font-weight:600; margin-bottom:6px;">Số điện thoại</label>
                    <input id="auPhone" name="phone" type="tel" class="input" placeholder="0901234567" style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;" value="<%=getFormValue(formData, "phone")%>" />
                </div>
                <div>
                    <label for="auRole" style="display:block; font-weight:600; margin-bottom:6px;">Vai trò</label>
                    <select id="auRole" name="role" class="input" style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;">
                        <option value="Learner" <%= "Learner".equalsIgnoreCase(getFormValue(formData, "role")) || getFormValue(formData, "role").isEmpty() ? "selected" : "" %>>Learner</option>
                        <option value="Instructor" <%= "Instructor".equalsIgnoreCase(getFormValue(formData, "role")) ? "selected" : "" %>>Instructor</option>
                        <option value="Admin" <%= "Admin".equalsIgnoreCase(getFormValue(formData, "role")) ? "selected" : "" %>>Admin</option>
                    </select>
                </div>
                <div>
                    <label for="auPassword" style="display:block; font-weight:600; margin-bottom:6px;">Mật khẩu</label>
                    <input id="auPassword" name="password" type="password" class="input" required style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;" />
                </div>
                <div>
                    <label for="auConfirm" style="display:block; font-weight:600; margin-bottom:6px;">Xác nhận mật khẩu</label>
                    <input id="auConfirm" name="confirmPassword" type="password" class="input" required style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;" />
                </div>
                <div>
                    <label for="auStatus" style="display:block; font-weight:600; margin-bottom:6px;">Trạng thái</label>
                    <select id="auStatus" name="status" class="input" style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;">
                        <option value="Active" <%= !"Locked".equalsIgnoreCase(getFormValue(formData, "status")) ? "selected" : "" %>>Active</option>
                        <option value="Locked" <%= "Locked".equalsIgnoreCase(getFormValue(formData, "status")) ? "selected" : "" %>>Locked</option>
                    </select>
                </div>
            </div>
            <div id="clientError" style="display:none; margin-top:12px; padding:10px 14px; border-radius:6px; background:#ffe6e6; color:#a8071a;"></div>
            <%
                if (formErrors != null && !formErrors.isEmpty()) {
            %>
            <div style="margin-top:12px; padding:10px 14px; border-radius:6px; background:#ffe6e6; color:#a8071a;">
                <ul style="margin:0; padding-left:18px;">
                    <%
                        for (String err : formErrors) {
                    %>
                    <li><%= err %></li>
                    <%
                        }
                    %>
                </ul>
            </div>
            <%
                }
            %>
            <div style="display:flex; justify-content:flex-end; gap:8px; margin-top:16px;">
                <button type="button" class="btn ghost" id="cancelAddUser">Hủy</button>
                <button type="submit" class="btn">Tạo tài khoản</button>
            </div>
        </form>
    </div>
    <script>
        (function(){
            var modal = document.getElementById('addUserModal');
            var openBtn = document.getElementById('openAddUserModalBtn');
            var closeBtn = document.getElementById('closeAddUserModalBtn');
            var cancelBtn = document.getElementById('cancelAddUser');
            var form = document.getElementById('addUserForm');
            var clientError = document.getElementById('clientError');

            function open(){ modal.style.display = 'flex'; }
            function close(){ modal.style.display = 'none'; }
            function clearClientError(){
                if (clientError) {
                    clientError.style.display = 'none';
                    clientError.innerHTML = '';
                }
            }

            if (openBtn) openBtn.addEventListener('click', open);
            if (closeBtn) closeBtn.addEventListener('click', function(){ clearClientError(); close(); });
            if (cancelBtn) cancelBtn.addEventListener('click', function(){ clearClientError(); close(); });
            if (modal) modal.addEventListener('click', function(e){
                if (e.target === modal) {
                    clearClientError();
                    close();
                }
            });
            document.addEventListener('keydown', function(e){
                if (e.key === 'Escape') {
                    clearClientError();
                    close();
                }
            });

            if (form) form.addEventListener('submit', function(e){
                clearClientError();
                if (!form.checkValidity()) {
                    return;
                }
                var pwd = document.getElementById('auPassword').value;
                var cfm = document.getElementById('auConfirm').value;
                if (pwd !== cfm) {
                    e.preventDefault();
                    if (clientError) {
                        clientError.innerHTML = 'Mật khẩu xác nhận không khớp.';
                        clientError.style.display = 'block';
                    } else {
                        alert('Mật khẩu xác nhận không khớp.');
                    }
                }
            });
            if (form && form.dataset.shouldOpen === 'true') {
                open();
            }
        })();
    </script>
</div>
