document.addEventListener("DOMContentLoaded", function() {
			data1 = $('#mCate1').text()
			data2 = $('#mCate2').text()
			data3 = $('#mCate3').text()
			
			new Chart(document.getElementById("chartjs-dashboard-pie1"), {
				type: "doughnut",
				data: {
					labels: ["IT", "자격증", "어학"],
					datasets: [{
						data: [data1, data2, data3],
						backgroundColor: [
							'rgb(255, 99, 132)',
					      'rgb(54, 162, 235)',
					      'rgb(255, 205, 86)'
						],
						borderWidth: 5
					}]
				},
				options: {maintainAspectRatio: false}
				
			});
		});
		document.addEventListener("DOMContentLoaded", function() {
			new Chart(document.getElementById("chartjs-dashboard-pie2"), {
				type: "doughnut",
				data: {
					labels: ["Chrome", "Firefox", "IE"],
					datasets: [{
						data: [4306, 3801, 1689],
						backgroundColor: [
							'rgb(255, 99, 132)',
					      	'rgb(54, 162, 235)',
					      	'rgb(255, 205, 86)'
						],
						borderWidth: 1
					}]
				},
				options: {maintainAspectRatio: false}
			});
		});
		document.addEventListener("DOMContentLoaded", function() {
			new Chart(document.getElementById("chartjs-dashboard-pie3"), {
				type: "doughnut",
				data: {
					labels: ["Chrome", "Firefox", "IE"],
					datasets: [{
						data: [4306, 3801, 1689],
						backgroundColor: [
							'rgb(255, 99, 132)',
					      	'rgb(54, 162, 235)',
					      	'rgb(255, 205, 86)'
						],
						borderWidth: 1
					}]
				},
				options: {maintainAspectRatio: false}
			});
		});
		document.addEventListener("DOMContentLoaded", function() {
			new Chart(document.getElementById("chartjs-dashboard-pie4"), {
				type: "doughnut",
				data: {
					labels: ["Chrome", "Firefox", "IE"],
					datasets: [{
						data: [4306, 3801, 1689],
						backgroundColor: [
							'rgb(255, 99, 132)',
					      	'rgb(54, 162, 235)',
					      	'rgb(255, 205, 86)'
						],
						borderWidth: 1
					}]
				},
				options: {maintainAspectRatio: false}
			});
		});
