let formSearch;

$(document).ready(function() {
	formSearch = $("form#searchFormHeader");
	
	formSearch.submit(function() {
		formSearch.children("button").attr("disabled", "disabled");
		return verifySearch();
	})
	
	formSearch.children("input").focus(function() {
		formSearch.children("button").removeAttr("disabled");
	})
})

function verifySearch() {
	if(formSearch.children("input").val() == "") {
		return false;
	}
	return true;
}