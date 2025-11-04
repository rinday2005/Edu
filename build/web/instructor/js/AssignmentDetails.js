/* 
 * AssignmentDetails.js
 * Handles assignment management functionality for instructors
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
    
    // Toggle create assignment form
    window.toggleCreateAssignmentForm = function() {
        const form = document.getElementById('createAssignmentForm');
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
        const form = document.getElementById('assignmentForm');
        if (form) {
            form.reset();
        }
        toggleCreateAssignmentForm();
        
        // Check if we're in edit mode (has assignment data attribute)
        const mainContainer = document.querySelector('.main-container');
        if (mainContainer && mainContainer.dataset.hasAssignment === 'true') {
            const contextPath = getContextPath();
            window.location.href = contextPath + '/ManageAssignment?action=list';
        }
    };
    
    // Initialize Course-Section filter
    function initCourseSectionFilter() {
        const courseSel = document.getElementById('courseSelect');
        const sectionSel = document.getElementById('sectionSelect');
        const lessionSel = document.getElementById('lessionSelect');
        if (!courseSel || !sectionSel || !lessionSel) return;
        
        const allSectionOptions = Array.from(sectionSel.querySelectorAll('option'));
        const allLessionOptions = Array.from(lessionSel.querySelectorAll('option'));
        
        function applySectionFilter() {
            const cid = courseSel.value;
            const selectedVal = sectionSel.value;
            // keep first placeholder
            sectionSel.innerHTML = '';
            const placeholder = document.createElement('option');
            placeholder.value = '';
            placeholder.textContent = '-- Chọn phần --';
            sectionSel.appendChild(placeholder);
            allSectionOptions.forEach(op => {
                if (!op.value) return; // skip old placeholder
                const oc = op.getAttribute('data-course-id');
                if (!cid || (oc && oc.toUpperCase() === cid.toUpperCase())) {
                    sectionSel.appendChild(op);
                }
            });
            // restore selection if still available
            if (selectedVal) {
                const found = Array.from(sectionSel.options).some(o => o.value === selectedVal);
                if (found) sectionSel.value = selectedVal;
            }
            // Update lesson filter when section changes
            applyLessionFilter();
        }
        
        function applyLessionFilter() {
            const sid = sectionSel.value;
            const selectedVal = lessionSel.value;
            // keep first placeholder
            lessionSel.innerHTML = '';
            const placeholder = document.createElement('option');
            placeholder.value = '';
            placeholder.textContent = '-- Chọn bài học (để test hiển thị trong lesson) --';
            lessionSel.appendChild(placeholder);
            allLessionOptions.forEach(op => {
                if (!op.value) return; // skip old placeholder
                const lsid = op.getAttribute('data-section-id');
                if (!sid || (lsid && lsid.toUpperCase() === sid.toUpperCase())) {
                    lessionSel.appendChild(op);
                }
            });
            // restore selection if still available
            if (selectedVal) {
                const found = Array.from(lessionSel.options).some(o => o.value === selectedVal);
                if (found) lessionSel.value = selectedVal;
            }
        }
        
        courseSel.addEventListener('change', applySectionFilter);
        sectionSel.addEventListener('change', applyLessionFilter);
        
        // Preselect course based on current section's data-course-id
        const currentSelected = sectionSel.querySelector('option[selected]');
        const currentCourse = currentSelected ? currentSelected.getAttribute('data-course-id') : '';
        if (currentCourse) courseSel.value = currentCourse;
        applySectionFilter();
    }
    
    // Initialize on DOM ready
    document.addEventListener('DOMContentLoaded', function() {
        // Auto show form if editing (check for assignment data attribute)
        const mainContainer = document.querySelector('.main-container');
        if (mainContainer && mainContainer.dataset.hasAssignment === 'true') {
            const form = document.getElementById('createAssignmentForm');
            const toggleBtn = document.getElementById('toggleCreateBtn');
            if (form) {
                form.style.display = 'block';
            }
            if (toggleBtn) {
                toggleBtn.innerHTML = '<i class="fas fa-chevron-up"></i> Ẩn Form';
            }
        }
        
        // Initialize course-section filter
        initCourseSectionFilter();
    });
    
})();

