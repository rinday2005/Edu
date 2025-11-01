<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lộ Trình Học - E-Learning System</title>
    <!-- Updated CSS path to src/css/learning.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/learner/css/learning.css">
</head>
<body>
    <!-- Header -->
    <jsp:include page="/learner/common/header.jsp" />

    <div class="main-container">
        <!-- Sidebar -->
        <jsp:include page="/learner/common/sidebar.jsp" />

        <!-- Main Content -->
        <main class="main-content">
            <!-- Learning Path Section -->
            <section class="learning-path-section">
                <!-- Learning Path Header -->
                <div class="learning-path-intro">
                    <h1>Lộ trình học</h1>
                    <p>Để bắt đầu một cách thuận lợi, bạn nên tập trung vào một lộ trình học. Ví dụ: Để đi làm với vị trí "Lập trình viên Front-end" bạn nên tập trung vào lộ trình "Front-end".</p>
                </div>

                <!-- Learning Path Cards Grid -->
                <div class="learning-path-cards-grid">
                    <!-- Frontend Path Card -->
                    <div class="learning-path-card">
                        <div class="learning-path-card-content">
                            <h3>Lộ trình học Front-end</h3>
                            <p>Lập trình viên Front-end là người xây dựng ra giao diện websites. Trong phần này F8 sẽ chỉ cho bạn lộ trình để trở thành lập trình viên Front-end nhé.</p>
                            <a href="${pageContext.request.contextPath}/learner/jsp/Learning/LearningPathFE.jsp" class="learning-path-btn">XEM CHI TIẾT</a>
                        </div>
                        <div class="learning-path-card-icon">
                            <img src="${pageContext.request.contextPath}/learner/images/learningPath1.webp" alt="Frontend Learning Path" class="path-icon-image" />
                        </div>
                    </div>

                    <!-- Backend Path Card -->
                    <div class="learning-path-card">
                        <div class="learning-path-card-content">
                            <h3>Lộ trình học Back-end</h3>
                            <p>Trái với Front-end thì lộ trình Back-end là người làm việc với dữ liệu, công việc thường nằm ở lộ trình "Back-end" bạn nên tập trung vào lộ trình "Back-end" nhé.</p>
                            <a href="${pageContext.request.contextPath}/learner/jsp/Learning/LearningPathBE.jsp" class="learning-path-btn">XEM CHI TIẾT</a>
                        </div>
                        <div class="learning-path-card-icon">
                            <img src="${pageContext.request.contextPath}/learner/images/learningPath2.jpg" alt="Backend Learning Path" class="path-icon-image" />
                        </div>
                    </div>
                </div>

                <!-- Facebook Community Section -->

            </section>
        </main>
    </div>

<!--     Authentication Modal 
    <div id="authModal" class="modal" role="dialog" aria-modal="true" aria-labelledby="modalTitle" aria-describedby="modalSubtitle">
        <div class="modal-content">
            <button class="modal-close" onclick="closeAuthModal()" aria-label="Đóng">✕</button>
            
            <div class="modal-header">
                <div class="modal-logo-container">
                    <img src="${pageContext.request.contextPath}/learner/images/logo.jpg"
                         alt="Logo"
                         class="modal-logo-img"
                         width="64" height="64" decoding="async" />
                </div>
                <h2 id="modalTitle">Đăng nhập vào E-Learning System</h2>
                <p id="modalSubtitle">Mỗi người nên sử dụng riêng một tài khoản, tài khoản nhiều người sử dụng chung sẽ bị khóa.</p>
            </div>

            <div class="modal-body">
                <button class="auth-btn email-btn" id="emailToggleBtn" onclick="toggleAuthForm()">
                    <span id="emailBtnText">Sử dụng email / số điện thoại</span>
                </button>

                <button class="auth-btn google-btn" onclick="handleGoogleLogin()">
                    <span class="btn-icon">G</span>
                    <span id="googleBtnText">Đăng nhập với Google</span>
                </button>

                <form id="authForm" class="auth-form" onsubmit="event.preventDefault(); handleFormSubmit();">
                    <div class="form-group">
                        <input type="text" id="usernameInput" placeholder="Tên tài khoản" class="form-input" onchange="validateUsername()">
                        <span class="error-message" id="usernameError"></span>
                    </div>

                    <div class="form-group">
                        <input type="text" id="emailInput" placeholder="Email hoặc số điện thoại" class="form-input" onchange="validateEmail()">
                        <span class="error-message" id="emailError"></span>
                    </div>

                    <div class="form-group">
                        <div class="password-input-wrapper">
                            <input type="password" id="passwordInput" placeholder="Mật khẩu" class="form-input" onchange="validatePassword()">
                            <button type="button" class="password-toggle" onclick="togglePasswordVisibility()">👁</button>
                        </div>
                        <span class="error-message" id="passwordError"></span>
                    </div>

                    <div class="form-group" id="confirmPasswordGroup" style="display: none;">
                        <div class="password-input-wrapper">
                            <input type="password" id="confirmPasswordInput" placeholder="Xác nhận lại mật khẩu" class="form-input" onchange="validateConfirmPassword()">
                            <button type="button" class="password-toggle" onclick="toggleConfirmPasswordVisibility()">👁</button>
                        </div>
                        <span class="error-message" id="confirmPasswordError"></span>
                    </div>

                    <div class="form-group" id="rememberMeGroup" style="display: none;">
                        <label class="checkbox-label">
                            <input type="checkbox" id="rememberMe" class="checkbox-input">
                            <span>Ghi nhớ đăng nhập</span>
                        </label>
                    </div>

                    <button class="form-submit" id="submitBtn" type="submit">
                        <span id="submitBtnText">Tiếp tục</span>
                        <span id="loadingSpinner" class="loading-spinner" style="display: none;">⏳</span>
                    </button>

                    <span class="error-message" id="generalError"></span>
                </form>

                <div class="auth-footer">
                    <p id="toggleText">Bạn chưa có tài khoản? <a href="#" onclick="toggleAuthMode(event)">Đăng kí</a></p>
                    <p id="forgotText" style="display: none;"><a href="#" onclick="handleForgotPassword(event)">Quên mật khẩu?</a></p>
                </div>

                <p class="auth-disclaimer">Việc bạn tiếp tục sử dụng trang web này đồng nghĩa bạn đồng ý với <a href="#">điều khoản sử dụng</a> của chúng tôi.</p>
            </div>
        </div>
    </div>-->

    <!-- Footer -->
     <jsp:include page="/learner/common/footer.jsp" />

    <!-- Updated JS path to src/js/learning.js -->
    <script src="${pageContext.request.contextPath}/learner/js/learning.js"></script>
</body>
</html>
