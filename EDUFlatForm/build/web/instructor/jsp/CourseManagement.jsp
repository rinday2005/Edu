<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Quản lý Khóa học - InstructorsHome</title>
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
                <h2><i class="fas fa-laptop-code"></i> Quản lý Khóa học</h2>
                <button class="btn btn-primary" onclick="showCourseForm()">
                    <i class="fas fa-plus"></i> Tạo khóa học mới
                </button>
            </div>
        </section>

        <section class="section">
            <h3><i class="fas fa-list"></i> Danh sách Khóa học</h3>
            <div class="card">
                <div class="card-header">Tất cả khóa học của bạn</div>
                <div class="card-body">
                    <div class="stats-grid">

                        <!-- ===== Khóa học 1 ===== -->
                        <div class="stat-card" data-course-name="React.js Cơ bản" data-course-level="Beginner" data-course-price="29.99" data-course-status="Published">
                            <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 15px;">
                                <div>
                                    <h4>React.js Cơ bản</h4>
                                    <p style="color: var(--text-muted); margin: 5px 0;">Cấp độ: Beginner</p>
                                    <p style="color: var(--text-muted); margin: 5px 0;">Giá: $29.99</p>
                                </div>
                                <span class="btn btn-success" style="padding: 5px 10px; font-size: 12px;">Published</span>
                            </div>
                            <div style="display: flex; gap: 10px; margin-top: 15px;">
                                <button class="btn btn-primary" style="padding: 8px 15px; font-size: 12px;" onclick="editCourseFromCard(this)"><i class="fas fa-edit"></i> Chỉnh sửa</button>
                                <button class="btn btn-secondary" style="padding: 8px 15px; font-size: 12px;" onclick="viewCourseContent(this)"><i class="fas fa-eye"></i> Xem nội dung</button>
                                <a href="CourseSession.jsp?course=React.js%20Cơ%20bản" class="btn btn-info" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-layer-group"></i> Quản lý chương</a>
                            </div>
                        </div>

<!--                         ===== Khóa học 2 ===== 
                        <div class="stat-card" data-course-name="Python Advanced" data-course-level="Advanced" data-course-price="49.99" data-course-status="Draft">
                            <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 15px;">
                                <div>
                                    <h4>Python Advanced</h4>
                                    <p style="color: var(--text-muted); margin: 5px 0;">Cấp độ: Advanced</p>
                                    <p style="color: var(--text-muted); margin: 5px 0;">Giá: $49.99</p>
                                </div>
                                <span class="btn btn-warning" style="padding: 5px 10px; font-size: 12px; background-color: #ffc107; color: #000;">Draft</span>
                            </div>
                            <div style="display: flex; gap: 10px; margin-top: 15px;">
                                <button class="btn btn-primary" style="padding: 8px 15px; font-size: 12px;" onclick="editCourseFromCard(this)"><i class="fas fa-edit"></i> Chỉnh sửa</button>
                                <button class="btn btn-secondary" style="padding: 8px 15px; font-size: 12px;" onclick="viewCourseContent(this)"><i class="fas fa-eye"></i> Xem nội dung</button>
                                <a href="CourseSession.jsp?course=Python%20Advanced" class="btn btn-info" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-layer-group"></i> Quản lý chương</a>
                            </div>
                        </div>

                         ===== Khóa học 3 ===== 
                        <div class="stat-card" data-course-name="JavaScript ES6+" data-course-level="Intermediate" data-course-price="39.99" data-course-status="Pending Review">
                            <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 15px;">
                                <div>
                                    <h4>JavaScript ES6+</h4>
                                    <p style="color: var(--text-muted); margin: 5px 0;">Cấp độ: Intermediate</p>
                                    <p style="color: var(--text-muted); margin: 5px 0;">Giá: $39.99</p>
                                </div>
                                <span class="btn btn-info" style="padding: 5px 10px; font-size: 12px; background-color: #17a2b8; color: white;">Pending Review</span>
                            </div>
                            <div style="display: flex; gap: 10px; margin-top: 15px;">
                                <button class="btn btn-primary" style="padding: 8px 15px; font-size: 12px;" onclick="editCourseFromCard(this)"><i class="fas fa-edit"></i> Chỉnh sửa</button>
                                <button class="btn btn-secondary" style="padding: 8px 15px; font-size: 12px;" onclick="viewCourseContent(this)"><i class="fas fa-eye"></i> Xem nội dung</button>
                                <a href="CourseSession.jsp?course=JavaScript%20ES6%2B" class="btn btn-info" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-layer-group"></i> Quản lý chương</a>
                            </div>
                        </div>

                         ===== Khóa học 4 ===== 
                        <div class="stat-card" data-course-name="HTML/CSS Master" data-course-level="Beginner" data-course-price="19.99" data-course-status="Published">
                            <div style="display: flex; justify-content: space-between; align-items: start; margin-bottom: 15px;">
                                <div>
                                    <h4>HTML/CSS Master</h4>
                                    <p style="color: var(--text-muted); margin: 5px 0;">Cấp độ: Beginner</p>
                                    <p style="color: var(--text-muted); margin: 5px 0;">Giá: $19.99</p>
                                </div>
                                <span class="btn btn-success" style="padding: 5px 10px; font-size: 12px;">Published</span>
                            </div>
                            <div style="display: flex; gap: 10px; margin-top: 15px;">
                                <button class="btn btn-primary" style="padding: 8px 15px; font-size: 12px;" onclick="editCourseFromCard(this)"><i class="fas fa-edit"></i> Chỉnh sửa</button>
                                <button class="btn btn-secondary" style="padding: 8px 15px; font-size: 12px;" onclick="viewCourseContent(this)"><i class="fas fa-eye"></i> Xem nội dung</button>
                                <a href="CourseSession.jsp?course=HTML%2FCSS%20Master" class="btn btn-info" style="padding: 8px 15px; font-size: 12px;"><i class="fas fa-layer-group"></i> Quản lý chương</a>
                            </div>
                        </div>-->

                    </div>
                </div>
            </div>
        </section>

        <!-- ===== MODAL TẠO KHÓA HỌC (có tích hợp Thêm chương) ===== -->
        <div id="courseFormModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); z-index: 1000;">
            <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: var(--bg-card); padding: 30px; border-radius: 8px; width: 90%; max-width: 700px; max-height: 85vh; overflow-y: auto;">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <h3><i class="fas fa-plus"></i> Tạo khóa học mới</h3>
                    <button onclick="hideCourseForm()" style="background: none; border: none; font-size: 24px; cursor: pointer; color: var(--text-light);">&times;</button>
                </div>
                <form id="createCourseForm"action="${pageContext.request.contextPath}/ManageCourse"  method="post"  enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="courseName">Tên khóa học *</label>
                        <input name="namecourse" type="text" id="courseName" class="form-control" placeholder="Nhập tên khóa học" required>
                    </div>
                    <div class="form-group">
                        <label for="courseDescription">Mô tả khóa học *</label>
                        <textarea name="descriptioncourse" id="courseDescription" class="form-control" rows="4" placeholder="Mô tả chi tiết về khóa học" required></textarea>
                    </div>
                    <div class="form-group">
                        <label for="coursePrice">Giá khóa học ($) *</label>
                        <input type="number" name = "pricecourse" id="coursePrice" class="form-control" placeholder="0.00" step="0.01" min="0" required>
                    </div>
                    <div class="form-group">
                        <label for="courseLevel">Cấp độ *</label>
                      
                        <select id="courseLevel" name="levelcourse" class="form-control" required>
                            <option value="">Chọn cấp độ</option>
                            <option value="Beginner">Beginner</option>
                            <option value="Intermediate">Intermediate</option>
                            <option value="Advanced">Advanced</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="courseImage">Ảnh bìa khóa học</label>
                        <input type="file" name="picturecourse" id="courseImage" class="form-control" accept="image/*">
                    </div>
                    <div class="form-group">
                        <label for="courseStatus">Trạng thái</label>
                        <select id="courseStatus" name="status" class="form-control">
                            <option value="Published">Published</option>
                            <option value="Pending Review">Pending Review</option>
                        </select>
                    </div>

                    <!-- ===== CHỨC NĂNG NHỎ: THÊM CHƯƠNG (SESSION) ===== -->
                    <div class="form-group" style="margin-top: 20px; padding-top: 20px; border-top: 1px solid var(--border-color);">
                        <label style="display: flex; align-items: center; gap: 8px; margin-bottom: 10px;">
                            <i class="fas fa-layer-group"></i>
                            <strong>Thêm Chương (Session)</strong>
                            
                        </label>
                        <p style="color: var(--text-light); opacity: 0.7; font-size: 0.9rem; margin-bottom: 12px;">
                            Thêm các chương cho khóa học này. Bạn có thể thêm nhiều chương cùng lúc.
                        </p>
                        <div id="sessionsContainer" style="display: grid; gap: 12px; margin-bottom: 10px;">
                            <!-- Các dòng chương sẽ được thêm động tại đây -->
                        </div>
                        <button type="button" id="addSessionBtn" class="btn btn-primary" style="width: 100%;">
                            <i class="fas fa-plus"></i> Thêm chương mới
                        </button>
                    </div>

                    <div style="display: flex; gap: 10px; justify-content: flex-end; margin-top: 20px;">
                        <button type="button" class="btn btn-secondary" onclick="hideCourseForm()">Hủy</button>
                        <button type="submit" class="btn btn-primary">Tạo khóa học</button>
                    </div>
                </form>
            </div>
        </div>

    </main>
</div>

<script src="${pageContext.request.contextPath}/assets/js/instructorsHome.js"></script>
<script>
    function showCourseForm() {
        document.getElementById('courseFormModal').style.display = 'block';
        // Khởi tạo 1 dòng chương mặc định nếu chưa có
        const sessionsContainer = document.getElementById('sessionsContainer');
        if (sessionsContainer && sessionsContainer.children.length === 0) {
            addSessionRow();
        }
    }

    function hideCourseForm() {
        document.getElementById('courseFormModal').style.display = 'none';
        // Reset form khi đóng
        const form = document.getElementById('createCourseForm');
        if (form) {
            form.reset();
            const container = document.getElementById('sessionsContainer');
            if (container) container.innerHTML = '';
        }
    }

    // Hàm thêm 1 dòng chương mới (FE only)
    function addSessionRow(prefill) {
        const container = document.getElementById('sessionsContainer');
        if (!container) return;

        const row = document.createElement('div');
        row.className = 'session-row';
        row.style.display = 'grid';
        row.style.gridTemplateColumns = '1fr';
        row.style.gap = '12px';
        row.style.alignItems = 'start';
        row.style.padding = '12px';
        row.style.border = '1px solid var(--border-color)';
        row.style.borderRadius = '8px';
        row.style.backgroundColor = 'var(--bg-card)';
        const sessionIndex = document.querySelectorAll('.session-row').length;
        row.setAttribute('data-session-index', String(sessionIndex));

        // Dùng nối chuỗi để tránh JSP EL can thiệp
        row.innerHTML =
            '<div class="session-header" style="display:grid; grid-template-columns: 1fr 1fr 1fr auto; gap:10px; align-items:center;">'
          +   '<input type="text" name="sessionNames[]" class="form-control" placeholder="Tên chương (vd: Giới thiệu)"'
          +     (prefill && prefill.name ? (' value="' + prefill.name + '"') : '') + ' required>'
          +   '<input type="text" name="sessionDescriptions[]" class="form-control" placeholder="Mô tả chương (tùy chọn)"'
          +     (prefill && prefill.desc ? (' value="' + prefill.desc + '"') : '') + '>'
          +   '<input type="file" name="sessionFiles[]" class="form-control" style="width:100%;" accept=".pdf,.doc,.docx,.ppt,.pptx,.txt,.zip,.rar,image/*,video/*">'
          +   '<button type="button" class="btn btn-danger remove-session" title="Xóa chương"><i class="fas fa-times"></i></button>'
          + '</div>'
          + '<div class="session-lessons" style="display:grid; gap:10px;">'
          +   '<div class="lessons-container" style="display:grid; gap:8px;"></div>'
          +   '<div style="display:flex; justify-content:flex-start;">'
          +     '<button type="button" class="btn btn-secondary add-lesson"><i class="fas fa-plus"></i> Thêm bài học</button>'
          +   '</div>'
          + '</div>';

        // Xử lý xóa chương
        row.querySelector('.remove-session').addEventListener('click', function(){ row.remove(); });
        // Thêm bài học
        row.querySelector('.add-lesson').addEventListener('click', function(){ addLessonRow(row); });

        container.appendChild(row);
    }

    // Thêm 1 dòng bài học cho một chương (FE only)
    function addLessonRow(sessionRow, prefill) {
        if (!sessionRow) return;
        const sessionIndex = sessionRow.getAttribute('data-session-index');
        const lessonsContainer = sessionRow.querySelector('.lessons-container');
        if (!lessonsContainer) return;

        const item = document.createElement('div');
        item.style.display = 'grid';
        item.style.gridTemplateColumns = '1fr 1fr 1fr auto';
        item.style.gap = '8px';

        item.innerHTML =
            '<input type="text" name="lessonNames_' + sessionIndex + '[]" class="form-control" placeholder="Tên bài học"'
          +   (prefill && prefill.name ? (' value="' + prefill.name + '"') : '') + ' required>'
          + '<input type="text" name="lessonUrls_' + sessionIndex + '[]" class="form-control" placeholder="URL video/tài liệu">'
          + '<input type="file" name="lessonFiles_' + sessionIndex + '[]" class="form-control" style="width:100%;" accept=".pdf,.doc,.docx,.ppt,.pptx,.txt,.zip,.rar,image/*,video/*">'
          + '<button type="button" class="btn btn-danger remove-lesson" title="Xóa bài học"><i class="fas fa-times"></i></button>';

        item.querySelector('.remove-lesson').addEventListener('click', function(){ item.remove(); });
        lessonsContainer.appendChild(item);
    }

    // Nút thêm chương
    const addSessionBtn = document.getElementById('addSessionBtn');
    if (addSessionBtn) {
        addSessionBtn.addEventListener('click', function() {
            addSessionRow();
        });
    }

    // Đóng modal khi click ra ngoài
    window.addEventListener('click', function(event) {
        const modal = document.getElementById('courseFormModal');
        if (event.target === modal) {
            hideCourseForm();
        }
    });

    // Xử lý submit form (FE only - demo)
    const createCourseForm = document.getElementById('createCourseForm');
    if (createCourseForm) {
        createCourseForm.addEventListener('submit', function(e) {
            e.preventDefault();

            const formData = new FormData(createCourseForm);
            const sessionNames = formData.getAll('sessionNames[]');
            const sessionDescriptions = formData.getAll('sessionDescriptions[]');
            const sessionFiles = formData.getAll('sessionFiles[]');

            // Thu thập thông tin khóa học
            const courseData = {
                name: formData.get('courseName'),
                description: formData.get('courseDescription'),
                price: formData.get('coursePrice'),
                level: formData.get('courseLevel'),
                status: formData.get('courseStatus'),
                sessions: []
            };

            // Thu thập thông tin các chương
            const sessionRows = Array.from(document.querySelectorAll('.session-row'));
            sessionRows.forEach(function(row, idx){
                const name = sessionNames[idx];
                if (!name || !name.trim()) return;
                const session = {
                    name,
                    description: sessionDescriptions[idx] || '',
                    fileName: (sessionFiles[idx] && sessionFiles[idx].name) ? sessionFiles[idx].name : '',
                    lessons: []
                };
                const sIdx = row.getAttribute('data-session-index');
                const lessonNameInputs = row.querySelectorAll('input[name="lessonNames_' + sIdx + '[]"]');
                const lessonUrlInputs = row.querySelectorAll('input[name="lessonUrls_' + sIdx + '[]"]');
                const lessonFileInputs = row.querySelectorAll('input[name="lessonFiles_' + sIdx + '[]"]');
                for (let i = 0; i < lessonNameInputs.length; i++) {
                    const ln = lessonNameInputs[i].value.trim();
                    const lu = (lessonUrlInputs[i] ? lessonUrlInputs[i].value.trim() : '');
                    const lf = (lessonFileInputs[i] && lessonFileInputs[i].files && lessonFileInputs[i].files[0]) ? lessonFileInputs[i].files[0].name : '';
                    if (ln) session.lessons.push({ name: ln, url: lu, fileName: lf });
                }
                courseData.sessions.push(session);
            });

            console.log('Dữ liệu khóa học và chương (FE):', courseData);
            alert('Đã thu thập dữ liệu:\n- Khóa học: ' + courseData.name + '\n- Số chương: ' + courseData.sessions.length + '\n\n(Xem console để xem chi tiết - FE demo)');
            
            hideCourseForm();
        });
    }

    // Mở modal tạo khóa học và prefill từ card
    function editCourseFromCard(btn) {
        const card = btn.closest('.stat-card');
        if (!card) return;
        const name = card.getAttribute('data-course-name') || '';
        const level = card.getAttribute('data-course-level') || '';
        const price = card.getAttribute('data-course-price') || '';
        const status = card.getAttribute('data-course-status') || '';

        document.getElementById('courseName').value = name;
        document.getElementById('courseLevel').value = level;
        document.getElementById('coursePrice').value = price;
        document.getElementById('courseStatus').value = status;
        showCourseForm();
    }

    // Chuyển đến trang quản lý nội dung
    function viewCourseContent(btn) {
        const card = btn.closest('.stat-card');
        const name = card ? (card.getAttribute('data-course-name') || '') : '';
        const url = 'CourseContentManager.jsp' + (name ? ('?course=' + encodeURIComponent(name)) : '');
        window.location.href = url;
    }
</script>
    </body>
</html>