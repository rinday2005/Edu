/* 
 * CourseSession.js
 * Handles course session management functionality for instructors
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
    
    // Toggle create section form
    window.toggleCreateSectionForm = function() {
        const form = document.getElementById('createSectionForm');
        const toggleBtn = document.getElementById('toggleCreateBtn');
        
        if (form.style.display === 'none') {
            form.style.display = 'block';
            toggleBtn.innerHTML = '<i class="fas fa-chevron-up"></i> Ẩn Form';
        } else {
            form.style.display = 'none';
            toggleBtn.innerHTML = '<i class="fas fa-chevron-down"></i> Hiển thị Form';
        }
    };
    
    // Cancel form
    window.cancelForm = function() {
        // Reset form and hide
        const form = document.getElementById('sectionForm');
        if (form) {
            form.reset();
        }
        toggleCreateSectionForm();
        
        // Check if we're in edit mode
        const mainContainer = document.querySelector('.main-container');
        const hasSection = mainContainer && mainContainer.dataset.hasSection === 'true';
        const courseId = mainContainer && mainContainer.dataset.courseId;
        
        if (hasSection) {
            const contextPath = getContextPath();
            if (courseId) {
                window.location.href = contextPath + '/ManageSection?course=' + courseId;
            } else {
                window.location.href = contextPath + '/ManageSection';
            }
        }
    };
    
    // Initialize on DOM ready
    document.addEventListener('DOMContentLoaded', function() {
        // Auto show form if editing
        const mainContainer = document.querySelector('.main-container');
        if (mainContainer && mainContainer.dataset.hasSection === 'true') {
            const form = document.getElementById('createSectionForm');
            const toggleBtn = document.getElementById('toggleCreateBtn');
            if (form) {
                form.style.display = 'block';
            }
            if (toggleBtn) {
                toggleBtn.innerHTML = '<i class="fas fa-chevron-up"></i> Ẩn Form';
            }
        }
    });
    
})();

