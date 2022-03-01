function showMoreVati(row) {
	let itis = "tr#rowVati" + row;
	$(itis).toggle();
}
function showMoreVaty(row) {
	let itis = "tr#rowVaty" + row;
	$(itis).toggle();
}
function VatiOb() {
}

VatiOb.prototype = {
	constructor: VatiOb,
	isElementInView: function(element, fullyInView) {
		var pageTop = $(window).scrollTop();
		var pageBottom = pageTop + $(window).height();
		var elementTop = $(element).offset().top;
		var elementBottom = elementTop + $(element).height();

		if (fullyInView === true) {
			return ((pageTop < elementTop) && (pageBottom > elementBottom));
		} else {
			return ((elementTop <= pageBottom) && (elementBottom >= pageTop));
		}
	}
};

let labelOfDonateButton = $("div#labelOfDonateButton");
let Vati = new VatiOb();
$(window).scroll(function() {
	let isDonateButtonVisible = Vati.isElementInView($("input#DonateNow"), true);
	if (isDonateButtonVisible) {
		labelOfDonateButton.fadeOut(300);
	} else {
		labelOfDonateButton.fadeIn(500);
	}
})