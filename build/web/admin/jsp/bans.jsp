<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/admin/common/header.jsp" />
<jsp:include page="/admin/common/sidebar.jsp" />
<main class="admin-content">
    <h1>Ban / Gỡ ban</h1>
    <div style="margin-bottom:12px; display:flex; gap:8px; justify-content:flex-end;">
        <a class="btn" href="#user-ban">Quản lý ban người dùng</a>
        <a class="btn" href="#post-ban">Quản lý ban bài viết</a>
    </div>

    <!-- Form 1: Users Ban Management (FE only) -->
    <form id="user-ban" class="card" onsubmit="return false;" style="margin-bottom:12px;">
        <h2 style="margin-top:0;">Người dùng bị ban </h2>
        <div style="overflow:auto;">
            <table class="table">
                <thead>
                    <tr>
                        <th>UserID</th>
                        <th>Họ tên</th>
                        <th>Email</th>
                        <th>Lý do</th>
                        <th>Thời hạn</th>
                        <th>Bị ban lúc</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody id="feBannedUsersTbody">
                    <tr><td colspan="7" style="text-align:center; color:#888;">Chưa có dữ liệu</td></tr>
                </tbody>
            </table>
        </div>
        <div style="display:flex; justify-content:flex-end; gap:8px; margin-top:8px;">
            <button type="button" class="btn ghost" id="clearUserBans">Xóa danh sách </button>
        </div>
    </form>

    <!-- Form 2: Posts Ban Management (FE only) -->
    <form id="post-ban" class="card" onsubmit="return false;">
        <h2 style="margin-top:0;">Bài viết bị ban </h2>
        <div style="display:grid; grid-template-columns:1fr 1fr; gap:12px; margin-bottom:8px;">
            <div>
                <label for="pbId" style="display:block; font-weight:600; margin-bottom:6px;">Post ID / URL</label>
                <input id="pbId" type="text" placeholder="abc123 hoặc /posts/abc123" style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;"/>
            </div>
            <div>
                <label for="pbDuration" style="display:block; font-weight:600; margin-bottom:6px;">Thời hạn</label>
                <select id="pbDuration" style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;">
                    <option value="7d">7 ngày</option>
                    <option value="30d">30 ngày</option>
                    <option value="perm">Vĩnh viễn</option>
                </select>
            </div>
            <div style="grid-column:1 / span 2;">
                <label for="pbReason" style="display:block; font-weight:600; margin-bottom:6px;">Lý do</label>
                <textarea id="pbReason" rows="2" placeholder="Nhập lý do..." style="width:100%; padding:10px; border:1px solid #ddd; border-radius:6px;"></textarea>
            </div>
        </div>
        <div style="display:flex; justify-content:flex-end; gap:8px;">
            <button type="button" class="btn ghost" id="pbClear">Xóa danh sách </button>
            <button type="button" class="btn" id="pbAdd">Thêm ban bài viết</button>
        </div>
        <div style="overflow:auto; margin-top:10px;">
            <table class="table">
                <thead>
                    <tr>
                        <th>Post</th>
                        <th>Lý do</th>
                        <th>Thời hạn</th>
                        <th>Bị ban lúc</th>
                        <th>Hành động</th>
                    </tr>
                </thead>
                <tbody id="feBannedPostsTbody">
                    <tr><td colspan="5" style="text-align:center; color:#888;">Chưa có dữ liệu</td></tr>
                </tbody>
            </table>
        </div>
    </form>
</main>
<jsp:include page="/admin/common/footer.jsp" />
<script src="<%=request.getContextPath()%>/admin/js/bans.js"></script>
