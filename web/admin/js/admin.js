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
           
        }
    };
    document.addEventListener('DOMContentLoaded', function () {
        window.EDUAdmin.init();
    });
})();

