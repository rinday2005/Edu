/* 
 * ArticleManage.js
 * Handles article management functionality for instructors
 */

(function() {
    'use strict';
    
    // Show article form modal
    window.showArticleForm = function() {
        const modal = document.getElementById('articleFormModal');
        if (modal) {
            modal.style.display = 'block';
        }
        // Reset form
        const form = document.getElementById('createArticleForm');
        if (form) {
            form.reset();
        }
    };
    
    // Hide article form modal
    window.hideArticleForm = function() {
        const modal = document.getElementById('articleFormModal');
        if (modal) {
            modal.style.display = 'none';
        }
    };
    
    // Show edit article modal
    window.showEditArticle = function(articleId) {
        // Find button that was clicked to get data
        const buttons = document.querySelectorAll('.btn-edit[data-article-id]');
        let articleData = null;
        
        for (let btn of buttons) {
            if (btn.getAttribute('data-article-id') === articleId) {
                articleData = {
                    id: btn.getAttribute('data-article-id'),
                    title: btn.getAttribute('data-article-title'),
                    content: btn.getAttribute('data-article-content'),
                    status: btn.getAttribute('data-article-status')
                };
                break;
            }
        }
        
        if (!articleData) {
            alert('Không tìm thấy dữ liệu bài viết!');
            return;
        }
        
        // Fill form with article data
        const editArticleId = document.getElementById('editArticleId');
        const editArticleTitle = document.getElementById('editArticleTitle');
        const editArticleContent = document.getElementById('editArticleContent');
        const editArticleStatus = document.getElementById('editArticleStatus');
        
        if (editArticleId) editArticleId.value = articleData.id;
        if (editArticleTitle) editArticleTitle.value = articleData.title || '';
        if (editArticleContent) editArticleContent.value = articleData.content || '';
        if (editArticleStatus) editArticleStatus.value = articleData.status || 'Draft';
        
        // Show modal
        const modal = document.getElementById('editArticleModal');
        if (modal) {
            modal.style.display = 'block';
        }
    };
    
    // Hide edit article modal
    window.hideEditArticle = function() {
        const modal = document.getElementById('editArticleModal');
        if (modal) {
            modal.style.display = 'none';
        }
        // Reset form
        const form = document.getElementById('editArticleForm');
        if (form) {
            form.reset();
        }
    };
    
    // Close modal when clicking outside
    window.onclick = function(event) {
        const createModal = document.getElementById('articleFormModal');
        const editModal = document.getElementById('editArticleModal');
        
        if (event.target === createModal) {
            hideArticleForm();
        }
        if (event.target === editModal) {
            hideEditArticle();
        }
    };
    
})();

