<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Chương - InstructorsHome</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/instructorsHome.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<div class="main-container">

     <!-- ===== HEADER ===== -->
    <jsp:include page="/instructor/common/header.jsp" />

    <!-- ===== SIDEBAR ===== -->
   <jsp:include page="/instructor/common/sidebar.jsp" />
    <!-- ===== MAIN CONTENT ===== -->
    <main class="content-area">
        <section class="section">
            <div style="display: flex; justify-content: space-between; align-items: center; gap: 10px;">
                <div>
                    <h2><i class="fas fa-layer-group"></i> Quản lý Chương</h2>
                    <p style="color: var(--text-muted); margin: 5px 0 0 0;">
                        <a href="CourseManagement.jsp" style="color: var(--primary-color); text-decoration: none;">
                            <i class="fas fa-arrow-left"></i> Quay lại Quản lý Khóa học
                        </a>
                    </p>
                </div>
                <button class="btn btn-primary" onclick="showAddSessionForm()">
                    <i class="fas fa-plus"></i> Thêm chương mới
                </button>
            </div>
        </section>

        <section class="section">
            <h3><i class="fas fa-list"></i> Danh sách Chương</h3>
            <div class="card">
                <div class="card-header">
                    <span id="courseTitle">React.js Cơ bản</span> - Tất cả chương
                </div>
                <div class="card-body">
                    <div class="stats-grid">

                        <!-- ===== Chương 1 ===== -->
                        <div class="stat-card session-card" data-session-id="1">
                            <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 15px;">
                                <div>
                                    <h4 class="session-title">Chương 1: Giới thiệu React.js</h4>
                                    <p class="session-lessons" style="color: var(--text-muted); margin: 5px 0;">Số bài học: 5</p>
                                    <p class="session-duration" style="color: var(--text-muted); margin: 5px 0;">Thời lượng: 2h 30m</p>
                                </div>
                                <span class="btn btn-success session-status" style="padding: 5px 10px; font-size: 12px;">Hoàn thành</span>
                            </div>
                            <div style="display: flex; gap: 10px; margin-top: 15px;">
                                <button class="btn btn-primary edit-session-btn" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-edit"></i> Chỉnh sửa</button>
                                <button class="btn btn-secondary" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-eye"></i> Xem chi tiết</button>
                                <button class="btn btn-danger" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-trash"></i> Xóa</button>
                            </div>
                        </div>

<!--                         ===== Chương 2 ===== 
                        <div class="stat-card session-card" data-session-id="2">
                            <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 15px;">
                                <div>
                                    <h4 class="session-title">Chương 2: Components & Props</h4>
                                    <p class="session-lessons" style="color: var(--text-muted); margin: 5px 0;">Số bài học: 7</p>
                                    <p class="session-duration" style="color: var(--text-muted); margin: 5px 0;">Thời lượng: 3h 15m</p>
                                </div>
                                <span class="btn btn-warning session-status" style="padding: 5px 10px; font-size: 12px; background-color: #ffc107; color: #000;">Đang làm</span>
                            </div>
                            <div style="display: flex; gap: 10px; margin-top: 15px;">
                                <button class="btn btn-primary edit-session-btn" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-edit"></i> Chỉnh sửa</button>
                                <button class="btn btn-secondary" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-eye"></i> Xem chi tiết</button>
                                <button class="btn btn-danger" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-trash"></i> Xóa</button>
                            </div>
                        </div>

                         ===== Chương 3 ===== 
                        <div class="stat-card session-card" data-session-id="3">
                            <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 15px;">
                                <div>
                                    <h4 class="session-title">Chương 3: State & Lifecycle</h4>
                                    <p class="session-lessons" style="color: var(--text-muted); margin: 5px 0;">Số bài học: 6</p>
                                    <p class="session-duration" style="color: var(--text-muted); margin: 5px 0;">Thời lượng: 2h 45m</p>
                                </div>
                                <span class="btn btn-info session-status" style="padding: 5px 10px; font-size: 12px; background-color: #17a2b8; color: white;">Chưa bắt đầu</span>
                            </div>
                            <div style="display: flex; gap: 10px; margin-top: 15px;">
                                <button class="btn btn-primary edit-session-btn" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-edit"></i> Chỉnh sửa</button>
                                <button class="btn btn-secondary" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-eye"></i> Xem chi tiết</button>
                                <button class="btn btn-danger" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-trash"></i> Xóa</button>
                            </div>
                        </div>

                         ===== Chương 4 ===== 
                        <div class="stat-card session-card" data-session-id="4">
                            <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 15px;">
                                <div>
                                    <h4 class="session-title">Chương 4: Hooks & Advanced Topics</h4>
                                    <p class="session-lessons" style="color: var(--text-muted); margin: 5px 0;">Số bài học: 8</p>
                                    <p class="session-duration" style="color: var(--text-muted); margin: 5px 0;">Thời lượng: 4h 20m</p>
                                </div>
                                <span class="btn btn-info session-status" style="padding: 5px 10px; font-size: 12px; background-color: #17a2b8; color: white;">Chưa bắt đầu</span>
                            </div>
                            <div style="display: flex; gap: 10px; margin-top: 15px;">
                                <button class="btn btn-primary edit-session-btn" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-edit"></i> Chỉnh sửa</button>
                                <button class="btn btn-secondary" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-eye"></i> Xem chi tiết</button>
                                <button class="btn btn-danger" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-trash"></i> Xóa</button>
                            </div>
                        </div>-->

                    </div>
                </div>
            </div>
        </section>

        <!-- ===== MODAL THÊM CHƯƠNG MỚI ===== -->
        <div id="addSessionModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); z-index: 1000;">
            <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: var(--bg-card); padding: 30px; border-radius: 8px; width: 90%; max-width: 600px; max-height: 85vh; overflow-y: auto;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <h3><i class="fas fa-plus"></i> Thêm chương mới</h3>
                    <button onclick="hideAddSessionForm()" style="background: none; border: none; font-size: 24px; cursor: pointer; color: var(--text-light);">&times;</button>
                </div>
                <form id="addSessionForm">
                    <div class="form-group">
                        <label for="sessionName">Tên chương *</label>
                        <input type="text" id="sessionName" class="form-control" placeholder="Ví dụ: Chương 1: Giới thiệu React.js" required>
                    </div>
                    <div class="form-group">
                        <label for="sessionDescription">Mô tả chương</label>
                        <textarea id="sessionDescription" class="form-control" rows="4" placeholder="Mô tả chi tiết về chương này..."></textarea>
                    </div>
                    <div class="form-group">
                        <label for="sessionOrder">Thứ tự chương *</label>
                        <input type="number" id="sessionOrder" class="form-control" placeholder="1" min="1" required>
                    </div>
                    <div class="form-group">
                        <label for="sessionStatus">Trạng thái</label>
                        <select id="sessionStatus" class="form-control">
                            <option value="Chưa bắt đầu">Chưa bắt đầu</option>
                            <option value="Đang làm">Đang làm</option>
                            <option value="Hoàn thành">Hoàn thành</option>
                        </select>
                    </div>

                    <div style="display: flex; gap: 10px; justify-content: flex-end; margin-top: 20px;">
                        <button type="button" class="btn btn-secondary" onclick="hideAddSessionForm()">Hủy</button>
                        <button type="submit" class="btn btn-primary">Thêm chương</button>
                    </div>
                </form>
            </div>
        </div>

    </main>
</div>

<script src="${pageContext.request.contextPath}/assets/js/instructorsHome.js"></script>
<script>
    // Modal chỉnh sửa session
    function openEditSessionModal(card) {
        const title = card.querySelector('.session-title')?.textContent || '';
        const lessons = (card.querySelector('.session-lessons')?.textContent || '').replace(/[^0-9]/g, '');
        const duration = card.querySelector('.session-duration')?.textContent.replace('Thời lượng: ', '') || '';
        const statusText = card.querySelector('.session-status')?.textContent || 'Chưa bắt đầu';

        document.getElementById('editSessionTitle').value = title;
        document.getElementById('editSessionLessons').value = lessons || '';
        document.getElementById('editSessionDuration').value = duration || '';
        document.getElementById('editSessionStatus').value = statusText;

        document.getElementById('editSessionModal').style.display = 'block';
        document.getElementById('editSessionModal').setAttribute('data-target-id', card.getAttribute('data-session-id'));
    }

    function hideEditSessionModal() {
        document.getElementById('editSessionModal').style.display = 'none';
    }

    // Lấy tên khóa học từ URL parameter
    function getCourseFromURL() {
        const urlParams = new URLSearchParams(window.location.search);
        const course = urlParams.get('course');
        if (course) {
            document.getElementById('courseTitle').textContent = decodeURIComponent(course);
        }
    }

    function showAddSessionForm() {
        document.getElementById('addSessionModal').style.display = 'block';
    }

    function hideAddSessionForm() {
        document.getElementById('addSessionModal').style.display = 'none';
        // Reset form khi đóng
        const form = document.getElementById('addSessionForm');
        if (form) {
            form.reset();
        }
    }

    // Đóng modal khi click ra ngoài
    window.addEventListener('click', function(event) {
        const modal = document.getElementById('addSessionModal');
        if (event.target === modal) {
            hideAddSessionForm();
        }
    });

    // Xử lý submit form thêm chương (FE only - demo)
    const addSessionForm = document.getElementById('addSessionForm');
    if (addSessionForm) {
        addSessionForm.addEventListener('submit', function(e) {
            e.preventDefault();

            const formData = new FormData(addSessionForm);
            const sessionData = {
                name: formData.get('sessionName'),
                description: formData.get('sessionDescription'),
                order: formData.get('sessionOrder'),
                status: formData.get('sessionStatus')
            };

            console.log('Dữ liệu chương mới (FE):', sessionData);
            alert('Đã thêm chương mới (FE demo):\n- Tên: ' + sessionData.name + '\n- Thứ tự: ' + sessionData.order + '\n- Trạng thái: ' + sessionData.status);
            
            hideAddSessionForm();
        });
    }

    // Khởi tạo trang
    document.addEventListener('DOMContentLoaded', function() {
        getCourseFromURL();

        // Attach edit handlers
        Array.from(document.querySelectorAll('.edit-session-btn')).forEach(function(btn){
            btn.addEventListener('click', function(){
                const card = btn.closest('.session-card');
                if (card) openEditSessionModal(card);
            });
        });

        // Submit edit
        const editForm = document.getElementById('editSessionForm');
        if (editForm) {
            editForm.addEventListener('submit', function(e){
                e.preventDefault();
                const targetId = document.getElementById('editSessionModal').getAttribute('data-target-id');
                const card = document.querySelector('.session-card[data-session-id="' + targetId + '"]');
                if (!card) return;

                const title = document.getElementById('editSessionTitle').value.trim();
                const lessons = document.getElementById('editSessionLessons').value.trim();
                const duration = document.getElementById('editSessionDuration').value.trim();
                const status = document.getElementById('editSessionStatus').value;

                if (title) card.querySelector('.session-title').textContent = title;
                card.querySelector('.session-lessons').textContent = 'Số bài học: ' + (lessons || '0');
                card.querySelector('.session-duration').textContent = 'Thời lượng: ' + (duration || '');
                const statusEl = card.querySelector('.session-status');
                statusEl.textContent = status;
                // đổi màu nhanh theo trạng thái
                statusEl.className = 'btn session-status';
                if (status === 'Hoàn thành') statusEl.classList.add('btn-success');
                else if (status === 'Đang làm') statusEl.classList.add('btn-warning');
                else statusEl.classList.add('btn-info');

                hideEditSessionModal();
            });
        }
    });
</script>

<!-- Modal Edit Session -->
<div id="editSessionModal" style="display:none; position: fixed; top:0; left:0; width:100%; height:100%; background: rgba(0,0,0,0.5); z-index: 1000;">
    <div style="position:absolute; top:50%; left:50%; transform: translate(-50%, -50%); background: var(--bg-card); width: 90%; max-width: 560px; border-radius: 8px; padding: 24px;">
        <div style="display:flex; justify-content: space-between; align-items:center; margin-bottom: 12px;">
            <h3 style="margin:0"><i class="fas fa-edit"></i> Chỉnh sửa chương</h3>
            <button onclick="hideEditSessionModal()" style="background:none; border:none; font-size:24px; cursor:pointer;">&times;</button>
        </div>
        <form id="editSessionForm">
            <div class="form-group">
                <label for="editSessionTitle">Tiêu đề chương</label>
                <input id="editSessionTitle" type="text" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="editSessionLessons">Số bài học</label>
                <input id="editSessionLessons" type="number" min="0" class="form-control">
            </div>
            <div class="form-group">
                <label for="editSessionDuration">Thời lượng (ví dụ: 2h 30m)</label>
                <input id="editSessionDuration" type="text" class="form-control">
            </div>
            <div class="form-group">
                <label for="editSessionStatus">Trạng thái</label>
                <select id="editSessionStatus" class="form-control">
                    <option>Chưa bắt đầu</option>
                    <option>Đang làm</option>
                    <option>Hoàn thành</option>
                </select>
            </div>
            <div style="display:flex; gap:8px; justify-content:flex-end; margin-top: 12px;">
                <button type="button" class="btn btn-secondary" onclick="hideEditSessionModal()">Hủy</button>
                <button type="submit" class="btn btn-primary">Lưu</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
