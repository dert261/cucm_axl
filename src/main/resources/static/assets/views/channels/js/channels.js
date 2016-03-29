var socket;
        /* Fill in modal with content loaded with Ajax */
        $(document).ready(function () {
        	$('#myModal2').on('show.bs.modal', function (event) {
            	  var link = $(event.relatedTarget) // Button that triggered the modal
            	  var recipient = link.data('whatever') // Extract info from data-* attributes
            	  var modal = $(this)
            	  modal.find('#chanspy_data').val(recipient);
            	})
        });
        
        function runningFormatter(value, row, index) {
            return index+1;
        }
        
        function actionFormatter(value, row, index) {
            return [
                '<a class="update" href="javascript:void(0)" data-toggle="modal" data-target="#myModal2" data-whatever="'+row.uniqueid+'" title="Подслушать">',
            	'<i class="glyphicon glyphicon-headphones"></i>',
                '</a>',
                '<a class="delete" href="javascript:void(0)" title="Удалить">',
                '<i class="glyphicon glyphicon-trash"></i>',
                '</a>'
            ].join('&nbsp;');
        }
        
        function dateFormatter(value, row, index) {
        	return moment(row.dateCreate).format("DD.MM.YYYY HH:mm:ss");
        }
        
        function secondsFormatter(value, row, index) {
        	var inSeconds = moment((moment().unix()*1000)-row.dateCreate).unix();
        	return moment().startOf('day').seconds(inSeconds).format('HH:mm:ss');;
        }
        
        function originateCall(){
        	var $channel = $('#originate_channel');
        	var $extension = $('#originate_extension');
        	if($channel[0].value==undefined || $extension[0].value==undefined){
        		alert("Error: enter channel and/or extension to originate call");
        		console.log($channel);
        		console.log($extension);
        	}
        	
        	var action = {action:"OriginateRequest"};
            var data = {args:[]};
            
            var originateData = {
            	type : "originateData",
            	application : null,
            	account : null,
            	async : false,
            	callerid : "Оригинация <0000>",
            	callingPress:0,
            	channel : $channel[0].value,
            	codecs : null,
            	context : "rost_local",
            	data : null,
            	exten : $extension[0].value,
            	priority : 1,
            	timeout : 30000,
            	variables : null
            }
            
            data.args.push(originateData);
            
            var content = {command: action, messageData: data};
            var header = {from:"WebServer", to:"msk-tl-001.corp.rostbank.ru"};
            
            var message ={header:header, content: content};
            
            var request=message;
            
            console.log(JSON.stringify(request));
            socket.send(JSON.stringify(request));
        }
        
        function findRow(table, uniqueid){
        	var data = table.bootstrapTable('getData');
        	for (var entry in data){
        		if(uniqueid == data[entry].uniqueid){
        			return data[entry];
        		}	
        	}
        	return null;
        }
        
        function spyCall(){
        	
			var $table = $('#activeChannelTable');
			var $spyChannel = $('#chanspy_data');
        	var currentRow = findRow($table,$spyChannel[0].value);
           
        	var $channel = $('#chanspy_channel');
        	if($channel[0].value==undefined){
        		alert("Error: enter channel to spy channel");
        		return;
        	}
        	
        	if(currentRow==null || currentRow==undefined){
        		alert("Error: spy channel is undefined. May be he shutdown");
        		return;
        	}
        	
        	var action = {action:"OriginateRequest"};
            var data = {args:[]};
            var content;
            var header;
            var message;
            var request;
            var row={channel:$spyChannel[0].value}
            
            var originateData = {
                	type : "originateData",
                	application : "ChanSpy",
                	account : null,
                	async : false,
                	callerid : "Прослушка <59920>",
                	callingPress : 0,
                	channel : $channel[0].value,
                	codecs : null,
                	context :"rost_local",
                	data : row.channel.substring(0, row.channel.search("-"))+",qb",
                	exten : null,
                	priority : 0,
                	timeout : 30000,
                	variables : null
            }
            data.args.push(originateData);
            content = {command: action, messageData: data};
            
            
            
            header = {from:"WebServer", to: currentRow.server};
            message ={header:header, content: content};
            
            request=message; 
            
           
            socket.send(JSON.stringify(request));
        }
        
        function channelStateFormatter(value, row, index) {
        	var onHold = row.onHold==true ? " (OnHold)" : "";
        	var channelStateDesc = row.channelstatedesc;
        	return channelStateDesc+onHold;
        }
        
        function channelHangup(row){
            if(confirm("Разорвать соединение")){
                var action = {action:"HangupRequest"};
                var data = {args:[]};
                data.args.push(row);
                var content = {command: action, messageData: data};
                var header = {from:"WebServer", to: row.server};
                var message ={header:header, content: content};
                var request=message;
                socket.send(JSON.stringify(request));
                
            }   
        }
        
        window.actionEvents = {
            'click .update': function (e, value, row, index) {
        	},
            
            'click .delete': function (e, value, row, index) {
            	channelHangup(row);
            }
        };
        
        
        
        
        
        
        
        
        
        
        
        
        var $table = $('#activeChannelTable');
    	var refreshTimeout=5;
    	var sockAddress=window.location.host; //"http://msk02-ws-0762.corp.rostbank.ru:8080/websocket"
    	
    	function setRefreshTimeout() {
    		refreshTimeout=document.getElementById("refreshPeriod").value; 
    	}
    	
    	var timer = setTimeout(function run() {
    		var $table = $('#activeChannelTable');
    		$table.bootstrapTable('refresh');
    		timer = setTimeout(run, refreshTimeout*1000);
    	}, refreshTimeout*1000);
    	
    	window.onresize=function(){
    		$table.bootstrapTable('resetView');
    	}
    	
    	window.onload=function(){
    		socket = new SockJS("http://"+sockAddress+"/websocket");
    		socket.onopen = function () {
    			console.log("Соединение открылось");
    		};
    		socket.onclose = function () {
    			console.log ("Соединение закрылось");
    			//socket = new SockJS("http://msk02-ws-0762.corp.rostbank.ru:8080/websocket");
    		};
    		socket.onmessage = function (event) {
    			console.log ("Пришло сообщение с содержанием:", event.data);
    		};
    	}