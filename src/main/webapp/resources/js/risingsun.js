/**
 *
 */
function invoke(url) {
    document.location.href = url;
}
function dropdown(elemets) {
    $(elemets).on({
        "click": function (e) {
            e.stopPropagation();
        }
    });
}

function toggle(blockId, hrefId) {
    var ele = document.getElementById(blockId);
    var text = document.getElementById(hrefId);
    if (ele.style.display == "block") {
        ele.style.display = "none";
        text.innerHTML = " Show ";
    }
    else {
        ele.style.display = "block";
        text.innerHTML = " Hide ";
    }
}
function pagination(tag) {
	$(tag).click(changePage);
	$('.portfolio-item').magnificPopup({ 
		type: 'image',
		gallery:{
			enabled:true
		}
	});
}