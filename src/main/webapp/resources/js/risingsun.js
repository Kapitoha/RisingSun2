/**
 * 
 */
function invoke(url)
{
	document.location.href = url;
}
function dropdown(elemets) {
	$(elemets).on({
		"click" : function(e) {
			e.stopPropagation();
		}
	});
}