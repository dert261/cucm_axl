		var socket;
		function serverNameFormatter(value, row, index) {
			return '<a href="/server/'+row.serverName+'/" title="Подробнее">'+row.serverName+'</a>';
			
    	}
	
		function cpuInfoFormatter(value, row, index) {
			var cpuInfo = row.cpuInfo;
			return cpuInfo.cpuVendor+" "+cpuInfo.cpuModel+" "+cpuInfo.cpuMhz+" ("+cpuInfo.cpuTotalCores+" ядер; "+cpuInfo.cpuCacheSize+" кэш)";
	    }
		
		function memInfoFormatter(value, row, index) {
			var memInfo = row.memInfo;
			return Math.round(memInfo.memTotal/1024/1024)+" Mb";
	    }
		
		function netInfoFormatter(value, row, index) {
			var netInfo = row.netInfo;
			return ;//cpuInfo.cpuVendor+" "+cpuInfo.cpuModel+" "+cpuInfo.cpuMhz+" ("+cpuInfo.cpuTotalCores+" ядер)";
	    }
		
		function loadInfoFormatter(value, row, index) {
			
			return[
				'<div class="progress progress-striped">',
					'<div class="progress-bar" role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%;" />',
						
					
				'</div>'
			       ].join('&nbsp;');;
			
			
			/*var netInfo = row.netInfo;
			return ;//cpuInfo.cpuVendor+" "+cpuInfo.cpuModel+" "+cpuInfo.cpuMhz+" ("+cpuInfo.cpuTotalCores+" ядер)";*/
	    }
		
		
		
	
        function runningFormatter(value, row, index) {
            return index+1;
        }
        
        function actionFormatter(value, row, index) {
            return [
                '<a class="reset" href="javascript:void(0)" title="Переустановить">',
            	'<i class="fa fa-repeat"></i>',
                '</a>',
                '<a class="restart" href="javascript:void(0)" title="Перезагрузить">',
                '<i class="fa fa-refresh"></i>',
                '</a>'
            ].join('&nbsp;');
        }
                
        window.actionEvents = {
            'click .reset': function (e, value, row, index) {
            	resetPeer(row);
        	},
            
            'click .restart': function (e, value, row, index) {
            	restartPeer(row);
            }
        };
        