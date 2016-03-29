	$(document).ready(function() {
		
		jQuery(function($) {
			$('[data-toggle="tooltip"]').tooltip();
		});
		
		function dateFormatter(value) {
			if(value==null){return "";}
        	return moment(value).format("DD.MM.YYYY HH:mm:ss");
        }
        
        var myTable = $('#userstable').DataTable({
	    	//"stateSave": true,
	    	"processing": true,
	        "serverSide": false,
	        "ajax": {
	        	"method": "GET",
	        	"url": "/users/ajax/userdata.json"
	        },
	    	"columns": [
	    	            { "data": "id" },
	    	            { "data": "name" },
	    	            { "data": "login" },
	    	            { "data": "roleLocalized" },
	    	            { "data": "statusLocalized" },
	    	            { "data": "localUserFlagLocalized" },
	    	            { "data": "lastLogin" },
	    	            { "data": "signinDate" },
	    	            { "data": "blockDate" },
	    	            { "data": "id" }
	    	        ],
	    	
	    	
			"language": {
	        	"url": "/static/assets/data-tables-1.10.6/i18n/russian.lang"
	        },
	        "lengthMenu": [[5, 10, 25, 50, -1], [5, 10, 25, 50, "Все"]],
	        "order": [[ 1, 'asc' ]],
	        "columnDefs": [
				{
	    			"searchable": false,
	    			"orderable": false,
	    			"targets": [0]
				},
	            {
		         	"targets": [-1],
		         	"searchable": false,
	    			"orderable": false,
		            "data": "Действия",
		            "render": function (data, type, full, meta) {
		            	var updateField = '<a class="update" href="/users/update/'+data+'" data-toggle="tooltip" title="Редактировать" data-original-title="Редактировать"> \
		     	 								<i class="glyphicon glyphicon-pencil"></i></a>';
		     	 		var deleteField = '<a class="delete" href="/users/delete" data-delete="'+data+'" data-toggle="tooltip" title="Удалить" data-original-title="Удалить"> \
		         								<i class="glyphicon glyphicon-trash"></i> </a>'; 	 
			            return "<center>"+updateField+"&nbsp;&nbsp;&nbsp;"+deleteField+"</center>";
	           		}
	           	},
	           	{
		         	"targets": [-2],
		         	"searchable": true,
	    			"orderable": true,
		            "data": "Дата блокировки",
		            "render": function (data, type, full, meta) {
		            	return "<center>"+dateFormatter(data)+"</center>";
	           		}
	           	},
				{
		         	"targets": [-3],
		         	"searchable": true,
	    			"orderable": true,
		            "data": "Дата регистрации",
		            "render": function (data, type, full, meta) {
		            	return "<center>"+dateFormatter(data)+"</center>";
	           		}
	           	},
				{
		         	"targets": [-4],
		         	"searchable": true,
	    			"orderable": true,
		            "data": "Время последнего входа",
		            "render": function (data, type, full, meta) {
		            	return "<center>"+dateFormatter(data)+"</center>";
	           		}
	           	}
			]
	    });
		
	    myTable.on('click','a.delete',function() {
			if(!confirm('Вы уверены что хотите удалить данного пользователя?')) return false;
			
			var deletedRow = myTable.row( $(this).parents('tr') );
			
			$.ajax({
				type: 'POST',
				url: jQuery(this).attr('href'),
				data: ({
					id: jQuery(this).attr('data-delete'),
					_method: 'DELETE'
				}),
				}).done(function() {
					deletedRow
				        .remove()
				        .draw(false);
			});
				
			return false;
	    })
	    
	    myTable.on( 'order.dt search.dt', function () {
	    	myTable.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
	            cell.innerHTML = i+1;
	        } );
	    } ).draw();
	    
	    
	});
	