(function(){
    'use strict';
    
    var avatarInput = document.getElementById('avatarUrlInput');
    var avatarPreview = document.getElementById('avatarPreview');
    
    if (!avatarInput || !avatarPreview) return;
    
    // Get default avatar from the onerror handler or use fallback
    var defaultAvatar = '';
    try {
        var onerrorAttr = avatarPreview.getAttribute('onerror');
        if (onerrorAttr) {
            var match = onerrorAttr.match(/src=['"]([^'"]+)['"]/);
            if (match) defaultAvatar = match[1];
        }
    } catch(e) {}
    if (!defaultAvatar) {
        // Fallback: try to get from current src or use common path
        var currentSrc = avatarPreview.src;
        var pathMatch = currentSrc.match(/^(.+\/uploads\/avatar\/)/);
        defaultAvatar = pathMatch ? pathMatch[1] + 'default.png' : '/uploads/avatar/default.png';
    }
    
    // Prevent flickering by preloading and using error handler
    function updatePreview() {
        var url = avatarInput.value.trim();
        if (!url) {
            avatarPreview.src = defaultAvatar;
            return;
        }
        
        // Create new image to test if URL is valid (prevents flickering)
        var testImg = new Image();
        var loading = false;
        
        testImg.onload = function() {
            // URL is valid, update preview with cache-busting timestamp
            var separator = url.indexOf('?') >= 0 ? '&' : '?';
            avatarPreview.src = url + separator + 't=' + Date.now();
            loading = false;
        };
        
        testImg.onerror = function() {
            // URL is invalid, use default
            avatarPreview.src = defaultAvatar;
            loading = false;
        };
        
        // Prevent multiple simultaneous loads
        if (!loading) {
            loading = true;
            testImg.src = url;
        }
    }
    
    // Update preview on input with debounce
    var updateTimeout = null;
    avatarInput.addEventListener('input', function() {
        if (updateTimeout) clearTimeout(updateTimeout);
        updateTimeout = setTimeout(updatePreview, 300);
    });
    
    avatarInput.addEventListener('paste', function() {
        setTimeout(function() {
            if (updateTimeout) clearTimeout(updateTimeout);
            updateTimeout = setTimeout(updatePreview, 100);
        }, 10);
    });
    
    // Prevent form submission if URL is invalid (FE validation only)
    var form = avatarInput.closest('form');
    if (form) {
        form.addEventListener('submit', function(e) {
            var url = avatarInput.value.trim();
            if (url) {
                // Basic URL validation
                var isValid = false;
                try {
                    new URL(url);
                    isValid = true;
                } catch(err) {
                    // Allow relative paths
                    if (url.startsWith('/') || url.startsWith('../') || url.startsWith('./')) {
                        isValid = true;
                    }
                }
                if (!isValid) {
                    alert('URL không hợp lệ. Vui lòng nhập URL đầy đủ (http/https) hoặc đường dẫn tương đối.');
                    e.preventDefault();
                    return false;
                }
            }
        });
    }
    
    // Initial preview update
    updatePreview();
    
    // Update header avatar after successful save (FE only)
    function updateHeaderAvatar() {
        var headerAvatar = document.querySelector('.admin-header .avatar');
        if (headerAvatar && avatarInput) {
            var newUrl = avatarInput.value.trim();
            if (newUrl) {
                // Add cache-busting timestamp
                var separator = newUrl.indexOf('?') >= 0 ? '&' : '?';
                headerAvatar.src = newUrl + separator + 't=' + Date.now();
                // Store in localStorage for persistence
                try {
                    localStorage.setItem('adminAvatarUrl', newUrl);
                } catch(e) {}
            } else {
                // Use default if empty
                var defaultPath = headerAvatar.getAttribute('onerror') ? 
                    headerAvatar.getAttribute('onerror').match(/src=['"]([^'"]+)['"]/)[1] : 
                    '/uploads/avatar/default.png';
                headerAvatar.src = defaultPath;
            }
        }
    }
    
    // Check if form was submitted successfully
    var messageDiv = document.querySelector('div[style*="color:green"]');
    if (messageDiv && messageDiv.textContent.trim().indexOf('thành công') !== -1) {
        // Update header avatar immediately after successful save
        updateHeaderAvatar();
    }
    
    // Update header avatar when input changes (live preview in header, debounced)
    var headerUpdateTimeout = null;
    avatarInput.addEventListener('input', function() {
        if (headerUpdateTimeout) clearTimeout(headerUpdateTimeout);
        headerUpdateTimeout = setTimeout(function() {
            var url = avatarInput.value.trim();
            if (url) {
                var headerAvatar = document.querySelector('.admin-header .avatar');
                if (headerAvatar) {
                    // Test URL first to avoid flickering
                    var testImg = new Image();
                    testImg.onload = function() {
                        var separator = url.indexOf('?') >= 0 ? '&' : '?';
                        headerAvatar.src = url + separator + 't=' + Date.now();
                    };
                    testImg.onerror = function() {
                        // Keep current avatar if URL is invalid
                    };
                    testImg.src = url;
                }
            }
        }, 500); // Debounce header update
    });
    
    // Load saved avatar from localStorage on page load (FE persistence)
    (function() {
        try {
            var savedUrl = localStorage.getItem('adminAvatarUrl');
            if (savedUrl) {
                var headerAvatar = document.querySelector('.admin-header .avatar');
                if (headerAvatar) {
                    var separator = savedUrl.indexOf('?') >= 0 ? '&' : '?';
                    headerAvatar.src = savedUrl + separator + 't=' + Date.now();
                }
            }
        } catch(e) {}
    })();
})();

