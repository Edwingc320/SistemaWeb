(function ($) {
    "use strict";

    document.addEventListener("DOMContentLoaded", () => {

        const token = localStorage.getItem("token");

        if (!token) {
            console.warn("Sin token. Menú oculto.");
            return;
        }

        let payload;
        try {
            payload = JSON.parse(atob(token.split(".")[1]));
        } catch (e) {
            console.error("Error leyendo token");
            return;
        }

        const rol = payload.rol?.replace("ROLE_", "") || "USUARIO";

        console.log("ROL DETECTADO:", rol);

        // ********** HOME (todos) **********
        document.getElementById("menuHome").style.display = "block";

        // ********** ADMIN + JEFE DIVISION **********
        if (rol === "ADMIN" || rol === "JEFE_DIVISION") {
            document.getElementById("menuHome").style.display = "block";
            document.getElementById("menuIndexGrupo").style.display = "block";
            document.getElementById("menuCalGrupo").style.display = "block";
            document.getElementById("menuPeriodo").style.display = "block";
            document.getElementById("menuProfesores").style.display = "block";
            document.getElementById("menuUsuarios").style.display = "block";
        }

        // ********** ADMIN + MAESTRO + TUTOR + JEFE DIVISION **********
        if (["ADMIN", "MAESTRO", "TUTOR", "JEFE_DIVISION"].includes(rol)) {
            document.getElementById("menuHome").style.display = "block";
            document.getElementById("menuCalIndividual").style.display = "block";
            document.getElementById("menuGestionAlumnos").style.display = "block";
        }

        // ********** MAESTRO **********
        if (rol === "MAESTRO") {
             document.getElementById("menuHome").style.display = "block";
            document.getElementById("menuCalGrupo").style.display = "block";
        }

        // ********** TUTOR **********
        // No tiene calificacionesGrupo
        if (rol === "TUTOR") {
             document.getElementById("menuHome").style.display = "block";
            document.getElementById("menuCalGrupo").style.display = "block";
        }
    });


    function logout() {
        localStorage.clear();
        sessionStorage.clear();
        window.location.href = "/login";
    }

    
    // Dropdown on mouse hover
    $(document).ready(function () {
        function toggleNavbarMethod() {
            if ($(window).width() > 992) {
                $('.navbar .dropdown').on('mouseover', function () {
                    $('.dropdown-toggle', this).trigger('click');
                }).on('mouseout', function () {
                    $('.dropdown-toggle', this).trigger('click').blur();
                });
            } else {
                $('.navbar .dropdown').off('mouseover').off('mouseout');
            }
        }
        toggleNavbarMethod();
        $(window).resize(toggleNavbarMethod);
    });
    
    
    // Back to top button
    $(window).scroll(function () {
        if ($(this).scrollTop() > 100) {
            $('.back-to-top').fadeIn('slow');
        } else {
            $('.back-to-top').fadeOut('slow');
        }
    });
    $('.back-to-top').click(function () {
        $('html, body').animate({scrollTop: 0}, 1500, 'easeInOutExpo');
        return false;
    });


    // Date and time picker
    $('#date').datetimepicker({
        format: 'L'
    });
    $('#time').datetimepicker({
        format: 'LT'
    });


    // Service carousel
    $(".service-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        margin: 30,
        dots: false,
        loop: true,
        nav : true,
        navText : [
            '<i class="fa fa-angle-left" aria-hidden="true"></i>',
            '<i class="fa fa-angle-right" aria-hidden="true"></i>'
        ],
        responsive: {
            0:{
                items:1
            },
            576:{
                items:1
            },
            768:{
                items:2
            },
            992:{
                items:3
            }
        }
    });


    // Team carousel
    $(".team-carousel").owlCarousel({
        autoplay: true,
        smartSpeed: 1500,
        margin: 30,
        dots: false,
        loop: true,
        nav : true,
        navText : [
            '<i class="fa fa-angle-left" aria-hidden="true"></i>',
            '<i class="fa fa-angle-right" aria-hidden="true"></i>'
        ],
        responsive: {
            0:{
                items:1
            },
            576:{
                items:2
            },
            768:{
                items:3
            },
            992:{
                items:4
            }
        }
    });


    // Testimonials carousel
    $(".testimonial-carousel").owlCarousel({
        center: true,
        autoplay: true,
        smartSpeed: 1000,
        margin: 30,
        dots: true,
        loop: true,
        responsive: {
            0:{
                items:1
            },
            576:{
                items:1
            },
            768:{
                items:2
            },
            992:{
                items:3
            }
        }
    });
    
})(jQuery);