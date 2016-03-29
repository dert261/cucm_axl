var socket;
                
        function runningFormatter(value, row, index) {
            return index+1;
        }
        
        function actionFormatter(value, row, index) {
            return [
                '<a class="reset" href="javascript:void(0)" title="Переустановить">',
            	'<i class="glyphicon glyphicon-repeat"></i>',
                '</a>',
                '<a class="restart" href="javascript:void(0)" title="Перезагрузить">',
                '<i class="glyphicon glyphicon-refresh"></i>',
                '</a>'
            ].join('&nbsp;');
        }
        
        function resetPeer_old(row){
        	var action = {action:"SipNotifyRequest"};
            var data = {args:[]};
            var content;
            var header;
            var message;
            var request;
                    
            var notifyVariables = {};
            notifyVariables["type"] = "sipNotifyData";
            notifyVariables["Event"] = "service-control";
            notifyVariables["Subscription-State"] = "active";
            notifyVariables["Content-Type"] = "text/plain";
            notifyVariables["Content"] = "action=reset "+
            		//"RegisterCallId={${SIPPEER(${PEERNAME},regcallid)}} "+
            		"ConfigVersionStamp=\{00000000-0000-0000-0000-000000000000\} "+
            		"DialplanVersionStamp=\{00000000-0000-0000-0000-000000000000\} "+
            		"SoftkeyVersionStamp=\{00000000-0000-0000-0000-000000000000\} "+
            		"FeatureControlVersionStamp=\{00000000-0000-0000-0000-000000000000\}";
            
            
            var notifyData = {
            		channel : row.objectName,
            		variables : notifyVariables
            }
            
            data.args.push(notifyData);
            content = {command: action, messageData: data};
            header = {from:"FROM", to:row.server};
            message ={header:header, content: content};
            
            request=message; 
            
            console.log(JSON.stringify(request));
            socket.send(JSON.stringify(request));
        }
        
        function restartPeer_old(row){
        	var action = {action:"SipNotifyRequest"};
            var data = {args:[]};
            var content;
            var header;
            var message;
            var request;
            //var row={channel:$spyChannel[0].value}
            
            /*var notifyVariables = {
            		type : "sipNotifyData",
            		Event : "service-control",
            		Subscription-State : "active",
            		Content-Type : "text/plain",
            		Content : "action=restart "+
            		"RegisterCallId={${SIPPEER(${PEERNAME},regcallid)}} "+
            		"ConfigVersionStamp={00000000-0000-0000-0000-000000000000} "+
            		"DialplanVersionStamp={00000000-0000-0000-0000-000000000000} "+
            		"SoftkeyVersionStamp={00000000-0000-0000-0000-000000000000} "+
            		"FeatureControlVersionStamp={00000000-0000-0000-0000-000000000000}"
            }*/
            
            var notifyVariables = {};
            notifyVariables["type"] = "sipNotifyData";
            notifyVariables["Event"] = "service-control";
            notifyVariables["Subscription-State"] = "active";
            notifyVariables["Content-Type"] = "text/plain";
            notifyVariables["Content"] = "action=restart "+
            		//"RegisterCallId={${SIPPEER(${PEERNAME},regcallid)}} "+
            		"ConfigVersionStamp=\{00000000-0000-0000-0000-000000000000\} "+
            		"DialplanVersionStamp=\{00000000-0000-0000-0000-000000000000\} "+
            		"SoftkeyVersionStamp=\{00000000-0000-0000-0000-000000000000\} "+
            		"FeatureControlVersionStamp=\{00000000-0000-0000-0000-000000000000\}";
            
            var notifyData = {
            		channel : row.objectName,
            		variables : notifyVariables
            }
            
            data.args.push(notifyData);
            content = {command: action, messageData: data};
            header = {from:"FROM", to:row.server};
            message ={header:header, content: content};
            
            request=message; 
            
            console.log(JSON.stringify(request));
            socket.send(JSON.stringify(request));
        }
        
        
        
        
        
        
        
        function resetPeer(row){
        	var action = {action:"CommandRequest"};
            var data = {args:[]};
            var content;
            var header;
            var message;
            var request;
                    
            var commandData = {
            	type : "commandData",
            	command : "sip notify cisco-reset "+row.objectName,
            }
            
            data.args.push(commandData);
            content = {command: action, messageData: data};
            header = {from:"FROM", to:row.server};
            message ={header:header, content: content};
            
            request=message; 
            
            console.log(JSON.stringify(request));
            socket.send(JSON.stringify(request));
        }
        
        function restartPeer(row){
        	var action = {action:"CommandRequest"};
            var data = {args:[]};
            var content;
            var header;
            var message;
            var request;
                    
            var commandData = {
            	type : "commandData",
            	command : "sip notify cisco-restart "+row.objectName,
            }
            
            data.args.push(commandData);
            content = {command: action, messageData: data};
            header = {from:"FROM", to:row.server};
            message ={header:header, content: content};
            
            request=message; 
            
            console.log(JSON.stringify(request));
            socket.send(JSON.stringify(request));
        }
        
        
        
        window.actionEvents = {
            'click .reset': function (e, value, row, index) {
            	resetPeer(row);
        	},
            
            'click .restart': function (e, value, row, index) {
            	restartPeer(row);
            }
        };
        
        
        
        
        
        
        
        var $table = $('#activeChannelTable');
    	var refreshTimeout=5;
    	var sockAddress=window.location.host; 
    	
    	function setRefreshTimeout() {
    		refreshTimeout=document.getElementById("refreshPeriod").value; 
    	}
    	
    	var timer = setTimeout(function run() {
    		var $table = $('#peersTable');
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
    		};
    		socket.onmessage = function (event) {
    			console.log ("Пришло сообщение с содержанием:", event.data);
    		};
    	}