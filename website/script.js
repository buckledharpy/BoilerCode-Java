function initMobileMenu() {
    console.log("CS Exercises Website Loaded!");

    const btn = document.getElementById('menu-toggle');
    const nav = document.getElementById('main-nav');

    if (!btn || !nav) {
        console.debug('Mobile menu: no #menu-toggle or #main-nav found on this page.');
        return;
    }

    function setOpen(isOpen) {
        btn.setAttribute('aria-expanded', isOpen ? 'true' : 'false');
        const overlay = getOrCreateOverlay();
        // ensure close button exists inside nav
        getOrCreateNavClose();
        if (isOpen) {
            nav.classList.add('open');
            btn.classList.add('open');
            overlay.classList.add('open');
            document.body.classList.add('no-scroll');
        } else {
            nav.classList.remove('open');
            btn.classList.remove('open');
            overlay.classList.remove('open');
            document.body.classList.remove('no-scroll');
        }
    }

    btn.addEventListener('click', (e) => {
        const isOpen = btn.getAttribute('aria-expanded') === 'true';
        setOpen(!isOpen);
    });

    // close when clicking outside the nav (on small screens)
    document.addEventListener('click', (e) => {
        if (!nav.classList.contains('open')) return;
        const target = e.target;
        if (target === btn || btn.contains(target) || nav.contains(target)) return;
        setOpen(false);
    });

    // overlay click will close the nav — create overlay element if needed
    function getOrCreateOverlay() {
        let overlay = document.getElementById('nav-overlay');
        if (!overlay) {
            overlay = document.createElement('div');
            overlay.id = 'nav-overlay';
            document.body.appendChild(overlay);
            overlay.addEventListener('click', () => setOpen(false));
        }
        return overlay;
    }

    // create a close (X) button inside the nav if not present
    function getOrCreateNavClose() {
        let closeBtn = nav.querySelector('.nav-close');
        if (!closeBtn) {
            closeBtn = document.createElement('button');
            closeBtn.type = 'button';
            closeBtn.className = 'nav-close';
            closeBtn.setAttribute('aria-label', 'Close menu');
            closeBtn.innerHTML = '&times;';
            closeBtn.addEventListener('click', () => setOpen(false));
            nav.appendChild(closeBtn);
        }
        return closeBtn;
    }

    // close on Escape
    document.addEventListener('keydown', (e) => {
        if (e.key === 'Escape' && nav.classList.contains('open')) setOpen(false);
    });
}

// If the DOM is already ready, init immediately; otherwise wait for DOMContentLoaded.
if (document.readyState === 'loading') {
    window.addEventListener('DOMContentLoaded', initMobileMenu);
} else {
    initMobileMenu();
}



