$(function(){
    $.fn.saveUserDetails = function() {
        var userDetails = $.fn.buildRequestStringData($('#new_form'));
        $.ajax({
            url: "/bin/saveUserDetails",
            type: 'GET',    
            timeout: 60000, 
            data: { 
                userDetails: userDetails
            },
            cache: true,   
            error: function(data){

            },    
            success: function(data){ 
                var configObj = data;
                if(configObj.success == "true"){
                	$( "#formValidationMsg" ).remove();
                	$(".cmp-form-button" ).after( "<p id='formSuccessMsg'>"+configObj.successMsg+"</p>" );
                }
                else{
                	$( "#formSuccessMsg" ).remove();
                	$(".cmp-form-button" ).after( "<p id='formValidationMsg'>"+configObj.userValidationMsg+"</p>" );
                }
            }
        });

    };

	$.fn.buildRequestStringData = function(form) {
    var input = form.find('input'),requestString = '{userDetails:[{';
        for (var i = 0; i < input.length; i++) {
            if($(input[i]).attr('name') != undefined && $(input[i]).attr('name').trim() != ""){

                requestString += $(input[i]).attr('name').replace(":", "") + ':"' + $(input[i]).val() + '"';

                requestString += ',';
            }
        }
		if (input.length > 0) {
            requestString = requestString.substring(0, requestString.length - 1);
        }
		requestString += '}]}';
        return requestString;
    }
});

$(document).ready(function(){
    /*
    * Validate & Save User Details
    */
	$(".cmp-form-button").click(function(e){
        $.fn.saveUserDetails();
    });
});