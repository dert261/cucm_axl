
	//$('#add_new_extension').click(function (){
		$("#deviceLinesPart").on("click", "#add_new_extension", function(){	
		
		
		var dev_id = $('#id').val();
		$.ajax({
			type: 'PUT',
			datatype:'json',
			url: "/cme/routers/phone/sccp/"+dev_id+"/addline",
			contentType: "application/json",
			data: JSON.stringify(jQuery('#sccpPhoneModalForm').serializeObject()) 
		}).done(function(result) {
			$('#deviceLinesPart').html(result);
		});
		return false;
	});
	
	
	
	
	
	
	
	$("#deviceLinesPart").on("click", ".panel-heading .close", function(){
		var deletedLine = $(this).data('line-id');
		var deviceId = $(this).data('device-id');
		if(!confirm("Do your realy want to delete line with ID "+deletedLine+" on device ID "+deviceId+"?")) return false;
				
		$.ajax({
			type: 'POST',
			url: jQuery(this).attr('href'),
			data: ({
				id: jQuery(this).attr('data-import'),
				_method: 'POST'
			}),
			}).done(function() {
				
		});
			
		return false;
    	  
    });    	

    
	
	
	


	