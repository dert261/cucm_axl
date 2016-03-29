	$(document).ready(function() {
	
		$(function () {
			$('[data-toggle="tooltip"]').tooltip()
		})
		
		/*$('#monthStart')
			.on("change", function(e) {
				console.log(monthDaysCount(e.target.value));
				$('#monthStop').select2('data', monthDaysCount(e.target.value));
				select2ChangeData('monthStop',monthDaysCount(e.target.value));
				
		});
		
		$('#monthStop')
			.on("change", function(e) {
				select2ChangeData('monthDayStop',monthDaysCount(e.target.value));
				//console.log(monthDaysCount(e.target.value));
		});*/
		
	});
	
	/*function select2ChangeData(selectId, data){
		
		var $select = $('#'+selectId);
		// save current config. options
		var options = $select.data('select2').options.options;

		// delete all items of the native select element
		$select.html('');

		// build new items
		var items = data;
		for (var i = 0; i < data.length; i++) {
		    $select.append("<option value=\"" + data[i].id + "\">" + data[i].text + "</option>");
		}

		// add new items
		options.data = data;
		$select.select2(options);
	}
	
	function monthDaysCount(month){
		var dayCount=0;
		var days=[];
		switch(month){
			case 'JANUARY': 	dayCount=31; break;
			case 'FEBRUARY': 	dayCount=29; break;
			case 'MARCH': 		dayCount=31; break;
			case 'APRIL': 		dayCount=30; break;
			case 'MAY': 		dayCount=31; break;
			case 'JUNE': 		dayCount=30; break;
			case 'JULY': 		dayCount=31; break;
			case 'AUGUST': 		dayCount=31; break;
			case 'SEPTEMBER': 	dayCount=30; break;
			case 'OCTOBER': 	dayCount=31; break;
			case 'NOVEMBER': 	dayCount=30; break;
			case 'DECEMBER': 	dayCount=31; break;
			default: break;
		}
		
		for(i=1;i<=dayCount;i++){
			days[i-1]={id: i, text: i};
		}
		
		return days;
	}*/
	