$.ajax({
    type: 'GET',
    url: 'modules',
    success: function(objJson) {
        var list = objJson;
        var html = '';
        for (var i = 0; i < list.length; i++) {

            html += '<li class="bounceIn animated">' +
                '<form id="form_' + i + '" action="menu" method="GET" >' +
                '<input type="hidden" name="opc" value="List_Privilegios"/>' +
                '<input type="hidden" name="idmod" value="' + list[i].id_modulo + '"/>' +
                '<a onclick="document.getElementById(\'form_' + i + '\').submit();"  href="#" style="text-decoration: none;font-size:20px " >' +
                '<span class="icon">' +
                '<i aria-hidden="true" class="' + list[i].ic_modulo + '"></i>' +
                '</span>' +
                '<span>' + list[i].no_modulo + '</span>' +
                '</a>' +
                '</form>' +
                '</li>';

        }
        $(".modulesSelector").append(html);

    },
    error: function(xhr, ajaxOptions, thrownError) {

    }

});

//  The function to change the class
var changeClass = function(r, className1, className2) {
    var regex = new RegExp("(?:^|\\s+)" + className1 + "(?:\\s+|$)");
    if (regex.test(r.className)) {
        r.className = r.className.replace(regex, ' ' + className2 + ' ');
    } else {
        r.className = r.className.replace(new RegExp("(?:^|\\s+)" + className2 + "(?:\\s+|$)"), ' ' + className1 + ' ');
    }
    return r.className;
};

//  Creating our button in JS for smaller screens
var menuElements = document.getElementById('menu');
menuElements.insertAdjacentHTML('afterBegin', '<button type="button" id="menutoggle" class="navtoogle" aria-hidden="true"><i aria-hidden="true" class="icon-menu"> </i> Menu</button>');

//  Toggle the class on click to show / hide the menu
document.getElementById('menutoggle').onclick = function() {
    changeClass(this, 'navtoogle active', 'navtoogle');
}

// http://tympanus.net/codrops/2013/05/08/responsive-retina-ready-menu/comment-page-2/#comment-438918
document.onclick = function(e) {
    var mobileButton = document.getElementById('menutoggle'),
        buttonStyle = mobileButton.currentStyle ? mobileButton.currentStyle.display : getComputedStyle(mobileButton, null).display;

    if (buttonStyle === 'block' && e.target !== mobileButton && new RegExp(' ' + 'active' + ' ').test(' ' + mobileButton.className + ' ')) {
        changeClass(mobileButton, 'navtoogle active', 'navtoogle');
    }
}