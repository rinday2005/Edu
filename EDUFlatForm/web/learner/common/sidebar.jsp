<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- SIDEBAR COMPONENT -->
<aside class="sidebar">
    <nav class="sidebar-nav">
        <!-- Home - Trang chủ -->
        <a href="${pageContext.request.contextPath}/learner/jsp/Home/home.jsp" class="nav-item">
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
    </nav>
</aside>

<script>
function setActiveSidebar() {
    const currentPage = window.location.pathname;
    const navItems = document.querySelectorAll('.nav-item');
    
    navItems.forEach(item => {
        item.classList.remove('active');
        const href = item.getAttribute('href');
        if (currentPage.endsWith(href.split('/').pop())) {
            item.classList.add('active');
        }
    });
}

window.addEventListener('DOMContentLoaded', setActiveSidebar);
</script>
