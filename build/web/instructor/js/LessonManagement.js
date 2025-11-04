/* 
 * LessonManagement.js
 * Handles lesson management functionality for instructors
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
    
    // Get section ID
    function getSectionId() {
        const hiddenInput = document.querySelector('input[name="sectionId"]');
        if (hiddenInput) {
            return hiddenInput.value;
        }
        const mainContainer = document.querySelector('.main-container');
        if (mainContainer && mainContainer.dataset.sectionId) {
            return mainContainer.dataset.sectionId;
        }
        return '';
    }
    
    // Toggle create lesson form
    window.toggleCreateLessonForm = function() {
        const form = document.getElementById('createLessonForm');
        const btn = document.getElementById('toggleCreateBtn');
        if (form && btn) {
            if (form.style.display === 'none') {
                form.style.display = 'block';
                btn.innerHTML = '<i class="fas fa-chevron-up"></i> Ẩn Form';
            } else {
                form.style.display = 'none';
                btn.innerHTML = '<i class="fas fa-chevron-down"></i> Hiển thị Form';
            }
        }
    };
    
    // Cancel edit
    window.cancelEdit = function() {
        const sectionId = getSectionId();
        const contextPath = getContextPath();
        window.location.href = contextPath + '/ManageLesson?action=list&section=' + sectionId;
    };
    
    // Initialize on DOM ready
    document.addEventListener('DOMContentLoaded', function() {
        // Auto show form if editing or creating
        const mainContainer = document.querySelector('.main-container');
        const hasLesson = mainContainer && mainContainer.dataset.hasLesson === 'true';
        const isEdit = mainContainer && mainContainer.dataset.isEdit === 'true';
        const isCreate = mainContainer && mainContainer.dataset.isCreate === 'true';
        
        if ((hasLesson && isEdit) || isCreate) {
            toggleCreateLessonForm();
            const lessonName = document.getElementById('lessonName');
            if (lessonName) {
                lessonName.focus();
            }
        }
    });
    
})();

