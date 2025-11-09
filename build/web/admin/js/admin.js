(function () {
    'use strict';
    
    window.EDUAdmin = {
        init: function () {
          
            var menu = document.getElementById('avatarMenu');
            if (menu) {
                var img = menu.querySelector('.avatar');
                if (img) {
                    img.addEventListener('click', function (e) {
                        e.stopPropagation();
                        menu.classList.toggle('open');
                    });
                }
                document.addEventListener('click', function () {
                    menu.classList.remove('open');
                });
            }
            
            // FE-only safe navigation for cross-role links - DISABLED
            // Các nút "Back to Instructor" và "Back to Learner" trong header đã có logic riêng
            // Không cần intercept nữa vì đã có script riêng trong header.jsp
            
            // Load avatar from localStorage if available (FE persistence)
            try {
                var savedAvatarUrl = localStorage.getItem('adminAvatarUrl');
                if (savedAvatarUrl) {
                    var headerAvatar = document.querySelector('.admin-header .avatar');
                    if (headerAvatar) {
                        var separator = savedAvatarUrl.indexOf('?') >= 0 ? '&' : '?';
                        headerAvatar.src = savedAvatarUrl + separator + 't=' + Date.now();
                    }
                }
            } catch(e) {}
        }
    };
    document.addEventListener('DOMContentLoaded', function () {
        window.EDUAdmin.init();
    });
})();
