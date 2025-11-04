/* 
 * CourseManagement.js
 * Handles course management functionality for instructors
 */

(function() {
    'use strict';
    
    // Get context path
    function getContextPath() {
        const mainContainer = document.querySelector('.main-container');
        if (mainContainer && mainContainer.dataset.contextPath) {
            return mainContainer.dataset.contextPath;
        }
        const scripts = document.getElementsByTagName('script');
        for (let script of scripts) {
            if (script.src && script.src.includes('/instructor/js/')) {
                const match = script.src.match(/^(.*?)\/instructor\/js\//);
                if (match) {
                    return match[1];
                }
            }
        }
        return '';
    }
    
    // Show course form modal
    window.showCourseForm = function() {
        const modal = document.getElementById('courseFormModal');
        if (modal) {
            modal.style.display = 'block';
            // Khởi tạo 1 dòng chương mặc định nếu chưa có
            const sessionsContainer = document.getElementById('sessionsContainer');
            if (sessionsContainer && sessionsContainer.children.length === 0) {
                // addSessionRow(); // Uncomment if needed
            }
        }
    };
    
    // Hide course form modal
    window.hideCourseForm = function() {
        const modal = document.getElementById('courseFormModal');
        if (modal) {
            modal.style.display = 'none';
        }
        // Reset form when closing
        const form = document.getElementById('createCourseForm');
        if (form) {
            form.reset();
            const formAction = document.getElementById('formAction');
            const courseIdInput = document.getElementById('courseIdInput');
            const modalTitle = document.getElementById('modalTitle');
            const submitBtn = document.getElementById('submitBtn');
            
            if (formAction) formAction.value = 'createcourse';
            if (courseIdInput) courseIdInput.value = '';
            if (modalTitle) modalTitle.innerHTML = '<i class="fas fa-plus"></i> Tạo khóa học mới';
            if (submitBtn) submitBtn.textContent = 'Tạo khóa học';
            
            const container = document.getElementById('sessionsContainer');
            if (container) container.innerHTML = '';
        }
    };
    
    // Edit course from card
    window.editCourseFromCard = function(courseId) {
        const contextPath = getContextPath();
        window.location.href = contextPath + '/ManageCourse?action=edit&courseId=' + courseId;
    };
    
    // View course content
    window.viewCourseContent = function(courseId) {
        const contextPath = getContextPath();
        window.location.href = contextPath + '/ManageCourse?action=view&courseId=' + courseId;
    };
    
    // Hide view course modal
    window.hideViewCourse = function() {
        const modal = document.getElementById('viewCourseModal');
        if (modal) {
            modal.style.display = 'none';
        }
        // Redirect về trang list để xóa viewCourse từ request
        const contextPath = getContextPath();
        window.location.href = contextPath + '/ManageCourse';
    };
    
    // Initialize on DOM ready
    document.addEventListener('DOMContentLoaded', function() {
        const mainContainer = document.querySelector('.main-container');
        const contextPath = getContextPath();
        
        // Auto open edit modal when course exists from server
        const hasCourse = mainContainer && mainContainer.dataset.hasCourse === 'true';
        const isEdit = mainContainer && mainContainer.dataset.isEdit === 'true';
        
        if (hasCourse && isEdit) {
            // Prefill form with course data
            const courseId = mainContainer.dataset.courseId || '';
            const courseName = mainContainer.dataset.courseName || '';
            const courseDescription = mainContainer.dataset.courseDescription || '';
            const coursePrice = mainContainer.dataset.coursePrice || '0';
            const courseLevel = mainContainer.dataset.courseLevel || '';
            const courseApproved = mainContainer.dataset.courseApproved || 'false';
            
            const formAction = document.getElementById('formAction');
            const courseIdInput = document.getElementById('courseIdInput');
            const modalTitle = document.getElementById('modalTitle');
            const submitBtn = document.getElementById('submitBtn');
            const courseNameInput = document.getElementById('courseName');
            const courseDescriptionInput = document.getElementById('courseDescription');
            const coursePriceInput = document.getElementById('coursePrice');
            const courseLevelSelect = document.getElementById('courseLevel');
            const courseStatusSelect = document.getElementById('courseStatus');
            
            if (formAction) formAction.value = 'updatecourse';
            if (courseIdInput) courseIdInput.value = courseId;
            if (modalTitle) modalTitle.innerHTML = '<i class="fas fa-edit"></i> Chỉnh sửa khóa học';
            if (submitBtn) submitBtn.textContent = 'Cập nhật khóa học';
            
            if (courseNameInput) courseNameInput.value = decodeHtmlEntities(courseName);
            if (courseDescriptionInput) courseDescriptionInput.value = decodeHtmlEntities(courseDescription);
            if (coursePriceInput) coursePriceInput.value = coursePrice;
            if (courseLevelSelect) courseLevelSelect.value = decodeHtmlEntities(courseLevel);
            if (courseStatusSelect) courseStatusSelect.value = courseApproved === 'true' ? 'true' : 'false';
            
            const modal = document.getElementById('courseFormModal');
            if (modal) modal.style.display = 'block';
        }
        
        // Auto open view modal when viewCourse exists
        const hasViewCourse = mainContainer && mainContainer.dataset.hasViewCourse === 'true';
        if (hasViewCourse) {
            const viewModal = document.getElementById('viewCourseModal');
            if (viewModal) viewModal.style.display = 'block';
        }
    });
    
    // Helper function to decode HTML entities
    function decodeHtmlEntities(text) {
        const textarea = document.createElement('textarea');
        textarea.innerHTML = text;
        return textarea.value;
    }
    
})();

