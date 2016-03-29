
$(document).ready(function(){
    		$('[data-toggle="popover"]').popover({
    			content: function() {
    				var intName;
    				$(this).each(function() {
    					$.each(this.attributes, function() {
    						if(this.specified) {
    					    	if(this.name == "data-element")
    					    		intName = this.value;
    					    }
    					});
    				});
    				return getIntDetail(intName); 
    			}
    		});
		});
	    
function formatHuman(value){
	
	var res=null;
	
	if(value<1024){											
		res = value+" b";									//b
	}else if(value>1024 && value<1048576){						
		res = (Math.ceil(value/1024))+" Kb";					//Kb
	}else if(value>1048576 && value<1073741824){					
		res = (Math.ceil(value/1024/1024))+" Mb";				//Mb
	}else if(value>1073741824 && value < 1099511627776){			
		res = (Math.ceil(value/1024/1024/1024))+" Gb";		//Gb
	}else if(value > 1099511627776){								
		res = (Math.ceil(value/1024/1024/1024/1024))+" Tb";	//Tb
	}
	return res;
}