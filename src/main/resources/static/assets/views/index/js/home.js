$(function() {	
	
		var container = $("#active-channel-graph");
		var activeChannelsCountContainer = $("#activeChannelsCount");
		var activePeersCountContainer = $("#activePeersCount");
		var activeHostsCountContainer = $("#activeHostsCount");
				
		var maximum = 300;
			
		var data = []; 
		
		while (data.length < maximum) {
            data.push(0);
        }
		
		var res = []; 
        for (var i = 0; i < data.length; ++i) {
            res.push([i, data[i]])
        }

        data=res;
		
		var series = [{
			data: data,
			lines: {
				fill: true
			}
		}];
		
		//$.plot("#active-channel-graph", data, options);
	
		
		var refreshTimeout=1;
		function setRefreshTimeout() {
			refreshTimeout=document.getElementById("refreshPeriod").value; 
		}
		
		var timer = setTimeout(function run() {
			refreshGraph();
			refreshPeer();
			refreshHost();
			timer = setTimeout(run, refreshTimeout*1000);
		}, 0);
		
		
		/*var timer2 = setTimeout(function run() {
			
			timer2 = setTimeout(run, refreshTimeout*10000);
		}, 0);*/
		
		
		var plot = $.plot(container, series, {
			grid: {
				hoverable: true,
				clickable: true,
				borderWidth: 1,
				minBorderMargin: 20,
				labelMargin: 10,
				backgroundColor: {
					colors: ["#fff", "#e4f4f4"]
				},
				margin: {
					top: 8,
					bottom: 20,
					left: 20
				},
				markings: function(axes) {
					var markings = [];
					var xaxis = axes.xaxis;
					for (var x = Math.floor(xaxis.min); x < xaxis.max; x += xaxis.tickSize * 2) {
						markings.push({ xaxis: { from: x, to: x + xaxis.tickSize }, color: "rgba(232, 232, 255, 0.2)" });
					}
					return markings;
				}
			},
			xaxis: {
				tickFormatter: function() {
					return "";
				}
			},
			yaxis: {
				min: 0,
				//max: 100
			},
			legend: {
				show: true
			}
		});
		
		$("<div id='tooltip'></div>").css({
			position: "absolute",
			display: "none",
			border: "1px solid #fdd",
			padding: "2px",
			"background-color": "#fee",
			opacity: 0.80
			}).appendTo("body");
		
		$(container).bind("plothover", function (event, pos, item) {
			if (item) {
				var x = item.datapoint[0].toFixed(2),
					y = item.datapoint[1].toFixed(2);

				$("#tooltip").html(y)
					.css({top: item.pageY+5, left: item.pageX+5})
					.fadeIn(200);
			} else {
				$("#tooltip").hide();
			}
		});
		/*var yaxisLabel = $("<div class='axisLabel yaxisLabel'></div>")
		.text("Количество активных каналов")
		.appendTo(container);

	// Since CSS transforms use the top-left corner of the label as the transform origin,
	// we need to center the y-axis label by shifting it down by half its width.
	// Subtract 20 to factor the chart's bottom margin into the centering.

		yaxisLabel.css("margin-top", yaxisLabel.width() / 2 - 20);*/
		
		
		
		function refreshGraph(){
			
			function onDataReceived(newData) {
				
				var res = [];
				if (data.length>maximum)
					data=data.slice(1);
				
				data.push(newData[0]);
						
				for (var i = 0; i < data.length; ++i) {
					res.push([i, data[i]])
				}
				
				series[0].data = res;
				
				activeChannelsCountContainer.html(newData[0]);
				
				plot.setData(series);
				plot.setupGrid();
				plot.draw();
			}			
			
			$.ajax({
				url: "activeChannelCount.json",
				type: "GET",
				dataType: "json",
				success: onDataReceived
			});
		}
		
		function refreshPeer(){
			
			function onDataReceived(data) {
				activePeersCountContainer.html(data);
			}			
			
			$.ajax({
				url: "activePeerCount.json",
				type: "GET",
				dataType: "json",
				success: onDataReceived
			});
		}
		
		function refreshHost(){
			
			function onDataReceived(data) {
				activeHostsCountContainer.html(data);
			}			
			
			$.ajax({
				url: "activeHostCount.json",
				type: "GET",
				dataType: "json",
				success: onDataReceived
			});
		}
		
	});