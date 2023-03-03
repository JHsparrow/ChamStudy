document.addEventListener("DOMContentLoaded", function() {
	var mCateNum = $('#mCateNum').val(); 
	var mCateName = [];
	var mCateCount = [];
	for(var i=1; i<=mCateNum ; i++){
		mCateName.push($('#mCateName'+i).text());
		mCateCount.push($('#mCateCount'+i).text());
	}
	new Chart(document.getElementById("chartjs-dashboard-pie1"), {
		type: "doughnut",
		data: {
			labels: mCateName,
			datasets: [{
				data: mCateCount,
				borderWidth: 5
			}]
		},
		options: {maintainAspectRatio: false}
		
	});
});
document.addEventListener("DOMContentLoaded", function() {
	var s1CateNum = $('#s1CateNum').val(); 
	var s1CateName = [];
	var s1CateCount = [];
	for(var i=1; i<=s1CateNum ; i++){
		s1CateName.push($('#s1CateName'+i).text());
		s1CateCount.push($('#s1CateCount'+i).text());
	}
	new Chart(document.getElementById("chartjs-dashboard-pie2"), {
		type: "doughnut",
		data: {
			labels: s1CateName,
			datasets: [{
				data: s1CateCount,
				borderWidth: 5
			}]
		},
		options: {maintainAspectRatio: false}
		
	});
});
document.addEventListener("DOMContentLoaded", function() {
	var s2CateNum = $('#s2CateNum').val(); 
	var s2CateName = [];
	var s2CateCount = [];
	for(var i=1; i<=s2CateNum ; i++){
		s2CateName.push($('#s2CateName'+i).text());
		s2CateCount.push($('#s2CateCount'+i).text());
	}
	new Chart(document.getElementById("chartjs-dashboard-pie3"), {
		type: "doughnut",
		data: {
			labels: s2CateName,
			datasets: [{
				data: s2CateCount,
				borderWidth: 5
			}]
		},
		options: {maintainAspectRatio: false}
		
	});
});
document.addEventListener("DOMContentLoaded", function() {
	var s3CateNum = $('#s3CateNum').val(); 
	var s3CateName = [];
	var s3CateCount = [];
	for(var i=1; i<=s3CateNum ; i++){
		s3CateName.push($('#s3CateName'+i).text());
		s3CateCount.push($('#s3CateCount'+i).text());
	}
	new Chart(document.getElementById("chartjs-dashboard-pie4"), {
		type: "doughnut",
		data: {
			labels: s3CateName,
			datasets: [{
				data: s3CateCount,
				borderWidth: 5
			}]
		},
		options: {maintainAspectRatio: false}
		
	});
});
