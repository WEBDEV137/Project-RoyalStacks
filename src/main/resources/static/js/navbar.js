// Wanneer er gescrollt wordt, voer functie uit
window.onscroll = function() {navBarFunction()};

const navbar = document.getElementById("navbar");

const sticky = navbar.offsetTop;

// Voeg sticky to aan navbar als er gescrollt wordt. Verwijder sticky wanneer dit stopt
function navBarFunction() {
    if (window.pageYOffset >= sticky) {
        navbar.classList.add("sticky")
    } else {
        navbar.classList.remove("sticky");
    }
}

document.getElementById("logoNavbar").innerHTML = "ROYAL STACKS ♔";