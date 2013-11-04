/* Javascript for the main layout that will import to all pages */

/**
 * Sets the html tag to have a class corresponding to the client's browser.
 * This will detect Chrome, Firefox, Opera, Safari, and IE[x] where [x] is
 * the version of IE.  The class set into the html tag can then be used
 * to make any browser specific css stylings. 
 * 
 * For example, if there is a 
 * div with id="myDiv" in the page, then to style it for Chrome just use:
 * 
 * .Chrome #myDiv {
 * 		width: 200px;
 * }
 * 
 */
$(function(){
	 navigator.sayswho= (function(){
		  var N= navigator.appName, ua= navigator.userAgent, tem;
		  var M= ua.match(/(opera|chrome|safari|firefox|msie)\/?\s*(\.?\d+(\.\d+)*)/i);
		  if(M && (tem= ua.match(/version\/([\.\d]+)/i))!= null) M[2]= tem[1];
		  M= M? [M[1], M[2]]: [N, navigator.appVersion,'-?'];
		  return M;
		 })();
	
	 var browser = navigator.sayswho[0];
	 var version = navigator.sayswho[1];
	 var tag = "";
	 
	 switch(browser){
	 	case "MSIE":
	 		tag = "IE";
	 		$('html').addClass(tag);
			switch(version){
			 	case "7.0": tag += "7"; break;
			 	case "8.0": tag += "8"; break;
			 	case "9.0": tag += "9"; break;
			 	case "10.0": tag += "10"; break;
			 	case "11.0": tag += "11"; break;
			 	default: alert("Your version of Internet Explorer may not be supported for this site.");
			}
			break;
	 	case "Firefox":
	 		tag = "Firefox";
	 		break;
	 	case "Chrome":
	 		tag = "Chrome";
	 		break;
	 	case "Safari":
	 		tag = "Safari";
	 		break;
	 	case "Opera":
	 		tag = "Opera";
	 		break;
	 	default: alert("Your browser may not be supported for this site.");
	 }
	 
	 $('html').addClass(tag);
});