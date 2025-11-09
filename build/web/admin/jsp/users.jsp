<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1 class="title">
        <svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M20 21v-2a4 4 0 0 0-3-3.87M4 21v-2a4 4 0 0 1 3-3.87"/><circle cx="12" cy="7" r="4"/></svg>
        Quản lý tài khoản
    </h1>
    <div style="margin-bottom:12px; display:flex; gap:8px;">
        <a class="btn ghost" href="<%=request.getContextPath()%>/admin/users">All</a>
        <a class="btn ghost" href="<%=request.getContextPath()%>/admin/users?role=Instructor">Instructor</a>
        <a class="btn ghost" href="<%=request.getContextPath()%>/admin/users?role=Learner">Learner</a>
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
                        <a class="btn" style="margin-left:6px;" href="<%=request.getContextPath()%>/admin/user/detail?id=<%= u.getUserID() %>">Xem chi tiết</a>
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
            <h2 style="margin:0; font-size:18px;">Thêm tài khoản (FE only)</h2>
            <button id="closeAddUserModalBtn" class="btn ghost" type="button">Đóng</button>
        </div>
        <form id="addUserForm" style="padding:16px 20px;">
            <div style="display:grid; grid-template-columns:1fr 1fr; gap:12px;">
                <div>
                    <label for="auFullName" style="display:block; font-weight:600; margin-bottom:6px;">Họ và tên</label>
                    <input id="auFullName" type="text" class="input" placeholder="Nguyễn Văn A" required style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;" />
                </div>
                <div>
                    <label for="auEmail" style="display:block; font-weight:600; margin-bottom:6px;">Email</label>
                    <input id="auEmail" type="email" class="input" placeholder="email@domain.com" required style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;" />
                </div>
                <div>
                    <label for="auPhone" style="display:block; font-weight:600; margin-bottom:6px;">Số điện thoại</label>
                    <input id="auPhone" type="tel" class="input" placeholder="0901234567" style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;" />
                </div>
                <div>
                    <label for="auRole" style="display:block; font-weight:600; margin-bottom:6px;">Vai trò</label>
                    <select id="auRole" class="input" style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;">
                        <option value="Learner">Learner</option>
                        <option value="Instructor">Instructor</option>
                        <option value="Admin">Admin</option>
                    </select>
                </div>
                <div>
                    <label for="auPassword" style="display:block; font-weight:600; margin-bottom:6px;">Mật khẩu</label>
                    <input id="auPassword" type="password" class="input" required style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;" />
                </div>
                <div>
                    <label for="auConfirm" style="display:block; font-weight:600; margin-bottom:6px;">Xác nhận mật khẩu</label>
                    <input id="auConfirm" type="password" class="input" required style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;" />
                </div>
                <div>
                    <label for="auStatus" style="display:block; font-weight:600; margin-bottom:6px;">Trạng thái</label>
                    <select id="auStatus" class="input" style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;">
                        <option value="Active">Active</option>
                        <option value="Locked">Locked</option>
                    </select>
                </div>
            </div>
            <div style="display:flex; justify-content:flex-end; gap:8px; margin-top:16px;">
                <button type="button" class="btn ghost" id="cancelAddUser">Hủy</button>
                <button type="submit" class="btn">Tạo tài khoản</button>
            </div>
            <p style="margin-top:8px; color:#888; font-size:12px;">Lưu ý: Chức năng này chỉ là giao diện (không gọi BE).</p>
        </form>
    </div>
    <!-- click outside catcher -->
    <span id="modalBackdropCatcher" style="position:absolute; inset:0;"></span>
    <script>
        (function(){
            var modal = document.getElementById('addUserModal');
            var openBtn = document.getElementById('openAddUserModalBtn');
            var closeBtn = document.getElementById('closeAddUserModalBtn');
            var cancelBtn = document.getElementById('cancelAddUser');
            var form = document.getElementById('addUserForm');
            var catcher = document.getElementById('modalBackdropCatcher');

            function open(){ modal.style.display = 'flex'; }
            function close(){ modal.style.display = 'none'; }

            if (openBtn) openBtn.addEventListener('click', open);
            if (closeBtn) closeBtn.addEventListener('click', close);
            if (cancelBtn) cancelBtn.addEventListener('click', close);
            if (catcher) catcher.addEventListener('click', function(e){
                // close when clicking backdrop
                if (e.target === catcher) close();
            });
            document.addEventListener('keydown', function(e){ if (e.key === 'Escape') close(); });

            if (form) form.addEventListener('submit', function(e){
                e.preventDefault();
                var pwd = document.getElementById('auPassword').value;
                var cfm = document.getElementById('auConfirm').value;
                if (pwd !== cfm) { alert('Mật khẩu không khớp'); return; }
                alert('FE only: Dữ liệu đã được nhập. Chưa gửi lên BE.');
                close();
            });
        })();
    </script>
</div>
