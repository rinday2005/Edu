<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- SIDEBAR COMPONENT -->
<aside class="sidebar" data-user-role="<%= session.getAttribute("role") != null ? session.getAttribute("role") : (session.getAttribute("user") != null ? ((model.User)session.getAttribute("user")).getRole() : "") %>">
    <nav class="sidebar-nav">
        <!-- Home - Trang chủ -->
        <a href="${pageContext.request.contextPath}/CourseServletController" class="nav-item" id="homeNavItem">
            <div class="nav-icon">
                <svg width="32" height="32" viewBox="0 0 32 32" fill="currentColor">
                    <path d="M16 2L4 12V28H12V18H20V28H28V12L16 2Z"/>
                </svg>
            </div>
            <span class="nav-text">Trang chủ</span>
        </a>

        <!-- Learning Path - Lộ trình -->
        <a href="${pageContext.request.contextPath}/learner/jsp/Learning/LearningPath.jsp" class="nav-item">
            <div class="nav-icon">
                <svg width="32" height="32" viewBox="0 0 32 32" fill="currentColor">
                    <g>
                        <circle cx="8" cy="8" r="2"/>
                        <circle cx="16" cy="8" r="2"/>
                        <circle cx="24" cy="8" r="2"/>
                        <rect x="6" y="12" width="4" height="12" rx="1"/>
                        <rect x="14" y="12" width="4" height="12" rx="1"/>
                        <rect x="22" y="12" width="4" height="12" rx="1"/>
                    </g>
                </svg>
            </div>
            <span class="nav-text">Lộ trình</span>
        </a>

        <!-- Articles - Bài viết -->
        <a href="${pageContext.request.contextPath}/learner/jsp/Article/articles.jsp" class="nav-item">
            <div class="nav-icon">
                <svg width="32" height="32" viewBox="0 0 32 32" fill="currentColor">
                    <rect x="4" y="4" width="24" height="24" rx="2" fill="none" stroke="currentColor" stroke-width="2"/>
                    <line x1="8" y1="10" x2="24" y2="10" stroke="currentColor" stroke-width="1.5"/>
                    <line x1="8" y1="15" x2="24" y2="15" stroke="currentColor" stroke-width="1.5"/>
                    <line x1="8" y1="20" x2="20" y2="20" stroke="currentColor" stroke-width="1.5"/>
                </svg>
            </div>
            <span class="nav-text">Bài viết</span>
        </a>

        <!-- Back to Instructor - FE only, only show for Instructor accounts -->
        <a href="#" id="backToInstructorBtn" class="nav-item back-nav-item" style="display:none; margin-top:auto; border-top:1px solid rgba(0,0,0,0.1); padding-top:12px;">
            <div class="nav-icon">
                <svg width="32" height="32" viewBox="0 0 32 32" fill="currentColor">
                    <path d="M20 24L12 16L20 8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
                </svg>
            </div>
<span class="nav-text">Back to Instructor</span>
        </a>
        
        <!-- Back to Admin - FE only, only show for Admin accounts -->
        <a href="#" id="backToAdminBtn" class="nav-item back-nav-item" style="display:none; border-top:1px solid rgba(0,0,0,0.1); padding-top:12px;">
            <div class="nav-icon">
                <svg width="32" height="32" viewBox="0 0 32 32" fill="currentColor">
                    <path d="M20 24L12 16L20 8" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" fill="none"/>
                </svg>
            </div>
            <span class="nav-text">Back to Admin</span>
        </a>
    </nav>
</aside>

<script>
function setActiveSidebar() {
    const currentPage = window.location.pathname;
    const navItems = document.querySelectorAll('.nav-item');
    
    navItems.forEach(item => {
        item.classList.remove('active');
        const href = item.getAttribute('href');
        if (href && !href.startsWith('#') && currentPage.endsWith(href.split('/').pop())) {
            item.classList.add('active');
        }
    });
}

window.addEventListener('DOMContentLoaded', setActiveSidebar);

// FE-only: Show "Back to Instructor" button only for Instructor accounts
(function(){
    // Check if user is Instructor (FE only - read from data attribute set by JSP)
    var isInstructor = false;
    
    // Get role from sidebar data attribute (set by JSP from session)
    var sidebar = document.querySelector('.sidebar');
    if (sidebar) {
        var userRole = sidebar.getAttribute('data-user-role');
        if (userRole && userRole.toLowerCase().indexOf('instructor') !== -1) {
            isInstructor = true;
        }
    }
    
    // Fallback: Try to detect from page context (if role is displayed somewhere)
    if (!isInstructor) {
        var roleElements = document.querySelectorAll('[class*="role"], [class*="Role"], .user-role, [data-role]');
        for (var i = 0; i < roleElements.length; i++) {
            var text = (roleElements[i].textContent || roleElements[i].innerText || '').toLowerCase();
            if (text.indexOf('instructor') !== -1 || text.indexOf('giảng viên') !== -1) {
                isInstructor = true;
                break;
            }
        }
    }
    
    // Fallback: Check localStorage (set by other pages)
    if (!isInstructor) {
        try {
            var savedRole = localStorage.getItem('userRole');
            if (savedRole && savedRole.toLowerCase().indexOf('instructor') !== -1) {
                isInstructor = true;
            }
        } catch(e) {}
    }
    
    // Check if we're on a learner page (not instructor page)
    var isLearnerPage = location.pathname.indexOf('/learner/') !== -1 || 
                        location.pathname.indexOf('/CourseServletController') !== -1 ||
                        location.pathname.indexOf('/learner/jsp/') !== -1;
// Instructor đóng 2 vai trò nên có thể xem CourseServletController
    // Không cần protection nữa
    
    // Check if user is Admin
    var isAdmin = false;
    if (sidebar) {
        var adminRole = sidebar.getAttribute('data-user-role');
        if (adminRole && adminRole.toLowerCase().indexOf('admin') !== -1) {
            isAdmin = true;
        }
    }
    
    // Show Back to Instructor button only if user is Instructor AND on learner page
    var backBtn = document.getElementById('backToInstructorBtn');
    if (backBtn && isInstructor && isLearnerPage) {
        backBtn.style.display = 'flex';
        
        backBtn.addEventListener('click', function(e){
            e.preventDefault();
            var base = (function(){
                var path = location.pathname;
                var idx = path.indexOf('/learner/');
                if (idx < 0) idx = path.indexOf('/CourseServletController');
                return idx >= 0 ? path.substring(0, idx) : '';
            })();
            window.location.href = base + '/instructor/dashboard';
        });
    }
    
    // Show Back to Admin button only if user is Admin AND on learner page
    var backToAdminBtn = document.getElementById('backToAdminBtn');
    if (backToAdminBtn && isAdmin && isLearnerPage) {
        backToAdminBtn.style.display = 'flex';
        
        backToAdminBtn.addEventListener('click', function(e){
            e.preventDefault();
            var base = (function(){
                var path = location.pathname;
                var idx = path.indexOf('/learner/');
                if (idx < 0) idx = path.indexOf('/CourseServletController');
                return idx >= 0 ? path.substring(0, idx) : '';
            })();
            window.location.href = base + '/admin/dashboard';
        });
    }
})();
</script>
