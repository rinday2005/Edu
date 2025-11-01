<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Quản lý Nội dung Khóa học - InstructorsHome</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/instructor/css/instructorsHome.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    </head>
    <body>
    <div class="main-container">
         <!-- ===== HEADER ===== -->
    <jsp:include page="/instructor/common/header.jsp" />

    <!-- ===== SIDEBAR ===== -->
   <jsp:include page="/instructor/common/sidebar.jsp" />

        <main class="content-area">
            <!-- Course Info Section -->
            <section class="section">
                <h2><i class="fas fa-video"></i> Quản lý Nội dung Khóa học</h2>
                <div class="card">
                    <div class="card-header">
                        <h3>React.js Cơ bản</h3>
                        <p style="margin: 5px 0; color: var(--text-muted);">Tổng số Section: 5 | Tổng thời lượng: 12 giờ 30 phút</p>
                    </div>
                </div>
            </section>

            <!-- Sections Management -->
            <section class="section">
                <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                    <h3><i class="fas fa-list"></i> Quản lý Sections</h3>
                    <button class="btn btn-primary" onclick="showSectionForm()">
                        <i class="fas fa-plus"></i> Thêm Section mới
                    </button>
                </div>

                <!-- Section 1 -->
                <div class="card" style="margin-bottom: 15px;">
                    <div class="card-header" style="cursor: pointer;" onclick="toggleSection('section1')">
                        <div style="display: flex; justify-content: space-between; align-items: center;">
                            <div>
                                <h4><i class="fas fa-folder"></i> Section 1: Giới thiệu React</h4>
                                <p style="margin: 5px 0; color: var(--text-muted);">3 bài giảng • 2 giờ 15 phút</p>
                            </div>
                            <div>
                                <i class="fas fa-chevron-down" id="section1-icon"></i>
                            </div>
                        </div>
                    </div>
                    <div class="card-body" id="section1-content" style="display: none;">
                        <!-- Lessons in Section 1 -->
                        <div style="margin-bottom: 15px;">
                            <h5>Bài giảng trong Section này:</h5>
                            <div class="activity-list">
                                <div class="activity-item">
                                    <div class="activity-icon">
                                        <i class="fas fa-play"></i>
                                    </div>
                                    <div class="activity-content">
                                        <h4>1.1 Giới thiệu React và JSX</h4>
                                        <p>Thời lượng: 45 phút • Trạng thái: Hoàn thành</p>
                                    </div>
                                    <div style="display: flex; gap: 10px;">
                                        <button class="btn btn-primary" style="padding: 5px 10px; font-size: 12px;">
                                            <i class="fas fa-edit"></i> Sửa
                                        </button>
                                        <button class="btn btn-secondary" style="padding: 5px 10px; font-size: 12px;">
                                            <i class="fas fa-plus"></i> Thêm Material
                                        </button>
                                    </div>
                                </div>
                                <div class="activity-item">
                                    <div class="activity-icon">
                                        <i class="fas fa-play"></i>
                                    </div>
                                    <div class="activity-content">
                                        <h4>1.2 Components và Props</h4>
                                        <p>Thời lượng: 50 phút • Trạng thái: Hoàn thành</p>
                                    </div>
                                    <div style="display: flex; gap: 10px;">
                                        <button class="btn btn-primary" style="padding: 5px 10px; font-size: 12px;">
                                            <i class="fas fa-edit"></i> Sửa
                                        </button>
                                        <button class="btn btn-secondary" style="padding: 5px 10px; font-size: 12px;">
                                            <i class="fas fa-plus"></i> Thêm Material
                                        </button>
                                    </div>
                                </div>
                                <div class="activity-item">
                                    <div class="activity-icon">
                                        <i class="fas fa-tasks"></i>
                                    </div>
                                    <div class="activity-content">
                                        <h4>Assignment: Tạo Component đầu tiên</h4>
                                        <p>Thời gian: 40 phút • Trạng thái: Hoàn thành</p>
                                    </div>
                                    <div style="display: flex; gap: 10px;">
                                        <button class="btn btn-primary" style="padding: 5px 10px; font-size: 12px;">
                                            <i class="fas fa-edit"></i> Sửa
                                        </button>
                                        <button class="btn btn-info" style="padding: 5px 10px; font-size: 12px; background-color: #17a2b8;">
                                            <i class="fas fa-question"></i> Quản lý Câu hỏi
                                        </button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div style="display: flex; gap: 10px; margin-top: 15px;">
                            <button class="btn btn-primary" style="padding: 8px 15px; font-size: 12px;">
                                <i class="fas fa-plus"></i> Thêm Bài giảng
                            </button>
                            <button class="btn btn-success" style="padding: 8px 15px; font-size: 12px;">
                                <i class="fas fa-plus"></i> Thêm Assignment
                            </button>
                            <button class="btn btn-secondary" style="padding: 8px 15px; font-size: 12px;">
                                <i class="fas fa-edit"></i> Sửa Section
                            </button>
                            <button class="btn btn-danger" style="padding: 8px 15px; font-size: 12px;">
                                <i class="fas fa-trash"></i> Xóa Section
                            </button>
                        </div>
                    </div>
                </div>

<!--                 Section 2 
                <div class="card" style="margin-bottom: 15px;">
                    <div class="card-header" style="cursor: pointer;" onclick="toggleSection('section2')">
                        <div style="display: flex; justify-content: space-between; align-items: center;">
                            <div>
                                <h4><i class="fas fa-folder"></i> Section 2: State và Lifecycle</h4>
                                <p style="margin: 5px 0; color: var(--text-muted);">4 bài giảng • 3 giờ 20 phút</p>
                            </div>
                            <div>
                                <i class="fas fa-chevron-down" id="section2-icon"></i>
                            </div>
                        </div>
                    </div>
                    <div class="card-body" id="section2-content" style="display: none;">
                        <p style="color: var(--text-muted);">Nội dung Section 2 sẽ được hiển thị khi click để mở rộng...</p>
                    </div>
                </div>

                 Section 3 
                <div class="card" style="margin-bottom: 15px;">
                    <div class="card-header" style="cursor: pointer;" onclick="toggleSection('section3')">
                        <div style="display: flex; justify-content: space-between; align-items: center;">
                            <div>
                                <h4><i class="fas fa-folder"></i> Section 3: Hooks và Functional Components</h4>
                                <p style="margin: 5px 0; color: var(--text-muted);">5 bài giảng • 4 giờ 10 phút</p>
                            </div>
                            <div>
                                <i class="fas fa-chevron-down" id="section3-icon"></i>
                            </div>
                        </div>
                    </div>
                    <div class="card-body" id="section3-content" style="display: none;">
                        <p style="color: var(--text-muted);">Nội dung Section 3 sẽ được hiển thị khi click để mở rộng...</p>
                    </div>
                </div>-->
            </section>

            <!-- Add Section Form Modal -->
            <div id="sectionFormModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); z-index: 1000;">
                <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: var(--bg-card); padding: 30px; border-radius: 8px; width: 90%; max-width: 500px;">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                        <h3><i class="fas fa-plus"></i> Thêm Section mới</h3>
                        <button onclick="hideSectionForm()" style="background: none; border: none; font-size: 24px; cursor: pointer;">&times;</button>
                    </div>
                    
                    <form>
                        <div class="form-group">
                            <label for="sectionName">Tên Section *</label>
                            <input type="text" id="sectionName" class="form-control" placeholder="Nhập tên section" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="sectionDescription">Mô tả Section</label>
                            <textarea id="sectionDescription" class="form-control" rows="3" placeholder="Mô tả về nội dung section"></textarea>
                        </div>
                        
                        <div style="display: flex; gap: 10px; justify-content: flex-end; margin-top: 20px;">
                            <button type="button" class="btn btn-secondary" onclick="hideSectionForm()">Hủy</button>
                            <button type="submit" class="btn btn-primary">Tạo Section</button>
                        </div>
                    </form>
                </div>
            </div>

            <!-- Add Lesson Form Modal -->
            <div id="lessonFormModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); z-index: 1000;">
                <div style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); background-color: var(--bg-card); padding: 30px; border-radius: 8px; width: 90%; max-width: 600px;">
                    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                        <h3><i class="fas fa-plus"></i> Thêm Bài giảng mới</h3>
                        <button onclick="hideLessonForm()" style="background: none; border: none; font-size: 24px; cursor: pointer;">&times;</button>
                    </div>
                    
                    <form>
                        <div class="form-group">
                            <label for="lessonName">Tên bài giảng *</label>
                            <input type="text" id="lessonName" class="form-control" placeholder="Nhập tên bài giảng" required>
                        </div>
                        
                        <div class="form-group">
                            <label for="lessonDescription">Mô tả bài giảng</label>
                            <textarea id="lessonDescription" class="form-control" rows="3" placeholder="Mô tả về nội dung bài giảng"></textarea>
                        </div>
                        
                        <div class="form-group">
                            <label for="videoUrl">URL Video *</label>
                            <input type="url" id="videoUrl" class="form-control" placeholder="https://youtube.com/watch?v=..." required>
                        </div>
                        
                        <div class="form-group">
                            <label for="videoDuration">Thời lượng (phút) *</label>
                            <input type="number" id="videoDuration" class="form-control" placeholder="45" min="1" required>
                        </div>
                        
                        <div style="display: flex; gap: 10px; justify-content: flex-end; margin-top: 20px;">
                            <button type="button" class="btn btn-secondary" onclick="hideLessonForm()">Hủy</button>
                            <button type="submit" class="btn btn-primary">Tạo Bài giảng</button>
                        </div>
                    </form>
                </div>
            </div>
        </main>
    </div>

    <script src="${pageContext.request.contextPath}/assets/js/instructorsHome.js"></script>
    <script>
        function toggleSection(sectionId) {
            const content = document.getElementById(sectionId + '-content');
            const icon = document.getElementById(sectionId + '-icon');
            
            if (content.style.display === 'none') {
                content.style.display = 'block';
                icon.className = 'fas fa-chevron-up';
            } else {
                content.style.display = 'none';
                icon.className = 'fas fa-chevron-down';
            }
        }
        
        function showSectionForm() {
            document.getElementById('sectionFormModal').style.display = 'block';
        }
        
        function hideSectionForm() {
            document.getElementById('sectionFormModal').style.display = 'none';
        }
        
        function showLessonForm() {
            document.getElementById('lessonFormModal').style.display = 'block';
        }
        
        function hideLessonForm() {
            document.getElementById('lessonFormModal').style.display = 'none';
        }
        
        // Close modals when clicking outside
        window.onclick = function(event) {
            const sectionModal = document.getElementById('sectionFormModal');
            const lessonModal = document.getElementById('lessonFormModal');
            
            if (event.target === sectionModal) {
                hideSectionForm();
            }
            if (event.target === lessonModal) {
                hideLessonForm();
            }
        }
    </script>
    </body>
</html>