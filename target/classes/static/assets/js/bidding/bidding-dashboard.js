
        $(document).ready(function () {
            //$('body').bootstrapMaterialDesign();
            $("#kpiImg").hide();
        });
       
        
       
        function tabView(type){
     	   if (type=="operational"){	
     		  $("#operationalImg").show();
     		   $("#kpiImg").hide();
     	   }else if(type=="kpi"){
     		   
     		  operationalHighChat();
     		  $("#operationalImg").hide();
     		  $("#kpiImg").show();
     	   }else if(type=="operational"){
     		  $("#operationalImg").show();
     		   }else{
     			  $("#operationalImg").show();
     		   }
     		   
        } 
   